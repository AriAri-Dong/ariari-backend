package com.ariari.ariari.domain.club.activity.like;

import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubActivityLikeRepository extends JpaRepository<ClubActivityLike, Long> {
    Optional<ClubActivityLike> findByClubActivityAndMember(ClubActivity clubActivity, Member reqMember);

    int countByClubActivity(ClubActivity clubActivity);

    List<ClubActivityLike> findByClubActivity(ClubActivity clubActivity);
}
