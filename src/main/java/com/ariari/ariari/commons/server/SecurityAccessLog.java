package com.ariari.ariari.commons.server;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class SecurityAccessLog {
    @Id
    @CustomPkGenerate
    @Column(name = "security_access_log_id")
    private Long id;

    private String ipAddress;

    private String requestUri;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    private boolean isSuspicious;

    public SecurityAccessLog(String ipAddress, String requestUri, boolean isSuspicious) {
        this.ipAddress = ipAddress;
        this.requestUri = requestUri;
        this.isSuspicious = isSuspicious;
    }
}
