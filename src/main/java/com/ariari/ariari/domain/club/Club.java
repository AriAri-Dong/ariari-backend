package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.commons.manager.views.ViewsContent;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.enums.RegionType;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubpost.ClubPost;
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
public class Club implements ViewsContent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    private String name;
    private String imagePath;
    private String introduction;

    @Enumerated(EnumType.STRING)
    private ClubCategoryType clubCategoryType;

    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Enumerated(EnumType.STRING)
    private ParticipantType participantType;

    private String clubScope;

    private String ScopeTypeName;

    private Long views = 0L;

    @Setter
    private Boolean hasRecruitment = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<ClubPost> clubPosts = new ArrayList<>();

    @OneToMany(mappedBy = "club")
    private List<Recruitment> recruitments = new ArrayList<>();


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
    public Club(String name, String introduction, ClubCategoryType clubCategoryType, School school) {
        this.name = name;
        this.introduction = introduction;
        this.clubCategoryType = clubCategoryType;
        this.school = school;
    }

}
