package com.ariari.ariari.domain.system;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.system.enums.TermType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE system_term SET deleted_date_time= CURRENT_TIMESTAMP WHERE system_term_id= ?")
@SQLRestriction("deleted_date_time is null")
public class SystemTerm extends LogicalDeleteEntity {
    @Id
    @CustomPkGenerate
    @Column(name = "system_term_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "term_type", unique = true)
    private TermType termType;

    @Lob
    @Column(name = "body")
    private String body;

    public SystemTerm(TermType termType, String body) {
        this.termType = termType;
        this.body = body;
    }
}
