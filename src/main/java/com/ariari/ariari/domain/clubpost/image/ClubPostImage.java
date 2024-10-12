package com.ariari.ariari.domain.clubpost.image;


import com.ariari.ariari.domain.clubpost.ClubPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubPostImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_post_image_id")
    private Long id;

    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_post_id")
    private ClubPost clubPost;

}
