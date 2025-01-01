package com.ariari.ariari.domain.club.passreview.note;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PassReviewNote {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_note_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_review_id")
    private PassReview passReview;

    public PassReviewNote(NoteType noteType, String title, String body){
        this.noteType = noteType;
        this.title = title;
        this.body = body;
    }

}
