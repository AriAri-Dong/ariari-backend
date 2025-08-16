package com.ariari.ariari.domain.member;

import com.ariari.ariari.commons.commonentity.LogicalDeleteEntity;
import com.ariari.ariari.commons.commonentity.report.Report;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.commons.entity.ClubActivity;
import com.ariari.ariari.commons.entity.ClubActivityComment;
import com.ariari.ariari.commons.entity.ClubActivityCommentLike;
import com.ariari.ariari.commons.entity.ClubActivityLike;
import com.ariari.ariari.commons.entity.ClubBookmark;
import com.ariari.ariari.commons.entity.ClubMember;
import com.ariari.ariari.commons.entity.Attendance;
import com.ariari.ariari.commons.entity.ClubNotice;
import com.ariari.ariari.commons.entity.PassReview;
import com.ariari.ariari.commons.entity.ClubQuestion;
import com.ariari.ariari.commons.entity.ClubReview;
import com.ariari.ariari.commons.entity.MemberAlarm;
import com.ariari.ariari.commons.entity.Block;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.ariari.ariari.commons.entity.PointHistory;
import com.ariari.ariari.commons.entity.Apply;
import com.ariari.ariari.commons.entity.ApplyTemp;
import com.ariari.ariari.commons.entity.RecruitmentBookmark;
import com.ariari.ariari.commons.entity.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE member SET deleted_date_time= CURRENT_TIMESTAMP WHERE member_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Member extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private Long kakaoId;

    @Setter
    @Column(length = 20, unique = true)
    private String nickName;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProfileType profileType;

    @Column(nullable = false)
    private boolean isSuperAdmin;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Setter
    private LocalDateTime lastLoginDateTime = LocalDateTime.now();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    /* -------------------- reverse mapping  -------------------- */

    @OneToMany(mappedBy = "member")
    private List<PassReview> passReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubReview> clubReviews = new ArrayList<>();

    @OneToMany(mappedBy = "reporter")
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubNotice> clubNotices = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubQuestion> clubQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubActivity> clubActivities = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubActivityComment> clubActivityComments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<MemberAlarm> memberAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "blockingMember", cascade = CascadeType.REMOVE)
    private List<Block> blockings = new ArrayList<>();

    @OneToMany(mappedBy = "blockedMember", cascade = CascadeType.REMOVE)
    private List<Block> blockeds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<RecruitmentBookmark> recruitmentBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Apply> applys = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ApplyTemp> applyTemps = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubActivityLike> clubActivityLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubActivityCommentLike> clubActivityCommentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<PointHistory> pointHistories = new ArrayList<>();

    /* --------------------------------------------------- */

    public static Member createMember(Long kakaoId, String nickname) {
        Member member = new Member(kakaoId, nickname);
        member.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        return member;
    }

    public static Member createMember(Long kakaoId, String nickname, ProfileType profileType) {
        Member member = new Member(kakaoId, nickname, profileType);
        member.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        return member;
    }

    public Member(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public Member(Long kakaoId, String nickName) {
        this.kakaoId = kakaoId;
        this.nickName = nickName;
    }

    public Member(Long kakaoId, String nickName, ProfileType profileType) {
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.profileType = profileType;
    }

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
    }

    public void setKakaoIdNull() {
        this.kakaoId = null;
    }

}
