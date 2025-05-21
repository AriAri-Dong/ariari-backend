package com.ariari.ariari.domain.school.auth;

import com.ariari.ariari.commons.manager.MailManager;
import com.ariari.ariari.commons.manager.RedisManager;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.auth.exception.InvalidAuthCodeException;
import com.ariari.ariari.domain.school.auth.exception.NoSchoolAuthCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SchoolAuthManager {

    private final RedisManager redisManager;

    // key, 만료시간
    @Value("${school-auth.key-name}")
    private String KEY_NAME;

    @Value("${school-auth.expiration-time}")
    private int EXPIRATION_TIME;

    @Value("${school-auth.code-length}")
    private int CODE_LENGTH;

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom random = new SecureRandom();

    public String issueSchoolAuthCode(Member reqMember, School school) {
        String key = resolveKey(reqMember);
        String authCode = generateCode();

        String value = resolveValue(school, authCode);
        redisManager.setExData(key, value, EXPIRATION_TIME, TimeUnit.MINUTES);

        return authCode;
    }

    public String issueSchoolAuthCode(String schoolEmail) {
        String authCode = generateCode();
        redisManager.setExData(schoolEmail, authCode, EXPIRATION_TIME, TimeUnit.MINUTES);
        return authCode;
    }

    /**
     * @return schoolId
     */
    public Long validateAuthCode(Member reqMember, String authCode) {
        String value = (String) redisManager.getData(resolveKey(reqMember));
        if (value == null) {
            throw new NoSchoolAuthCodeException();
        }

        String authCodeInRedis = extractAuthCode(value);
        if (!authCode.equals(authCodeInRedis)) {
            throw new InvalidAuthCodeException();
        }

        return Long.valueOf(extractSchoolId(value));
    }

    public void validateAuthCode(String schoolEmail, String authCode) {
        String value = (String) redisManager.getData(schoolEmail);

        if (value == null) {
            throw new NoSchoolAuthCodeException();
        }

        if(!value.equals(authCode)){
            throw new InvalidAuthCodeException();
        }
    }

    private String resolveKey(Member member) {
        return KEY_NAME + "_" + member.getId();
    }

    private String resolveValue(School school, String authCode) {
        return school.getId().toString() + "_" + authCode;
    }

    private String extractAuthCode(String value) {
        return value.split("_")[1];
    }

    private String extractSchoolId(String value) {
        return value.split("_")[0];
    }

    public String generateCode() {
        StringBuilder authCode = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            authCode.append(CHARACTERS.charAt(randomIndex));
        }

        return authCode.toString();
    }

}
