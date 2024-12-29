package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

    @Query("select a from Apply a where a.member= :member order by a.createdDateTime desc")
    Page<Apply> findByMember(Member member, Pageable pageable);

}
