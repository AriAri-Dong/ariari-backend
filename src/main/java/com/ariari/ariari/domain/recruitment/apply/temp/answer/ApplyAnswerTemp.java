package com.ariari.ariari.domain.recruitment.apply.temp.answer;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyAnswerTemp {

    @Id @CustomPkGenerate
    @Column(name = "apply_answer_temp_id")
    private Long id;

    @Setter
    @Column(length = 200)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_temp_id")
    private ApplyTemp applyTemp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_question_id")
    private ApplyQuestion applyQuestion;

    public ApplyAnswerTemp(String body, ApplyQuestion applyQuestion) {
        this.body = body;
        this.applyQuestion = applyQuestion;
    }

}
