package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityCommentRepository extends JpaRepository<ClubActivityComment ,Long> {
    List<ClubActivityComment> findAllByClubMember(ClubMember reqClubMember);
}
