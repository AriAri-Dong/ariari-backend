package com.ariari.ariari.domain.member.member.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.dto.MemberSchoolData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MemberSchoolListRes {

    List<MemberSchoolData> memberDataList;

    public static MemberSchoolListRes createRes(List<Member> members) {
        return new MemberSchoolListRes(MemberSchoolData.fromEntities(members)
        );
    }

}