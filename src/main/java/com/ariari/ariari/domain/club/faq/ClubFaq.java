package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubFaq {

    @Id @CustomPkGenerate
    @Column(name = "club_faq_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String body;

    private String clubFaqClassification;

    @Enumerated(EnumType.STRING)
    private ClubFaqColorType clubFaqColorType;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_member_id")
    private ClubMember clubMember;

    public ClubFaq(String title, String body, String clubFaqClassification, ClubFaqColorType clubFaqColorType, Club club, ClubMember clubMember) {
        this.title = title;
        this.body = body;
        this.clubFaqClassification = clubFaqClassification;
        this.clubFaqColorType = clubFaqColorType;
        this.club = club;
        this.clubMember = clubMember;
    }

}
