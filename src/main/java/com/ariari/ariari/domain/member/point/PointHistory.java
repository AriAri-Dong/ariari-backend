package com.ariari.ariari.domain.member.point;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE point_history SET deleted_date_time= CURRENT_TIMESTAMP WHERE point_history_id= ?")
@SQLRestriction("deleted_date_time is null")
public class PointHistory extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "point_history_id")
    private Long id;

    private Integer amount;

    @Column(length = 100)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
