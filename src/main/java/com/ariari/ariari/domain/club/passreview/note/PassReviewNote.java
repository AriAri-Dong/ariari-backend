package com.ariari.ariari.domain.club.passreview.note;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE pass_review_note SET deleted_date_time= CURRENT_TIMESTAMP WHERE pass_review_note_id= ?")
@SQLRestriction("deleted_date_time is null")
public class PassReviewNote extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_note_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String body;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pass_review_id")
    private PassReview passReview;

    public PassReviewNote(NoteType noteType, String title, String body){
        this.noteType = noteType;
        this.title = title;
        this.body = body;
    }

}
