package com.ariari.ariari.domain.recruitment.applyform.applyquestion;


import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE apply_question SET deleted_date_time= CURRENT_TIMESTAMP WHERE apply_question_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ApplyQuestion extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "apply_question_id")
    private Long id;

    @Column(length = 50)
    private String body;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

    public ApplyQuestion(String body) {
        this.body = body;
    }

}
