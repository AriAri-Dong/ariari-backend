package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
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
public class ApplyTemp {

    @Id @CustomPkGenerate
    @Column(name = "apply_temp_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "applyTemp")
    private List<ApplyAnswerTemp> applyAnswerTemps = new ArrayList<>();

}
