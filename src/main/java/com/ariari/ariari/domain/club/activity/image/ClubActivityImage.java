package com.ariari.ariari.domain.club.activity.image;

import com.ariari.ariari.commons.entity.image.Image;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE image SET deleted_date_time= CURRENT_TIMESTAMP WHERE image_id= ?")
@SQLRestriction("deleted_date_time is null")
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
