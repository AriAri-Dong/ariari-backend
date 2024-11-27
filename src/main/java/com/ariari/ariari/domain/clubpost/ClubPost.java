package com.ariari.ariari.domain.clubpost;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.clubpost.comment.ClubPostComment;
import com.ariari.ariari.domain.clubpost.image.ClubPostImage;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.clubpost.enums.ClubPostType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class ClubPost {

    @Id @CustomPkGenerate
    @Column(name = "club_post_id")
    private Long id;

    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private ClubPostType clubPostType;

    private Integer orders;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "clubPost")
    private List<ClubPostImage> clubPostImages = new ArrayList<>();

    @OneToMany(mappedBy = "clubPost", cascade = CascadeType.REMOVE)
    private List<ClubPostComment> clubPostComments = new ArrayList<>();

}
