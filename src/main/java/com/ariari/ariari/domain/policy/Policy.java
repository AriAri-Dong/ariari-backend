package com.ariari.ariari.domain.policy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Policy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long id;

    private String title;

    private String body;

    private Integer orders;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

}
