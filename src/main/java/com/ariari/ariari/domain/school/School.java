package com.ariari.ariari.domain.school;


import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE school SET deleted_date_time= CURRENT_TIMESTAMP WHERE school_id= ?")
@SQLRestriction("deleted_date_time is null")
public class School extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "school_id")
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    @Column(length = 30, nullable = false)
    private String email;

    public School(String name) {
        this.name = name;
    }

    public School(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
