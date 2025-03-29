package com.ariari.ariari.domain.member.member.dto.res;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.dto.MemberData;
import com.ariari.ariari.domain.school.dto.SchoolData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "회원 상세 응답 (schoolData 는 회원의 학교 데이터입니다. 학교 인증을 안한 경우 null 입니다.)")
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
