package com.ariari.ariari.domain.club.question.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.dto.ClubQuestionData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubQnaListRes {

    private List<ClubQuestionData> clubQuestionDataList;
    private PageInfo pageInfo;

    public static ClubQnaListRes fromEntities(Page<ClubQuestion> page) {
        return new ClubQnaListRes(
                ClubQuestionData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
