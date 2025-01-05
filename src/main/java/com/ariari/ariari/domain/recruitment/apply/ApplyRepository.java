package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

    Optional<Apply> findByMemberAndRecruitment(Member member, Recruitment recruitment);

}
