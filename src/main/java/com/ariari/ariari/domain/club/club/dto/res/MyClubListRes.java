package com.ariari.ariari.domain.club.club.dto.res;

import com.ariari.ariari.domain.club.club.dto.MyClubData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyClubListRes {

    @Schema(description = "내 동아리 데이터 리스트")
    private List<MyClubData> myClubDataList;

    public static MyClubListRes createRes(List<MyClubData> myClubDataList) {
        return new MyClubListRes(myClubDataList);
    }

}
