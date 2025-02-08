package com.ariari.ariari.domain.school.school;

import com.ariari.ariari.domain.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    Page<School> findByNameContains(String query, Pageable pageable);

    Optional<School> findByEmail(String emailSuffix);

}
