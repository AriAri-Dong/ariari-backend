package com.ariari.ariari.domain.club.activity.dto.req;

import com.ariari.ariari.commons.enums.YnType;
import com.ariari.ariari.domain.club.activity.enums.AccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.mail.Multipart;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ClubActivitySaveReq {
    @Schema(description = "전체 공개인지, CLUB_MEMBER or All", example = "")
    @NotNull(message = "AccessType이 null일 수 없습니다.")
    private AccessType accessType;

    @Schema(description = "본문", example = "2025년 4월 10일 아리아리 회의")
    @NotNull(message = "Body가 null일 수 없습니다. 빈 문자열이라도 필요합니다.")
    private String body;

    @Schema(description = "첨부 이미지 리스트", example = "")
    private List<MultipartFile> images;
}
