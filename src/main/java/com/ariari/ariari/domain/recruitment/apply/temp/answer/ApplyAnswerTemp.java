package com.ariari.ariari.domain.recruitment.apply.temp.answer;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyAnswerTemp {

    @Id @CustomPkGenerate
    @Column(name = "apply_answer_temp_id")
    private Long id;

    @Column(length = 200)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_temp_id")
    private ApplyTemp applyTemp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_question_id")
    private ApplyQuestion applyQuestion;

}
