package com.ariari.ariari.domain.club.club.dto.req;

import com.ariari.ariari.domain.club.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.club.enums.ParticipantType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "동아리 검색 조건 (리스트 내에서는 or 검색되고, 리스트 간에는 and 검색됩니다.)")
public class ClubSearchCondition {

    @Schema(description = "동아리 카테고리 타입 리스트")
    private List<ClubCategoryType> clubCategoryTypes = new ArrayList<>();
    @Schema(description = "동아리 지역 타입 리스트")
    private List<ClubRegionType> clubRegionTypes = new ArrayList<>();
    @Schema(description = "동아리 참여 대상 타입 리스트")
    private List<ParticipantType> participantTypes = new ArrayList<>();

}
