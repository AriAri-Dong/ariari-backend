package com.ariari.ariari.domain.club.event.attendance;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Attendance {

    @Id @CustomPkGenerate
    @Column(name = "attendance_record_id")
    private Long id;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_event_id")
    private ClubEvent clubEvent;

    public Attendance(ClubMember clubMember, ClubEvent clubEvent) {
        this.clubMember = clubMember;
        this.clubEvent = clubEvent;
    }

}
