package com.ariari.ariari.domain.club.activity.comment;

<<<<<<< HEAD
import com.ariari.ariari.domain.club.clubmember.ClubMember;
=======
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityCommentData;
>>>>>>> feat/club-activity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityCommentRepository extends JpaRepository<ClubActivityComment ,Long> {
<<<<<<< HEAD
    List<ClubActivityComment> findAllByClubMember(ClubMember reqClubMember);
=======
    List<ClubActivityComment> findAllByParentComment(ClubActivityComment comment);

    int countByClubActivity(ClubActivity clubActivity);

    List<ClubActivityComment> findByClubActivityAndParentComment(ClubActivity clubActivity, ClubActivityComment comment);

    List<ClubActivityComment> findByClubActivity(ClubActivity clubActivity);
>>>>>>> feat/club-activity
}
