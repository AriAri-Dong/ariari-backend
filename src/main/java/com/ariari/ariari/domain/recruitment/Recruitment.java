package com.ariari.ariari.domain.recruitment;


import com.ariari.ariari.commons.entitydelete.LogicalDeleteEntity;
import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.commons.manager.views.ViewsContent;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.apply.Apply;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.recruitment.enums.ProcedureType;
import com.ariari.ariari.domain.recruitment.image.RecruitmentImage;
import com.ariari.ariari.domain.recruitment.recruitmentnote.RecruitmentNote;
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
public class Recruitment implements ViewsContent, LogicalDeleteEntity {

    @Id @CustomPkGenerate
    @Column(name = "recruitment_id")
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 2000)
    private String body;

    private String posterUri;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProcedureType procedureType;

    private Boolean isActivated = Boolean.TRUE;

    private Integer limits;
    private Long views = 0L;

    private LocalDateTime endDateTime;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    private LocalDateTime deletedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "recruitment")
    private List<RecruitmentImage> recruitmentImages = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment")
    private List<Apply> applys = new ArrayList<>();

    @OneToMany(mappedBy = "recruitment")
    private List<RecruitmentNote> recruitmentNotes = new ArrayList<>();


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

    @Override
    public void deleteLogically() {
        this.deletedDateTime = LocalDateTime.now();
    }

}
