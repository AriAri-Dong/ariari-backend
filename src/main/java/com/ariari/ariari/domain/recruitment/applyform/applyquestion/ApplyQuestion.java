package com.ariari.ariari.domain.recruitment.applyform.applyquestion;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ApplyQuestion {

    @Id @CustomPkGenerate
    @Column(name = "question_id")
    private Long id;

    @Column(length = 30)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

}
