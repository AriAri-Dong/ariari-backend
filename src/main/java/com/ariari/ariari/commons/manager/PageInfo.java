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
                .contentSize(page.getSize())
                .totalSize((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
