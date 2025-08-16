package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.image.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class RecruitmentImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    public RecruitmentImage(String imageUri, Recruitment recruitment) {
        super(imageUri);
        this.recruitment = recruitment;
    }

}
