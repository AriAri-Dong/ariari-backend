package com.ariari.ariari.domain.system.image;

import com.ariari.ariari.commons.entity.image.Image;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SystemNoticeImage extends Image {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_notice_id")
    private ClubNotice clubNotice;

    public SystemNoticeImage(String imageUri, ClubNotice clubNotice) {
        super(imageUri);
        this.clubNotice = clubNotice;
    }
}
