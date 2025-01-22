package com.ariari.ariari.domain.club.notice;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_notice SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_notice_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubNotice extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_notice_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String body;

    private Boolean isFixed = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    @Setter
    @OneToMany(mappedBy = "clubNotice", cascade = CascadeType.ALL)
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
