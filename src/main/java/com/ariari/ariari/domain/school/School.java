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

    @Column(length = 20)
    private String name;

    @Column(length = 30)
    private String email;

    public School(String name) {
        this.name = name;
    }

}
