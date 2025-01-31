package com.ariari.ariari.domain.club.event.dto;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.event.ClubEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(description = "동아리 일정 리스트 응답")
public class ClubEventListRes {

    @Schema(description = "동아리 일정 데이터 리스트")
    private List<ClubEventData> clubEventDataList;
    private PageInfo pageInfo;

    public static ClubEventListRes createRes(Page<ClubEvent> page, Map<ClubEvent, List<ClubMember>> clubMemberMap, Map<ClubEvent, Long> attendeeCountMap) {
        return new ClubEventListRes(
                ClubEventData.fromEntities(page.getContent(), clubMemberMap, attendeeCountMap),
                PageInfo.fromPage(page)
        );
    }

}
