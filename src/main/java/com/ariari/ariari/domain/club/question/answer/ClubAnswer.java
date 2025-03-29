package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_answer SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_answer_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubAnswer extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_answer_id")
    private Long id;

    @Setter
    @Column(length = 500)
    private String body;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_question_id")
    private ClubQuestion clubQuestion;


    public ClubAnswer(String body, ClubQuestion clubQuestion ) {
        this.body = body;
        this.clubQuestion = clubQuestion;
    }

}
