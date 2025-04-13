package com.ariari.ariari.domain.club.clubmember;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.passreview.PassReview;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import com.ariari.ariari.domain.club.review.ClubReview;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

import static com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType.*;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_member SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_member_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubMember extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_member_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    @Enumerated(EnumType.STRING)
    private ClubMemberRoleType clubMemberRoleType;

    @Setter
    @Enumerated(EnumType.STRING)
    private ClubMemberStatusType clubMemberStatusType = ClubMemberStatusType.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

//    @OneToMany(mappedBy = "clubMember", cascade = CascadeType.REMOVE)
//    private List<ClubAnswer> clubAnswers;


    public static ClubMember createAdmin(Member member, Club club) {
        return new ClubMember(
                "동아리 대표",
                ADMIN,
                member,
                club
        );
    }

    public static ClubMember createGeneral(Apply apply) {
        return new ClubMember(
                apply.getName(),
                GENERAL,
                apply.getMember(),
                apply.getRecruitment().getClub()
        );
    }

    public ClubMember(String name, ClubMemberRoleType clubMemberRoleType, Member member, Club club) {
        this.name = name;
        this.clubMemberRoleType = clubMemberRoleType;
        this.member = member;
        this.club = club;
    }

    public boolean isHigherRoleTypeThan(ClubMember clubMember) {
        ClubMemberRoleType myRole = this.clubMemberRoleType;
        ClubMemberRoleType hisRole = clubMember.getClubMemberRoleType();
        if (myRole.equals(ADMIN)) {
            return true;
        } else return myRole.equals(MANAGER) && hisRole.equals(GENERAL);
    }

}
