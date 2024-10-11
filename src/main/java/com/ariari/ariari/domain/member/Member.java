package com.ariari.ariari.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private Long kakaoId;

    private String nickName;

    // private String profileUri

    // private String memberCode;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<GrantedAuthority> authorities = new HashSet<>();

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
