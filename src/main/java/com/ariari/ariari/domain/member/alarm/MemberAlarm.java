package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberAlarm {

    @Id @CustomPkGenerate
    @Column(name = "member_alarm_id")
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 200)
    private String body;

    @Column(length = 200)
    private String extraBody;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private MemberAlarmType memberAlarmType;

    private String uri;

    private Boolean isChecked = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
