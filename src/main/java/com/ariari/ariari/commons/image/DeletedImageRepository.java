package com.ariari.ariari.commons.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DeletedImageRepository extends JpaRepository<DeletedImage, Long> {

    List<DeletedImage> findByCreatedDateTimeBefore(LocalDateTime localDateTime);

}
