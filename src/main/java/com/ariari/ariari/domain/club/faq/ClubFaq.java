package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.faq.enums.ClubFaqColorType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club_faq SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_faq_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ClubFaq extends LogicalDeleteEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public ClubFaq(String title, String body, String clubFaqClassification, ClubFaqColorType clubFaqColorType, Club club ) {
        this.title = title;
        this.body = body;
        this.clubFaqClassification = clubFaqClassification;
        this.clubFaqColorType = clubFaqColorType;
        this.club = club;
    }

}
