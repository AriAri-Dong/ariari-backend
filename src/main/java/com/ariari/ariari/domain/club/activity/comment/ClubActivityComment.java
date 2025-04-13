package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
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
@SQLDelete(sql = "UPDATE club_activity_comment SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_activity_comment_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubActivityComment extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_comment_id")
    private Long id;

    @Setter
    @Column(length = 200)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id")
    private ClubActivity clubActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ClubActivityComment parentComment;

    public void modifyClubMember(){
        this.member = null;
    }

    public ClubActivityComment(String body, Member member, ClubActivity clubActivity) {
        this.body = body;
        this.member = member;
        this.clubActivity = clubActivity;
    }

    public ClubActivityComment(String body, Member member, ClubActivity clubActivity, ClubActivityComment parentComment) {
        this.body = body;
        this.member = member;
        this.clubActivity = clubActivity;
        this.parentComment = parentComment;
    }
}
