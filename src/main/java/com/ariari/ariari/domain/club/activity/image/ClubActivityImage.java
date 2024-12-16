package com.ariari.ariari.domain.club.activity.image;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivityImage {

    @Id @CustomPkGenerate
    @Column(name = "club_activity_image_id")
    private Long id;

    private String imageUri;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id")
    private ClubActivity clubActivity;

}
