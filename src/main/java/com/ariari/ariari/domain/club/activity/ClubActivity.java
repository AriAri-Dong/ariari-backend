package com.ariari.ariari.domain.club.activity;


import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_activity SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_activity_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubActivity extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccessType accessType;

    @Column(length = 500)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

}
