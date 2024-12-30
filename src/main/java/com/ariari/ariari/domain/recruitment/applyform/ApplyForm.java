package com.ariari.ariari.domain.recruitment.applyform;

import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.applyform.applyquestion.ApplyQuestion;
import com.ariari.ariari.domain.club.Club;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @OneToMany(mappedBy = "applyForm", cascade = CascadeType.PERSIST)
    private List<ApplyQuestion> applyQuestions = new ArrayList<>();

    public ApplyForm(Club club, List<ApplyQuestion> applyQuestions) {
        this.club = club;
        this.applyQuestions = applyQuestions;
    }


    public Map<Long, ApplyQuestion> getApplyQuestionMap() {
        HashMap<Long, ApplyQuestion> applyQuestionMap = new HashMap<>();
        for (ApplyQuestion applyQuestion : this.applyQuestions) {
            applyQuestionMap.put(applyQuestion.getId(), applyQuestion);
        }
        return applyQuestionMap;
    }

}
