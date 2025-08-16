package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.image.Image;
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
