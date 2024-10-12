package com.ariari.ariari.commons.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedImageRepository extends JpaRepository<DeletedImage, Long> {
}
