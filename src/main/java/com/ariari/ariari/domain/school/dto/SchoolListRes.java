package com.ariari.ariari.domain.school.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SchoolListRes {

    private List<SchoolData> schoolDataList = new ArrayList<>();
    private PageInfo pageInfo;

    public static SchoolListRes createRes(Page<School> page) {
        return new SchoolListRes(
                SchoolData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
