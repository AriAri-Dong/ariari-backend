package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.domain.club.question.ClubQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubAnswerRepository extends JpaRepository<ClubAnswer, Long> {

    Optional<ClubAnswer> findByClubQuestion(ClubQuestion clubQuestion);
}
