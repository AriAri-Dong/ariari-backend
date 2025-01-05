package com.ariari.ariari.commons.manager;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class PageInfo {

    private Integer contentSize;
    private Integer totalSize;
    private Integer totalPages;

    public static PageInfo fromPage(Page<?> page) {
        return PageInfo.builder()
                .contentSize(page.getContent().size())
                .totalSize((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public static PageInfo fromOther(Integer contentSize, Integer totalSize, Integer pageSize){
        return PageInfo.builder()
                .contentSize(contentSize)
                .totalSize(totalSize)
                .totalPages(totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1)
                .build();
    }

}
