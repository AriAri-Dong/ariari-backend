package com.ariari.ariari.domain.club.activity.comment.like;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivityCommentLike {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_comment_id")
    private ClubActivityComment clubActivityComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ClubActivityCommentLike(ClubActivityComment clubActivityComment, Member member) {
        this.clubActivityComment = clubActivityComment;
        this.member = member;
    }
}
