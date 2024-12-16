package com.ariari.ariari.domain.club.event;

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
public class ClubEvent {

    @Id @CustomPkGenerate
    @Column(name = "club_event_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 200)
    private String body;

    private LocalDateTime eventDateTime;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

}
