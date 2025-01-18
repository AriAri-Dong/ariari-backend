package com.ariari.ariari.domain.club.event;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 100)
    private String location;

    private LocalDateTime eventDateTime;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "clubEvent")
    private List<Attendance> attendances = new ArrayList<>();

    public ClubEvent(String title, String body, String location, LocalDateTime eventDateTime, Club club) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.eventDateTime = eventDateTime;
        this.club = club;
    }

    public void modify(String title, String body, String location, LocalDateTime eventDateTime) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.eventDateTime = eventDateTime;
    }

}
