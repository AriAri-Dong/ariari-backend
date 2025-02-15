package com.ariari.ariari.commons.manager;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
@Schema(description = "페이지네이션 정보")
public class PageInfo {

    @Schema(description = "가져온 데이터 크기", example = "10")
    private Integer contentSize;
    @Schema(description = "전체 데이터 크기", example = "25")
    private Integer totalSize;
    @Schema(description = "전체 페이지 크기", example = "3")
    private Integer totalPages;

    public static PageInfo fromPage(Page<?> page) {
        return PageInfo.builder()
                .contentSize(page.getNumberOfElements())
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
