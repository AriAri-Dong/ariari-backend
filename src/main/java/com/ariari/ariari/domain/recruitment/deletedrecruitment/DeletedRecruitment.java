package com.ariari.ariari.domain.recruitment.deletedrecruitment;

import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class DeletedRecruitment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deleted_recruitment_id")
    private Long id;

    private String title;
    private String body;
    private String imagePtah;
    private LocalDateTime dueDateTime;
    private Integer maxParticipants;
    private Long views = 0L;
    private Boolean isPortfolioRequired;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public static DeletedRecruitment fromRecruitment(Recruitment recruitment) {
        return DeletedRecruitment.builder()
                .title(recruitment.getTitle())
                .body(recruitment.getBody())
                .build();
    }

}
