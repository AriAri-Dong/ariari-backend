package com.ariari.ariari.domain.club.alarm;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.alarm.enums.ClubAlarmType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_alarm SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_alarm_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubAlarm extends LogicalDeleteEntity {

    @Id
    @CustomPkGenerate
    @Column(name = "club_alarm_id")
    private Long id;

    @Column(length = 100)
    private String title;

    private String uri;

    private Boolean isChecked = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    private ClubAlarm(Long id, String title, String uri, Club club, Boolean isChecked) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.club = club;
        this.isChecked = isChecked;  // 기본값 설정
    }

    public void MarkRead(){
        this.isChecked = true;
    }

}
