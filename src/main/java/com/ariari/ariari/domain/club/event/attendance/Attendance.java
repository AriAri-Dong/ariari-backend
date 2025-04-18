package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE attendance SET deleted_date_time= CURRENT_TIMESTAMP WHERE attendance_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Attendance extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_event_id")
    private ClubEvent clubEvent;

    public Attendance(Member member, ClubEvent clubEvent) {
        this.member = member;
        this.clubEvent = clubEvent;
    }

    public void modifyMember(){
        this.member = null;
    }



}
