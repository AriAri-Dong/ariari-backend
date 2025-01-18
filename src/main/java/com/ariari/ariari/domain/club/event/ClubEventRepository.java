package com.ariari.ariari.domain.club.event;

import com.ariari.ariari.domain.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {

    Page<ClubEvent> findByClubOrderByEventDateTimeDesc(Club club, Pageable pageable);

}
