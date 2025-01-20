package com.ariari.ariari.domain.club.review.repository;

import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.enums.IconType;
import com.ariari.ariari.domain.club.review.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByIconType(IconType iconType);
    Optional<List<Tag>> findByIconIn(List<Icon> icons);
}
