package com.ariari.ariari.domain.recruitment.image;

import com.ariari.ariari.domain.recruitment.Recruitment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RecruitmentImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_image_id")
    private Long id;

    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

}
