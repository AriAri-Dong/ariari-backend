package com.ariari.ariari.commons.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AuthCodeManager {
    @Value("${email.code.characters}")
    private String CHARACTERS;

    @Value("${email.code.code-length}")
    private int CODE_LENGTH;

    @Value("${email.code.expiration-time}")
    private long EXPIRATION_TIME;

    private final StringRedisTemplate redisTemplate;

    // 인증번호 생성 후 레디스에 저장
    public String createAuthCode(String key) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        String authCode = code.toString();
        try{
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(key, authCode, EXPIRATION_TIME, TimeUnit.MINUTES);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return authCode;
    }

    // 인증번호 검증
    public boolean validateAuthCode(String key, String authCode) {
        try{
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            String storedCode = ops.get(key);

            if (authCode.equals(storedCode)) {
                redisTemplate.delete(key);
                return true;
            }
        }catch (Exception e){
            throw new RuntimeException(e); // 예외처리 필요
        }
        return false;
    }
}
