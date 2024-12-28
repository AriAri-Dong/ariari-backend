package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecruitmentBookmark {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public RecruitmentBookmark(Recruitment recruitment, Member member) {
        this.recruitment = recruitment;
        this.member = member;
    }

}
