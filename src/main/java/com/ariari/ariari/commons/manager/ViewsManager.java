package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.manager.RedisManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
 *  -> CLUB31_1101 : 1 (views) (expired : 14 days)
 */
@Component
@RequiredArgsConstructor
public class ViewsManager {

    private final RedisManager redisManager;

    @Value("${views-manager.expiration-date.view-duplicate}")
    private int VIEW_DUPLICATE_EXPIRED_DAYS;

    @Value("${views-manager.expiration-date.view}")
    private int VIEW_EXPIRED_DAYS;

    private static final boolean INVALID_DATA = true;

    public void addClientIp(ViewsContentType viewsContentType, Long id, String clientIp) {
        String key = resolveClientIpKey(viewsContentType, id, clientIp);
        redisManager.setExData(key, INVALID_DATA, VIEW_DUPLICATE_EXPIRED_DAYS, TimeUnit.DAYS);
    }

    public boolean checkForDuplicateView(ViewsContentType viewsContentType, Long id, String clientIp) {
        String key = resolveClientIpKey(viewsContentType, id, clientIp);
        return redisManager.checkExistingData(key);
    }

    public void addViews(ViewsContentType viewsContentType, Long id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");
        String now = dateFormat.format(new Date());

        String key = resolveViewsKey(viewsContentType, id, now);
        Integer views = (Integer) redisManager.getData(key);

        if (views != null) {
            redisManager.setExData(key, views + 1, VIEW_EXPIRED_DAYS, TimeUnit.DAYS);
        } else {
            redisManager.setExData(key, 1, VIEW_EXPIRED_DAYS, TimeUnit.DAYS);
        }
    }

    private String resolveClientIpKey(ViewsContentType viewsContentType, Long id, String clientIp) {
        return viewsContentType.toString() + id + '_' + clientIp;
    }

    private String resolveViewsKey(ViewsContentType viewsContentType, Long id, String date) {
        return viewsContentType.toString() + id + '_' + date;
    }

}
