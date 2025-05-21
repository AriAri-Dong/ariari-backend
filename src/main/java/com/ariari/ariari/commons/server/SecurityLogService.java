package com.ariari.ariari.commons.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityLogService {

    private final SecurityAccessLogRepository securityAccessLogRepository;

    @Transactional
    public void SaveSecurityAccessLog(String ip, String endPoint, boolean isSuspicious) {
        securityAccessLogRepository.save(new SecurityAccessLog(ip, endPoint, isSuspicious));
    }

}
