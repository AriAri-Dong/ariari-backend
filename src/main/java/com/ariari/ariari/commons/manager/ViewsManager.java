package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.enums.ViewsContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 1. 조회수 중복 방지 set에 추가
 *  -> CLUB31_213.23.41.202 : null (expired : 1 day)
 * 2. 중복 조회인지 확인
 *  -> find in redis (CLUB + clubId + "_" + clientId)
 * 3. 조회 수 처리
 *  -> CLUB31_11-01 : 1 (views) (expired : 14 days)
 */
@Component
@RequiredArgsConstructor
public class ViewsManager {

    private final RedisTemplate<String, Integer> redisTemplate;

    private static final int VIEW_DUPLICATE_EXPIRED_DAYS = 1;
    private static final int VIEW_EXPIRED_DAYS = 14;

    public void addClientIp(ViewsContentType viewsContentType, Long id, String clientIp) {
        String key = resolveKeyForDuplicateView(viewsContentType, id, clientIp);
        redisTemplate.opsForValue().set(key, 1, VIEW_DUPLICATE_EXPIRED_DAYS, TimeUnit.DAYS);
    }

    public boolean checkForDuplicateView(ViewsContentType viewsContentType, Long id, String clientIp) {
        String key = resolveKeyForDuplicateView(viewsContentType, id, clientIp);
        Integer result = redisTemplate.opsForValue().get(key);
        return result != null;
    }

    public void addViews(ViewsContentType viewsContentType, Long id) {
        ValueOperations<String, Integer> ops = redisTemplate.opsForValue();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String dateString = dateFormat.format(new Date());
        String key = resolveKeyForAddingView(viewsContentType, id, dateString);
        Integer views = ops.get(key);

        if (views == null) {
            ops.set(key, 1);
        } else {
            ops.set(key, views + 1);
        }
    }

    private String resolveKeyForDuplicateView(ViewsContentType viewsContentType, Long id, String clientIp) {
        return viewsContentType.toString() + id + '_' + clientIp;
    }

    private String resolveKeyForAddingView(ViewsContentType viewsContentType, Long id, String dateString) {
        return viewsContentType.toString() + id + '_' + dateString;
    }

}
