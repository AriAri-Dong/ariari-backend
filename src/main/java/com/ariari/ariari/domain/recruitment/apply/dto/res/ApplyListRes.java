package com.ariari.ariari.domain.recruitment.apply.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.recruitment.apply.Apply;
import com.ariari.ariari.domain.recruitment.apply.dto.ApplyData;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApplyListRes {

    private List<ApplyData> applyDataList = new ArrayList<>();
    private PageInfo pageInfo;

    public static ApplyListRes fromPage(Page<Apply> page) {
        return new ApplyListRes(
                ApplyData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

    public static ApplyListRes fromTempPage(Page<ApplyTemp> page) {
        return new ApplyListRes(
                ApplyData.fromApplyTemps(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

}
