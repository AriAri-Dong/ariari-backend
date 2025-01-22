package com.ariari.ariari.domain.recruitment.apply;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.answer.ApplyAnswer;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE apply SET deleted_date_time= CURRENT_TIMESTAMP WHERE apply_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Apply extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "apply_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ApplyStatusType applyStatusType = ApplyStatusType.PENDENCY;

    @Setter
    private String fileUri;

    private String portfolioUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "apply", cascade = CascadeType.ALL)
    private List<ApplyAnswer> applyAnswers = new ArrayList<>();

    public Apply(String name, String portfolioUrl, Member member, Recruitment recruitment, List<ApplyAnswer> applyAnswers) {
        this.name = name;
        this.portfolioUrl = portfolioUrl;
        this.member = member;
        this.recruitment = recruitment;
        this.applyAnswers = applyAnswers;
    }

}
