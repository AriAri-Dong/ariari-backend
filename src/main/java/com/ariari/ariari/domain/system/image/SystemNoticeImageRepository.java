package com.ariari.ariari.domain.system.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemNoticeImageRepository extends JpaRepository<SystemNoticeImage, Long> {
}
