package com.ariari.ariari.commons.entity.image;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SQLDelete(sql = "UPDATE image SET deleted_date_time= CURRENT_TIMESTAMP WHERE image_id= ?")
@SQLRestriction("deleted_date_time is null")
@Getter
public abstract class Image extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "image_id")
    private Long id;

    private String imageUri;

    public Image(String imageUri) {
        this.imageUri = imageUri;
    }

}
