package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassReviewNoteRepository extends JpaRepository<PassReviewNote, Long> {
    Optional<List<PassReviewNote>> findByPassReviewAndNoteType(PassReview passReview, NoteType noteType);
}
