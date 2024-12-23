package com.ariari.ariari.domain.recruitment.applyform;

import com.ariari.ariari.domain.club.Club;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyFormRepository extends JpaRepository<ApplyForm, Long> {

    @EntityGraph(attributePaths = "applyQuestions")
    Optional<ApplyForm> findFirstByClubOrderByCreatedDateTimeDesc(Club club);

}
