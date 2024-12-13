package com.ariari.ariari.domain.applyform;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.applyform.applyquestion.ApplyQuestion;
import com.ariari.ariari.domain.club.Club;
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
public class ApplyForm {

    @Id @CustomPkGenerate
    @Column(name = "apply_form_id")
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "applyForm")
    private List<ApplyQuestion> applyQuestions = new ArrayList<>();

}
