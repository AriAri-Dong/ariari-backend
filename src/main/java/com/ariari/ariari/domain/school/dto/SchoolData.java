package com.ariari.ariari.domain.school.dto;

import com.ariari.ariari.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class SchoolData {

    private String name;
    private String email;

    public static SchoolData fromEntity(School school) {
        return new SchoolData(
                school.getName(),
                school.getEmail()
        );
    }

    public static List<SchoolData> fromEntities(List<School> schools) {
        return schools.stream().map(SchoolData::fromEntity).toList();
    }

}
