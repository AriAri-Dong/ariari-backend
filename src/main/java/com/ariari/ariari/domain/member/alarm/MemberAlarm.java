package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE member_alarm SET deleted_date_time= CURRENT_TIMESTAMP WHERE member_alarm_id= ?")
@SQLRestriction("deleted_date_time is null")
public class MemberAlarm extends LogicalDeleteEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
