package com.ariari.ariari.domain.recruitment.recruitment;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentRepositoryCustom {

    List<Recruitment> findByClub(Club club);



}
