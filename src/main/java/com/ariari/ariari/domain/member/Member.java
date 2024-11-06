package com.ariari.ariari.domain.member;

import com.ariari.ariari.domain.alarm.Alarm;
import com.ariari.ariari.domain.apply.Apply;
import com.ariari.ariari.domain.block.Block;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubpost.ClubPost;
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
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long kakaoId;

    @Setter
    private String nickName;

    // private String profilePath

    // private String memberCode;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<GrantedAuthority> authorities = new HashSet<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "blockingMember", cascade = CascadeType.REMOVE)
    private List<Block> blockings = new ArrayList<>();

    @OneToMany(mappedBy = "blockedMember", cascade = CascadeType.REMOVE)
    private List<Block> blockeds = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<ClubPost> clubPosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Apply> applies = new ArrayList<>();


    public static Member createMember(Long kakaoId) {
        Member member = new Member(kakaoId);
        member.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        return member;
    }

    public Member(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public void addAuthority(GrantedAuthority authority) {
        this.authorities.add(authority);
    }

}
