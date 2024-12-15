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
import com.ariari.ariari.domain.clubmember.ClubMember;
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
@AllArgsConstructor
@Builder
@Getter
public class Club implements ViewsContent, LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "club_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 1000)
    private String body;

    private String profileUri;
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

    /**
     * for test
     */
    public Club(String name, String body, ClubCategoryType clubCategoryType, School school) {
        this.name = name;
        this.body = body;
        this.clubCategoryType = clubCategoryType;
        this.school = school;
    }

    @Override
    public void deleteLogically() {
        this.deletedDateTime = LocalDateTime.now();
    }
}
