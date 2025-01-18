package com.ariari.ariari.domain.club.review.tag;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.enums.IconType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@SQLRestriction("deleted_date_time is null")
@Getter
public class Tag {

    @Id @CustomPkGenerate
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 30)
    private String body;

    @Enumerated(EnumType.STRING)
    private IconType iconType;

    @Enumerated(EnumType.STRING)
    private Icon icon;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    private LocalDateTime deletedDateTime;
}
