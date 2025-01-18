package com.ariari.ariari.domain.club.financial.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.financial.FinancialRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.ListIterator;

@Data
@AllArgsConstructor
public class FinancialRecordListRes {

    private List<FinancialRecordData> financialRecordDataList;
    private PageInfo pageInfo;

    public static FinancialRecordListRes fromPage(Page<FinancialRecord> page, Long totalBeforeLast) {
        List<FinancialRecordData> list = page.getContent().stream().map(FinancialRecordData::fromEntity).toList();

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
