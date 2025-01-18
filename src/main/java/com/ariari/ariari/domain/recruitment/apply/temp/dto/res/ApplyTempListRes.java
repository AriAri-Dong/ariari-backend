package com.ariari.ariari.domain.recruitment.apply.temp.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.ApplyTempData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ApplyTempListRes {

    private List<ApplyTempData> applyDataList;
    private PageInfo pageInfo;

    public static ApplyTempListRes fromPage(Page<ApplyTemp> page) {
        return new ApplyTempListRes(
                ApplyTempData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
