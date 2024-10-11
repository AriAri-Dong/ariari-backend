package com.ariari.ariari.domain.alarm;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Alarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private String title;
    private String body;
    private String path;
    private Boolean isChecked = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

}
