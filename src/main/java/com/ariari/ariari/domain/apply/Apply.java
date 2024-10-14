package com.ariari.ariari.domain.apply;

import com.ariari.ariari.domain.apply.answer.Answer;
import com.ariari.ariari.domain.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Apply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplyStatusType applyStatusType;

    // 저장 상태 필드
    // 포트폴리오 파일 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    @OneToMany(mappedBy = "apply", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

}
