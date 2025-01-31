package com.ariari.ariari.domain.club.clubmember.dto.req;

import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "동아리 회원 상태 수정 형식")
public class ClubMemberStatusModifyReq {

    private ClubMemberStatusType clubMemberStatusType;

    public void modifyStatusType(List<ClubMember> clubMembers) {
        for (ClubMember clubMember : clubMembers) {
            clubMember.setClubMemberStatusType(clubMemberStatusType);
        }
    }

}
