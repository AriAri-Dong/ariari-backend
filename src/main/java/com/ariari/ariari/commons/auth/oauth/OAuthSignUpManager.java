package com.ariari.ariari.commons.auth.oauth;

import com.ariari.ariari.commons.auth.oauth.exceptions.InvalidSignUpKeyException;
import com.ariari.ariari.commons.manager.RedisManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OAuthSignUpManager {

    private final RedisManager redisManager;

    private static final String KEY_PREFIX = "OAUTH_SIGNUP_KEY";

    private static final int KEY_EXPIRATION_TIME = 10;

    public String issueSignUpKey(String token) {
        String key = generateKey();
        redisManager.setExData(convertPrefix(key), "¦" + token, KEY_EXPIRATION_TIME, TimeUnit.MINUTES);
        return key;
    }

    // oauth 토큰 획득
    public String getToken(String key) {
        if (!redisManager.checkExistingData(convertPrefix(key))) {
            throw new InvalidSignUpKeyException();
        }

        String value = (String) redisManager.getData(convertPrefix(key));
//        deleteKey(key);

        return value.split("¦")[1];
    }

    // 데이터 삭제
    public void deleteKey(String key) {
        redisManager.deleteData(convertPrefix(key));
    }

    private String generateKey() {
        return UUID.randomUUID().toString();
    }

    private String convertPrefix(String key) {
        return KEY_PREFIX + "_" + key;
    }

}
