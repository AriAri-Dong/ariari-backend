package com.ariari.ariari.domain.member.member.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.dto.MemberDataA;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MemberListRes {

    List<MemberDataA> memberDataAList;

    public static MemberListRes createRes(List<Member> members) {
        return new MemberListRes(
                MemberDataA.fromEntities(members)
        );
    }

}
