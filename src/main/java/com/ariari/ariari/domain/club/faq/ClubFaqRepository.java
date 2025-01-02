package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.domain.club.Club;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubFaqRepository extends JpaRepository<ClubFaq, Long> {

    Page<ClubFaq> findByClub(Club club, Pageable pageable);

}