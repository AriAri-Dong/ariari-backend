package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.question.answer.ClubAnswer;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE club_question SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_question_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubQuestion extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_question_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "clubQuestion", cascade = CascadeType.ALL)
    public ClubAnswer clubAnswer;

    public ClubQuestion(String title, String body, Club club, Member member) {
        this.title = title;
        this.body = body;
        this.club = club;
        this.member = member;
    }

}
