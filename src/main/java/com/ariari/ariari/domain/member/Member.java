package com.ariari.ariari.domain.member;

import com.ariari.ariari.commons.entitydelete.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.passreview.access.PassReviewAccess;
import com.ariari.ariari.domain.club.review.access.ClubReviewAccess;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import com.ariari.ariari.domain.member.block.Block;
import com.ariari.ariari.domain.member.enums.ProfileType;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.school.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
public class Member implements LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private Long kakaoId;

    @Setter
    @Column(length = 20, unique = true)
    private String nickName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProfileType profileType;

    private Integer point;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "member")
    private List<MemberAlarm> memberAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "blockingMember")
    private List<Block> blockings = new ArrayList<>();

    @OneToMany(mappedBy = "blockedMember")
    private List<Block> blockeds = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<RecruitmentBookmark> recruitmentBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Apply> applys = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<PassReviewAccess> passReviewAccessList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ClubReviewAccess> clubReviewAccessList = new ArrayList<>();


    public static Member createMember(Long kakaoId, String nickname) {
        Member member = new Member(kakaoId, nickname);
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

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
    }

    @Override
    public void deleteLogically() {
        this.deletedDateTime = LocalDateTime.now();
    }

}
