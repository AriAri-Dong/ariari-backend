package com.ariari.ariari.domain.club.notice.image;

import com.ariari.ariari.commons.image.Image;
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
public class ClubNoticeImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_notice_id")
    private ClubNotice clubNotice;

    public ClubNoticeImage(String imageUri, ClubNotice clubNotice) {
        super(imageUri);
        this.clubNotice = clubNotice;
    }

}
