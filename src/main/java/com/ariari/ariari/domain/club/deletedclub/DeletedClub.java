package com.ariari.ariari.domain.club.deletedclub;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.enums.RegionType;
import com.ariari.ariari.domain.school.School;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class DeletedClub {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deleted_club_id")
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

    private LocalDateTime createdDateTime;

    @CreationTimestamp
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

}
