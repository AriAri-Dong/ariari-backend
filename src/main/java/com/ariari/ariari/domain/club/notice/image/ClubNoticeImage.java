package com.ariari.ariari.domain.club.notice.image;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class ClubNoticeImage {

    @Id @CustomPkGenerate
    @Column(name = "club_notice_image_id")
    private Long id;

    private String imageUri;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_notice_id")
    private ClubNotice clubNotice;

    public ClubNoticeImage(String imageUri, ClubNotice clubNotice) {
        this.imageUri = imageUri;
        this.clubNotice = clubNotice;
    }

}
