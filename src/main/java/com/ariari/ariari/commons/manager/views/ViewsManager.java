package com.ariari.ariari.commons.manager.views;

import com.ariari.ariari.commons.manager.RedisManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 1. 조회수 중복 방지 set에 추가
 *  -> CLUB31_213.23.41.202 : null (expired : 1 day)
 * 2. 중복 조회인지 확인
 *  -> find in redis (CLUB + clubId + "_" + clientId)
 * 3. 조회 수 처리 -> 더하기
 *  -> CLUB31_1101 : 1 (views) (expired : 14 days)
 * 4. 조회 수 처리 -> 빼기
 *  -> 15일 전 조회 수
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
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");

    public void addClientIp(ViewsContent content, String clientIp) {
        String key = resolveClientIpKey(content, clientIp);
        redisManager.setExData(key, INVALID_DATA, VIEW_DUPLICATE_EXPIRED_DAYS, TimeUnit.DAYS);
    }

    public boolean checkForDuplicateView(ViewsContent content, String clientIp) {
        String key = resolveClientIpKey(content, clientIp);
        return redisManager.checkExistingData(key);
    }

    @Transactional
    public void addViews(ViewsContent content) {
        String now = dateFormat.format(new Date());

        String key = resolveViewsKey(content, now);
        Integer views = (Integer) redisManager.getData(key);

        if (views != null) {
            redisManager.setExData(key, views + 1, VIEW_EXPIRED_DAYS, TimeUnit.DAYS);
        } else {
            redisManager.setExData(key, 1, VIEW_EXPIRED_DAYS, TimeUnit.DAYS);
        }

        content.addViews(1);
    }

    @Transactional
    public void subtractViews(ViewsContent content) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -(VIEW_EXPIRED_DAYS + 1));

        String before = dateFormat.format(cal.getTime());

        String key = resolveViewsKey(content, before);
        Integer views = (Integer) redisManager.getData(key);

        if (views != null) {
            content.addViews(-views);
        }
    }

    private String resolveClientIpKey(ViewsContent content, String clientIp) {
        return content.getViewsContentType().toString() + content.getId() + '_' + clientIp;
    }

    private String resolveViewsKey(ViewsContent content, String date) {
        return content.getViews().toString() + content.getId() + '_' + date;
    }

    public static String getClientIp(HttpServletRequest request) {
        return request.getHeader("x-forwarded-for");
    }

}
