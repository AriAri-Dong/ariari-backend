package com.ariari.ariari.domain.recruitment;


import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.commons.manager.views.ViewsContent;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.bookmark.RecruitmentBookmark;
import com.ariari.ariari.domain.recruitment.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.image.RecruitmentImage;
import com.ariari.ariari.domain.recruitment.note.RecruitmentNote;
import com.ariari.ariari.domain.recruitment.recruitment.enums.RecruitmentStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE recruitment SET deleted_date_time= CURRENT_TIMESTAMP WHERE recruitment_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Recruitment extends LogicalDeleteEntity implements ViewsContent {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_id")
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 2000)
    private String body;

    @Setter
    private String posterUri;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProcedureType procedureType;

    @Setter
    private Boolean isEarlyClosed = Boolean.FALSE; // 조기 종료 여부

    private Integer limits;
    private Long views = 0L;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_form_id")
    private ApplyForm applyForm;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE)
    private List<Apply> applys = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE)
    private List<ApplyTemp> applyTemps = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.REMOVE)
    private List<RecruitmentImage> recruitmentImages = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
    private List<RecruitmentNote> recruitmentNotes = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL)
    private List<RecruitmentBookmark> recruitmentBookmarks = new ArrayList<>();

    @Override
    public void addViews(long n) {
        this.views += n;
    }

    @Override
    public ViewsContentType getViewsContentType() {
        return ViewsContentType.RECRUITMENT;
    }

    /**
     * for test
     */
    public Recruitment(String title, String body, Club club) {
        this.title = title;
        this.body = body;
        this.club = club;
    }

    public Recruitment(String title, String body, ProcedureType procedureType, Integer limits, LocalDateTime startDateTime, LocalDateTime endDateTime, Club club, ApplyForm applyForm, List<RecruitmentNote> recruitmentNotes) {
        this.title = title;
        this.body = body;
        this.procedureType = procedureType;
        this.limits = limits;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.club = club;
        this.applyForm = applyForm;
        this.recruitmentNotes = recruitmentNotes;
    }

    public boolean isRecruiting() {
        return !isEarlyClosed && startDateTime.isBefore(LocalDateTime.now()) && LocalDateTime.now().isBefore(endDateTime);
    }

    public RecruitmentStatusType getRecruitmentStatusType() {
        if (isRecruiting()) {
            return RecruitmentStatusType.OPEN;
        } else if (isEarlyClosed.equals(Boolean.FALSE) && LocalDateTime.now().isBefore(startDateTime)) {
            return RecruitmentStatusType.SCHEDULED;
        } else {
            return RecruitmentStatusType.CLOSED;
        }
    }

}
