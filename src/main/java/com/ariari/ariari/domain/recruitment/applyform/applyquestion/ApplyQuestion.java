package com.ariari.ariari.domain.recruitment.applyform.applyquestion;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyQuestion {

    @Id @CustomPkGenerate
    @Column(name = "question_id")
    private Long id;

    @Column(length = 50)
    private String body;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

    public ApplyQuestion(String body) {
        this.body = body;
    }

}
