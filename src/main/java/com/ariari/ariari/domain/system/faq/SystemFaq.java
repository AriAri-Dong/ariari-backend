package com.ariari.ariari.domain.system.faq;

import com.ariari.ariari.commons.entity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import com.ariari.ariari.domain.system.faq.enums.SystemFaqStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE system_notice SET deleted_date_time= CURRENT_TIMESTAMP WHERE system_notice_id= ?")
@SQLRestriction("deleted_date_time is null")
public class SystemFaq extends LogicalDeleteEntity {

    @Id
    @CustomPkGenerate
    @Column(name = "system_faq_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 500)
    private String body;

    @Enumerated(EnumType.STRING)
    private SystemFaqStatusType systemFaqStatusType;

    private SystemFaq(String title, String body, SystemFaqStatusType systemFaqStatusType) {
        this.title = title;
        this.body = body;
        this.systemFaqStatusType = systemFaqStatusType;
    }

    public static SystemFaq create(String title, String body, SystemFaqStatusType  systemFaqStatusType){
        return new SystemFaq(title, body, systemFaqStatusType);
    }

    public void modify(String title, String body, SystemFaqStatusType systemFaqStatusType) {
        this.title = title;
        this.body = body;
        this.systemFaqStatusType = systemFaqStatusType;
    }
}
