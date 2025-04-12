package com.ariari.ariari.domain.club.activity.comment.like;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubActivityCommentLikeRepository extends JpaRepository<ClubActivityCommentLike, Long> {
    Optional<ClubActivityCommentLike> findByClubActivityCommentAndMember(ClubActivityComment comment, Member reqMember);

    List<ClubActivityCommentLike> findByClubActivityCommentIn(List<ClubActivityComment> deletedComments);

    List<ClubActivityCommentLike> findByClubActivityComment_ClubActivity(ClubActivity clubActivity);
}
