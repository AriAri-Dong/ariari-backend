package com.ariari.ariari.domain.clubmember;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ClubMember {

    @Id @CustomPkGenerate
    @Column(name = "club_member_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ClubMemberRoleType clubMemberRoleType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubMemberStatusType clubMemberStatusType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;



}
