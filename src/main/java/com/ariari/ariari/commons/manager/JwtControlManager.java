package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.exception.exceptions.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/** Redis 세팅 후 작업
 * - 토큰 ban 처리
 * - ban 처리된 토큰 여부 체크
 */
@Component
@RequiredArgsConstructor
public class JwtControlManager {

    private final RedisManager redisManager;

    private static final String REDIS_BANNED_TOKEN_KEY = "BANNED_TOKEN";

    public boolean isBannedToken(String token) {
        Object data = redisManager.getData(resolveRedisKey(token));
        return data != null;
    }

    public void banToken(String token, Date date) {
        Date now = new Date();
        long diffMilli = date.getTime() - now.getTime();
        if (diffMilli <= 0) {
            throw new ExpiredTokenException();
        }

        redisManager.setExData(resolveRedisKey(token), true, diffMilli, TimeUnit.MILLISECONDS);
    }

    private String resolveRedisKey(String token) {
        return REDIS_BANNED_TOKEN_KEY + "_" + token;
    }

}
