package com.ariari.ariari.domain.recruitment.note;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RecruitmentNote {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_note_id")
    private Long id;

    @Column(length = 30)
    private String question;

    @Column(length = 100)
    private String answer;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

}
