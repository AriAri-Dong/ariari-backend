package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class FinancialRecord {

    @Id @CustomPkGenerate
    @Column(name = "financial_record_id")
    private Long id;

    private Long amount;

    @Column(length = 100)
    private String body;

    private LocalDateTime recordDateTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    public FinancialRecord(Long amount, String body, LocalDateTime recordDateTime, Club club) {
        this.amount = amount;
        this.body = body;
        this.recordDateTime = recordDateTime;
        this.club = club;
    }

    public void modify(Long amount, String body, LocalDateTime recordDateTime) {
        this.amount = amount;
        this.body = body;
        this.recordDateTime = recordDateTime;
    }

}
