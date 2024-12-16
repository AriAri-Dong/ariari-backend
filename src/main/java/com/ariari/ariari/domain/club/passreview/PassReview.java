package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class PassReview {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @Enumerated(EnumType.STRING)
    private InterviewRatioType interviewRatioType;

    private Integer interviewMood;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

}
