package com.ariari.ariari.commons.entity;

import com.ariari.ariari.commons.commonentity.LogicalDeleteEntity;
import com.ariari.ariari.commons.pkgenerator.CustomPkGenerate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE system_notice SET deleted_date_time= CURRENT_TIMESTAMP WHERE system_notice_id= ?")
@SQLRestriction("deleted_date_time is null")
public class SystemNotice extends LogicalDeleteEntity {

    @Id
    @CustomPkGenerate
    @Column(name = "system_notice_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String body;

    @Setter
    @OneToMany(mappedBy = "systemNotice", cascade = CascadeType.ALL)
    private List<SystemNoticeImage> systemNoticeImages = new ArrayList<>();

    private SystemNotice(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public static SystemNotice create(String title, String body){
        return new SystemNotice(title, body);
    }


    public void modify(String title, String body) {
        this.title = title;
        this.body = body;
    }


}
