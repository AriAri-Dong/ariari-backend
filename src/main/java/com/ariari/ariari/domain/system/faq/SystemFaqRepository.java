package com.ariari.ariari.domain.system.faq;

import com.ariari.ariari.domain.system.SystemNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemFaqRepository extends JpaRepository<SystemFaq, Long> {
}
