package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.enums.ViewsContentType;
import com.ariari.ariari.commons.manager.views.ViewsContent;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.club.activity.ClubActivity;
import com.ariari.ariari.domain.club.alarm.ClubAlarm;
import com.ariari.ariari.domain.club.event.ClubEvent;
import com.ariari.ariari.domain.club.faq.ClubFaq;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import com.ariari.ariari.domain.club.notice.ClubNotice;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.school.School;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE club SET deleted_date_time= CURRENT_TIMESTAMP WHERE club_id= ?")
@SQLRestriction("deleted_date_time is null")
public class Club extends LogicalDeleteEntity implements ViewsContent {

    @Id @CustomPkGenerate
    @Column(name = "club_id")
    private Long id;

    @Column(length = 20)
    private String name;

    @Setter
    @Column(length = 1000)
    private String body;

    @Setter
    private String profileUri;

    @Setter
    private String bannerUri;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubCategoryType clubCategoryType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClubRegionType clubRegionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ParticipantType participantType;

    private Long views = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;


    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubAlarm> clubAlarms = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubBookmark> clubBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Recruitment> recruitments = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ApplyForm> applyForms = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubNotice> clubNotices = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubEvent> clubEvents = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<FinancialRecord> financialRecords = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubActivity> clubActivitys = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubFaq> clubFaqs = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<ClubQuestion> clubQuestions = new ArrayList<>();

    @Override
    public void addViews(long n) {
        ;this.views += n;
    }

    @Override
    public ViewsContentType getViewsContentType() {
        return ViewsContentType.CLUB;
    }

    public Club(String name, String body, ClubCategoryType clubCategoryType, ClubRegionType clubRegionType, ParticipantType participantType, School school) {
        this.name = name;
        this.body = body;
        this.clubCategoryType = clubCategoryType;
        this.clubRegionType = clubRegionType;
        this.participantType = participantType;
        this.school = school;
    }

}
