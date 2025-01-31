package com.ariari.ariari.domain.club.clubmember.dto.res;

import com.ariari.ariari.commons.manager.PageInfo;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.dto.ClubMemberData;
import com.ariari.ariari.domain.club.event.attendance.Attendance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "동아리 회원 리스트 응답")
public class ClubMemberListRes {

    @Schema(description = "동아리 회원 데이터 리스트")
    private List<ClubMemberData> clubMemberDataList;
    private PageInfo pageInfo;

    public static ClubMemberListRes createRes(Page<ClubMember> page) {
        return new ClubMemberListRes(
                ClubMemberData.fromEntities(page.getContent()),
                PageInfo.fromPage(page)
        );
    }

    public static ClubMemberListRes createResByAttendances(Page<Attendance> page) {
        List<Attendance> attendances = page.getContent();
        List<ClubMember> clubMembers = attendances.stream().map(Attendance::getClubMember).toList();

        return new ClubMemberListRes(
                ClubMemberData.fromEntities(clubMembers),
                PageInfo.fromPage(page)
        );
    }

}
