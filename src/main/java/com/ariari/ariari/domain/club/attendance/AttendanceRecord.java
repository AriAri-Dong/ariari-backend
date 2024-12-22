package com.ariari.ariari.domain.club.attendance;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class AttendanceRecord {

    @Id @CustomPkGenerate
    @Column(name = "attendance_record_id")
    private Long id;

    private LocalDateTime recordDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

}
