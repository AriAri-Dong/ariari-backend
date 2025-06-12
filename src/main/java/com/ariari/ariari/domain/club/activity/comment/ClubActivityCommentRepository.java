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
    @Query("update ClubActivityComment as cac set cac.member = null where cac.clubActivity.club= :club and cac.member = :member")
    void clubActivityCommentUpdate(@Param("club") Club club, @Param("member") Member member);

    @Modifying(clearAutomatically = true)
    @Query("update ClubActivityComment cac set cac.member= null where cac.member= :member")
    void updateMemberNull(Member member);

}
