package com.ariari.ariari.domain.system.image;

import com.ariari.ariari.commons.entity.image.Image;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.system.SystemNotice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SystemNoticeImage extends Image {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "system_notice_id")
    private SystemNotice systemNotice;

    public SystemNoticeImage(String imageUri, SystemNotice systemNotice) {
        super(imageUri);
        this.systemNotice = systemNotice;
    }
}
