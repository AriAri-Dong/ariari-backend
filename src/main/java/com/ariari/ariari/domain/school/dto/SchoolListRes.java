package com.ariari.ariari.domain.school.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.school.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "학교 리스트 응답")
public class SchoolListRes {

    @Schema(description = "학교 데이터 리스트")
    private List<SchoolData> schoolDataList = new ArrayList<>();
    private PageInfo pageInfo;

    public static SchoolListRes createRes(Page<School> page) {
        return new SchoolListRes(
                SchoolData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
