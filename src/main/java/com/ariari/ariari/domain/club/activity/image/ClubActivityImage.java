package com.ariari.ariari.domain.club.activity.image;

import com.ariari.ariari.commons.entity.image.Image;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ClubActivityImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_activity_id")
    private ClubActivity clubActivity;

    public ClubActivityImage(String imageUri, ClubActivity clubActivity) {
        super(imageUri);
        this.clubActivity = clubActivity;
    }

}
