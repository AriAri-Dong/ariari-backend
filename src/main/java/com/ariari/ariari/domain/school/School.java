package com.ariari.ariari.domain.school;


import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class School {

    @Id @CustomPkGenerate
    @Column(name = "school_id")
    private Long id;

    private String name;

    public School(String name) {
        this.name = name;
    }

}
