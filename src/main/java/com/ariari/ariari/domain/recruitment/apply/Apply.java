package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Apply {

    @Id @CustomPkGenerate
    @Column(name = "apply_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ApplyStatusType applyStatusType;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "apply")
    private List<ApplyAnswer> applyAnswers = new ArrayList<>();

}
