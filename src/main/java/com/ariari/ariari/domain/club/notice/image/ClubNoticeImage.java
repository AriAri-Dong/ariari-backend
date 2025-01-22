package com.ariari.ariari.domain.club.notice.image;

import com.ariari.ariari.commons.entity.image.Image;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
