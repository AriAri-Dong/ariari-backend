package com.ariari.ariari.domain.member.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.dto.MemberData;
import com.ariari.ariari.domain.school.dto.SchoolData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDetailRes {

    private MemberData memberData;
    private SchoolData schoolData;

    public static MemberDetailRes createRes(Member member) {
        return new MemberDetailRes(
                MemberData.fromEntity(member),
                member.getSchool() == null ? null : SchoolData.fromEntity(member.getSchool())
        );
    }

}
