package com.ariari.ariari.domain.recruitment.apply.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyListRes {

    private List<ApplyData> applyDataList;
    private PageInfo pageInfo;

    public static ApplyListRes fromPage(Page<Apply> page) {
        return new ApplyListRes(
                ApplyData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
