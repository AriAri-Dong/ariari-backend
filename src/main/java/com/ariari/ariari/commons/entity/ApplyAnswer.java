package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE apply_answer SET deleted_date_time= CURRENT_TIMESTAMP WHERE apply_answer_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ApplyAnswer extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "apply_answer_id")
    private Long id;

    @Column(length = 1000)
    private String body;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_question_id")
    private ApplyQuestion applyQuestion;

    public ApplyAnswer(String body, ApplyQuestion applyQuestion) {
        this.body = body;
        this.applyQuestion = applyQuestion;
    }

}
