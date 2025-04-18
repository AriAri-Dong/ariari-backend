package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityCommentRepository extends JpaRepository<ClubActivityComment ,Long> {

    List<ClubActivityComment> findAllByParentComment(ClubActivityComment comment);

    int countByClubActivity(ClubActivity clubActivity);

    List<ClubActivityComment> findByClubActivityAndParentComment(ClubActivity clubActivity, ClubActivityComment comment);

    List<ClubActivityComment> findByClubActivity(ClubActivity clubActivity);

    @Modifying
    @Query("update ClubActivity as ca set ca.member = null where ca.club= :club and ca.member = :member")
    void clubActivityCommentUpdate(@Param("club") Club club, @Param("member") Member member);
}
