package com.ariari.ariari.domain.club.dto.req;

import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.club.enums.ClubRegionType;
import com.ariari.ariari.domain.club.enums.ParticipantType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClubSearchCondition {

    private List<ClubCategoryType> clubCategoryTypes = new ArrayList<>();
    private List<ClubRegionType> clubRegionTypes = new ArrayList<>();
    private List<ParticipantType> participantTypes = new ArrayList<>();

}
