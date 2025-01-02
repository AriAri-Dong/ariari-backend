package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.entitydelete.LogicalDeleteEntity;
import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.commons.manager.views.ViewsContent;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.school.School;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Club implements ViewsContent, LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    @Column(length = 1000)
    private String body;

    @Setter
    private String profileUri;

    @Setter
    private String bannerUri;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubCategoryType clubCategoryType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubRegionType clubRegionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ParticipantType participantType;

    private Long views = 0L;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "club")
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Recruitment> recruitments = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<ApplyForm> applyForms = new ArrayList<>();


    @Override
    public void addViews(long n) {
        ;this.views += n;
    }

    @Override
    public ViewsContentType getViewsContentType() {
        return ViewsContentType.CLUB;
    }

    public Club(String name, String body, ClubCategoryType clubCategoryType, ClubRegionType clubRegionType, ParticipantType participantType, School school) {
        this.name = name;
        this.body = body;
        this.clubCategoryType = clubCategoryType;
        this.clubRegionType = clubRegionType;
        this.participantType = participantType;
        this.school = school;
    }

    @Override
    public void deleteLogically() {
        this.deletedDateTime = LocalDateTime.now();
    }
}
