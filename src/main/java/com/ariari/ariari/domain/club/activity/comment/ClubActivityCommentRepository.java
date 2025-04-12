package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityCommentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubActivityCommentRepository extends JpaRepository<ClubActivityComment ,Long> {
    List<ClubActivityComment> findAllByParentComment(ClubActivityComment comment);

    int countByClubActivity(ClubActivity clubActivity);

    List<ClubActivityComment> findByClubActivityAndParentComment(ClubActivity clubActivity, ClubActivityComment comment);

    List<ClubActivityComment> findByClubActivity(ClubActivity clubActivity);
}
