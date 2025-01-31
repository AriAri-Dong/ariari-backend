package com.ariari.ariari.domain.school.dto;

import com.ariari.ariari.domain.school.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "학교 데이터")
public class SchoolData {

    @Schema(description = "학교 이름", example = "네이버대학교")
    private String name;

    public static SchoolData fromEntity(School school) {
        return new SchoolData(
                school.getName()
        );
    }

    public static List<SchoolData> fromEntities(List<School> schools) {
        return schools.stream().map(SchoolData::fromEntity).toList();
    }

}
