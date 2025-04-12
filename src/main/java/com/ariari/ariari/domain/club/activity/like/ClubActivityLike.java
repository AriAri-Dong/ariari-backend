package com.ariari.ariari.domain.club.activity.like;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"club_activity_id", "member_id"})
        }
)
public class ClubActivityLike {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id")
    private ClubActivity clubActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ClubActivityLike(ClubActivity clubActivity, Member member) {
        this.clubActivity = clubActivity;
        this.member = member;
    }
}
