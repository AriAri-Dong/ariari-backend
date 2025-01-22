package com.ariari.ariari.domain.club.activity.image;

import com.ariari.ariari.commons.image.Image;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
