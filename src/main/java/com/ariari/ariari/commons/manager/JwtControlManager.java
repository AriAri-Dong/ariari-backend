package com.ariari.ariari.commons.manager;

import org.springframework.stereotype.Component;

/** Redis 세팅 후 작업
 * - 토큰 ban 처리
 * - ban 처리된 토큰 여부 체크
 */
@Component
public class JwtControlManager {

    public boolean isBannedToken(String token) {
        return false;
    }

    public void banToken(String token) {
    }

}
