package com.ariari.ariari.domain.apply.applyanswer;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.apply.Apply;
import com.ariari.ariari.domain.applyform.applyquestion.ApplyQuestion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyAnswer {

    @Id @CustomPkGenerate
    @Column(name = "apply_answer_id")
    private Long id;

    @Column(length = 200)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_id")
    private Apply apply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_question_id")
    private ApplyQuestion applyQuestion;

}
