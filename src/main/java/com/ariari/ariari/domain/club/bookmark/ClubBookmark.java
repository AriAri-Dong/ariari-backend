package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubBookmark {

    @Id @CustomPkGenerate
    @Column(name = "club_bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public ClubBookmark(Member member, Club club) {
        this.member = member;
        this.club = club;
    }

}
