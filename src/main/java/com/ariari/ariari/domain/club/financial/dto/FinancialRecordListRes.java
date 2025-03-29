package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@Data
@AllArgsConstructor
@Schema(description = "동아리 회계 리스트 응답")
public class FinancialRecordListRes {

    @Schema(description = "동아리 회계 데이터 리스트")
    private List<FinancialRecordData> financialRecordDataList;
    private PageInfo pageInfo;

    public static FinancialRecordListRes fromPage(Page<FinancialRecord> page, Long totalBeforeLast) {
        List<FinancialRecordData> list = page.getContent().stream().map(FinancialRecordData::fromEntity).toList();
        if(list.isEmpty()){
            return new FinancialRecordListRes(
                    Collections.emptyList(),
                    PageInfo.fromPage(page)
            );
        }
        // setBalance
        ListIterator<FinancialRecordData> iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
            FinancialRecordData ptr = iterator.previous();
            totalBeforeLast += ptr.getAmount();
            ptr.setBalance(totalBeforeLast);
        }

        return new FinancialRecordListRes(
                list,
                PageInfo.fromPage(page)
        );
    }

}
