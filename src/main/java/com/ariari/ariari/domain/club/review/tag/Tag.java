package com.ariari.ariari.domain.club.review.tag;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.enums.IconType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE tag SET deleted_date_time= CURRENT_TIMESTAMP WHERE tag_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Tag extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 30)
    private String body;

    @Enumerated(EnumType.STRING)
    private IconType iconType;

    @Enumerated(EnumType.STRING)
    private Icon icon;

    public Tag(String body, Icon icon) {
        this.body = body;
        this.iconType = IconType.CLUBREVIEW;
        this.icon = icon;
    }
}
