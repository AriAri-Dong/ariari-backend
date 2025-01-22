package com.ariari.ariari.domain.recruitment.note;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE recruitment_note SET deleted_date_time= CURRENT_TIMESTAMP WHERE recruitment_note_id= ?")
@SQLRestriction("deleted_date_time is null")
public class RecruitmentNote extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_note_id")
    private Long id;

    @Column(length = 30)
    private String question;

    @Column(length = 100)
    private String answer;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    public RecruitmentNote(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
