package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.image.Image;
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
