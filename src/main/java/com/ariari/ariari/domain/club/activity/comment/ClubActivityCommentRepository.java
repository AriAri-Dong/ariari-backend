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
    @Query(value = """
    UPDATE club_activity_comment cac
    JOIN club_activity ca ON cac.club_activity_id = ca.club_activity_id
    SET cac.member_id = NULL
    WHERE ca.club_id = :clubId
      AND cac.member_id = :memberId
      AND cac.deleted_date_time IS NULL
    """, nativeQuery = true)
    void clubActivityCommentUpdate(@Param("clubId") Long clubId, @Param("memberId") Long memberId);



    @Modifying(clearAutomatically = true)
    @Query("update ClubActivityComment cac set cac.member= null where cac.member= :member")
    void updateMemberNull(Member member);

}
