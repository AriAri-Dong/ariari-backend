package com.ariari.ariari.domain.club.activity.comment;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.clubmember.ClubMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivityComment {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_comment_id")
    private Long id;

    @Column(length = 200)
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

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
