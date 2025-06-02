package com.ariari.ariari.domain.club.club.invite;

import com.ariari.ariari.commons.manager.RedisManager;
import com.ariari.ariari.domain.club.club.invite.exception.InvalidInviteKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class InviteManager{

    private final RedisManager redisManager;

    private static final String PREFIX = "invite:";

    public String createKey(Long clubId){
        String key = UUID.randomUUID().toString();
        redisManager.setExData(PREFIX + key, clubId,1, TimeUnit.DAYS);
        return key;
    }

    public Long getInviteKey(String key) {
        Long clubId = (Long) redisManager.getData(PREFIX + key);
        if (clubId == null) {
            throw new InvalidInviteKeyException();
        }
        redisManager.deleteData(PREFIX + key);
        return clubId;
    }
}
