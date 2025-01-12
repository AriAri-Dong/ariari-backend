package com.ariari.ariari.domain.club.notice;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class ClubNotice {

    @Id @CustomPkGenerate
    @Column(name = "club_notice_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String body;

    private Boolean isFixed = Boolean.FALSE;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @Setter
    @OneToMany(mappedBy = "clubNotice", cascade = CascadeType.PERSIST)
    private List<ClubNoticeImage> clubNoticeImages = new ArrayList<>();

    public ClubNotice(String title, String body, Club club, ClubMember clubMember) {
        this.title = title;
        this.body = body;
        this.club = club;
        this.clubMember = clubMember;
    }

    public void modify(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public void controlIsFixed() {
        if (isFixed) {
            isFixed = Boolean.FALSE;
        } else {
            isFixed = Boolean.TRUE;
        }
    }

}
