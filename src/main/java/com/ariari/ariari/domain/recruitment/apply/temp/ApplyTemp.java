package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.temp.answer.ApplyAnswerTemp;
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
@SQLDelete(sql = "UPDATE apply_temp SET deleted_date_time= CURRENT_TIMESTAMP WHERE apply_temp_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ApplyTemp extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "apply_temp_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    private String fileUri;

    private String portfolioUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "applyTemp", cascade = CascadeType.ALL)
    private List<ApplyAnswerTemp> applyAnswerTemps = new ArrayList<>();

    public ApplyTemp(String name, String portfolioUrl, Member member, Recruitment recruitment, List<ApplyAnswerTemp> applyAnswerTemps) {
        this.name = name;
        this.portfolioUrl = portfolioUrl;
        this.member = member;
        this.recruitment = recruitment;
        this.applyAnswerTemps = applyAnswerTemps;
    }

    public void modify(String name, String portfolioUrl) {
        this.name = name;
        this.portfolioUrl = portfolioUrl;
    }

}
