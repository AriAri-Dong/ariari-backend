package com.ariari.ariari.commons.image;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE image SET deleted_date_time= CURRENT_TIMESTAMP WHERE image_id= ?")
@SQLRestriction("deleted_dt IS NULL")
@Getter
public abstract class Image {

    @Id @CustomPkGenerate
    @Column(name = "image_id")
    private Long id;

    private String imageUri;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;

    public Image(String imageUri) {
        this.imageUri = imageUri;
    }

}
