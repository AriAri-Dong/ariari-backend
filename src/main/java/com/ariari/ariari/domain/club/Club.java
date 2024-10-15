package com.ariari.ariari.domain.club;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.enums.RegionType;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubpost.ClubPost;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.school.School;
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
public class Club {

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

    private Long viewCount;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubPost> clubPosts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Recruitment> recruitments = new ArrayList<>();

}
