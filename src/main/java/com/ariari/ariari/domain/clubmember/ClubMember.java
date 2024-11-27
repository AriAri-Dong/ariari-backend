package com.ariari.ariari.domain.clubmember;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.clubmember.enums.ClubMemberRoleType;
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

    private String name;
    private String phoneNumber;
    private String email;

    @Enumerated(EnumType.STRING)
    private ClubMemberRoleType clubMemberRoleType;
    private String clubMemberRoleName;
    private Boolean isInAttendance = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;



}
