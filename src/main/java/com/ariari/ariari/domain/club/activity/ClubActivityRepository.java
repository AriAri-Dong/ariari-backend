package com.ariari.ariari.domain.club.activity;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityRepository extends JpaRepository<ClubActivity, Long> {
//    List<ClubActivity> findAllByClubMember(ClubMember reqClubMember);

    Page<ClubActivity> findByClub(Club club, Pageable pageable);
}
