package com.ariari.ariari.domain.member.member.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.dto.MemberData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberListRes {

    List<MemberData> memberDataList;

    public static MemberListRes createRes(List<Member> members) {
        return new MemberListRes(
                MemberData.fromEntities(members)
        );
    }

}
