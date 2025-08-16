package com.ariari.ariari.domain.club.activity.image;

import com.ariari.ariari.commons.entity.ClubActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubActivityImageRepository extends JpaRepository<ClubActivityImage, Long> {
}
