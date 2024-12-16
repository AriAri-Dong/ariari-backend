package com.ariari.ariari.domain.club.review.tag;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Tag {

    @Id @CustomPkGenerate
    @Column(name = "tag_id")
    private Long id;

    @Column(length = 30)
    private String body;

    private String icon;

}
