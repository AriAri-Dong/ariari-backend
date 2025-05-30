package com.ariari.ariari.commons.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityAccessLogRepository extends JpaRepository<SecurityAccessLog, Long> {
}
