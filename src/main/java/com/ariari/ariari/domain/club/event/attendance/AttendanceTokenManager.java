package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.manager.RedisManager;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.club.event.attendance.exception.InvalidAttendanceKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AttendanceTokenManager {

    private final RedisManager redisManager;

    private final int ATTENDANCE_TOKEN_EXPIRED_HOURS = 1; // yml hiding 예정

    public String createAttendanceKey(ClubEvent clubEvent) {
        String key = createKey();
        redisManager.setExData(key, clubEvent.getId(), ATTENDANCE_TOKEN_EXPIRED_HOURS, TimeUnit.HOURS);
        return key;
    }

    public Long getClubEventId(String key) {
        Long clubEventId = (Long) redisManager.getData(key);
        if (clubEventId == null) {
            throw new InvalidAttendanceKeyException();
        }

        return clubEventId;
    }

    private String createKey() {
        return "ATTENDANCE_TOKEN_" + UUID.randomUUID().toString();
    }

}
