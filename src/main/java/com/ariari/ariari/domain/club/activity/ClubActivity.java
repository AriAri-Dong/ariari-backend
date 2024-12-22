package com.ariari.ariari.domain.club.activity;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivity {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccessType accessType;

    @Column(length = 500)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

}
