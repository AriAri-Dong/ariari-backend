package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(length = 200)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id")
    private ClubActivity clubActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ClubActivityComment parentComment;

}
