package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubAnswer {

    @Id @CustomPkGenerate
    @Column(name = "club_answer_id")
    private Long id;

    @Setter
    @Column(length = 500)
    private String body;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_question_id")
    private ClubQuestion clubQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    public ClubAnswer(String body, ClubQuestion clubQuestion, ClubMember clubMember) {
        this.body = body;
        this.clubQuestion = clubQuestion;
        this.clubMember = clubMember;
    }

}
