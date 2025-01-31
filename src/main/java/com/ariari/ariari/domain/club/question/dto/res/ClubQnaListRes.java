package com.ariari.ariari.domain.club.question.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.dto.ClubQuestionData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 Q&N 리스트 응답")
public class ClubQnaListRes {

    @Schema(description = "동아리 Q&A 데이터 리스트")
    private List<ClubQuestionData> clubQuestionDataList;
    private PageInfo pageInfo;

    public static ClubQnaListRes fromEntities(Page<ClubQuestion> page) {
        return new ClubQnaListRes(
                ClubQuestionData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
