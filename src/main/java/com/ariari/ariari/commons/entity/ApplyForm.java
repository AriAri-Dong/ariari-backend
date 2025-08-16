package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE apply_form SET deleted_date_time= CURRENT_TIMESTAMP WHERE apply_form_id= ?")
@SQLRestriction("deleted_date_time is null")
public class ApplyForm extends LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "apply_form_id")
    private Long id;

    private Boolean requiresPortfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "applyForm", cascade = CascadeType.ALL)
    private List<ApplyQuestion> applyQuestions = new ArrayList<>();

    public ApplyForm(Club club, Boolean requiresPortfolio, List<ApplyQuestion> applyQuestions) {
        this.club = club;
        this.requiresPortfolio = requiresPortfolio;
        this.applyQuestions = applyQuestions;
    }

    public Map<Long, ApplyQuestion> getApplyQuestionMap() {
        HashMap<Long, ApplyQuestion> applyQuestionMap = new HashMap<>();
        for (ApplyQuestion applyQuestion : this.applyQuestions) {
            applyQuestionMap.put(applyQuestion.getId(), applyQuestion);
        }
        return applyQuestionMap;
    }

    public Map<String, Long> getApplyQuestionBodyMap() {
        HashMap<String, Long> applyQuestionMap = new HashMap<>();
        for (ApplyQuestion applyQuestion : this.applyQuestions) {
            applyQuestionMap.put(applyQuestion.getBody(), applyQuestion.getId());
        }
        return applyQuestionMap;
    }

}
