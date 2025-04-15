package com.ariari.ariari.domain.recruitment.apply.temp.dto.res;

import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "임시 지원서 작성 성공 응답")
public class ApplyTempSaveRes {

    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(description = "임시 지원서 id", example = "673012345142938986")
    private Long id;

    public static ApplyTempSaveRes createRes(Long applyTempId) {
        return new ApplyTempSaveRes(applyTempId);
    }

}
