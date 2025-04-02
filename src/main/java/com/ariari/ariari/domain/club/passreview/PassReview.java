package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.passreview.enums.InterviewRatioType;
import com.ariari.ariari.domain.club.passreview.enums.InterviewType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE pass_review SET deleted_date_time= CURRENT_TIMESTAMP WHERE pass_review_id= ?")
@SQLRestriction("deleted_date_time is null")
public class PassReview extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "pass_review_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    private ProcedureType procedureType;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    @Enumerated(EnumType.STRING)
    private InterviewRatioType interviewRatioType;

    private Integer interviewMood;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "passReview", cascade = CascadeType.PERSIST)
    private List<PassReviewNote> passReviewNotes = new ArrayList<>();

    public PassReview(String title, ProcedureType procedureType, InterviewType interviewType, InterviewRatioType interviewRatioType,
                      Integer interviewMood, Club club, Member member) {
        this.title = title;
        this.procedureType = procedureType;
        this.interviewType = interviewType;
        this.interviewRatioType = interviewRatioType;
        this.interviewMood = interviewMood;
        this.club = club;
        this.member = member;
    }




}
