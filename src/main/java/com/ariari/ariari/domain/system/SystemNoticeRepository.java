package com.ariari.ariari.domain.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemNoticeRepository extends JpaRepository<SystemNotice, Long> {

    @Query("select s from SystemNotice as s order by s.createdDateTime desc")
    List<SystemNotice> findAllByOrderByCreatedAtDesc();
}
