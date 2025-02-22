package com.ariari.ariari.commons.auth.oauth;

import com.ariari.ariari.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KakaoAuthManager {

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;

    @Value("${kakao.auth-uri}")
    private String KAKAO_AUTH_URI;

    @Value("${kakao.kapi-uri}")
    private String KAKAO_KAPI_URI;

    @Value("${kakao.client.admin}")
    private String KAKAO_ADMIN_KEY;

    @Value("${kakao.unlink-uri}")
    private String KAKAO_UNLINK_URI;

    public String getKakaoToken(String code) {
        if (code == null) {
            throw new RuntimeException("No Kakao auth code");
        }

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type"   , "authorization_code");
        requestBody.add("client_id"    , KAKAO_CLIENT_ID);
        requestBody.add("client_secret", KAKAO_CLIENT_SECRET);
        requestBody.add("code"         , code);
        requestBody.add("redirect_uri" , KAKAO_REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, requestHeader);
        ResponseEntity<String> response = new RestTemplate().exchange(
                KAKAO_AUTH_URI,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        String responseBody = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("파싱하다가 에러남 ㅠ");
        }

        return String.valueOf(jsonObject.get("access_token"));
    }

    public Long getKakaoId(String token) {
        if (token == null) {
            throw new RuntimeException("No Kakao auth token");
        }

        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", "Bearer " + token);
        requestHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestHeader);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_KAPI_URI,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        String responseBody = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("파싱하다가 에러남 ㅠ");
        }

        String kakaoId = String.valueOf(jsonObject.get("id"));
        return Long.valueOf(kakaoId);
    }

    public void unregister(Member reqMember) {
        HttpHeaders requestHeader = new HttpHeaders();
        requestHeader.add("Authorization", "KakaoAK " + KAKAO_ADMIN_KEY);
        requestHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("target_id_type" , "user_id");
        requestBody.add("target_id"      , reqMember.getKakaoId().toString());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, requestHeader);
        ResponseEntity<String> response = new RestTemplate().exchange(
                KAKAO_UNLINK_URI,
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        String responseBody = response.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("파싱하다가 에러남 ㅠ");
        }

        String kakaoId = String.valueOf(jsonObject.get("id"));
        log.info("unregister : {}", kakaoId);
    }

}
