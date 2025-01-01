package com.ariari.ariari.domain.club.passreview.repository;

import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassReviewNoteRepository extends JpaRepository<PassReviewNote, Long> {
}
