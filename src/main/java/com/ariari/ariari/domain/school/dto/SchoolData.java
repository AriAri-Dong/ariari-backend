package com.ariari.ariari.domain.school.dto;

import com.ariari.ariari.domain.school.School;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolData {

    private String name;

    public static SchoolData fromEntity(School school) {
        return new SchoolData(
                school.getName()
        );
    }

}
