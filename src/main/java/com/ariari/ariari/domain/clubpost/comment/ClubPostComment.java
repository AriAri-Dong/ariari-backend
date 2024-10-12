package com.ariari.ariari.domain.clubpost.comment;

import com.ariari.ariari.domain.clubpost.ClubPost;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubPostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_post_comment_id")
    private Long id;

    private String body;
    private Boolean secret;
    Long depth;
    Long order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_post_id")
    private ClubPost clubPost;

}
