package com.ariari.ariari.domain.club.review.dto;

import com.ariari.ariari.domain.club.review.enums.Icon;
import com.ariari.ariari.domain.club.review.tag.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TagData {
    @Schema(description = "Tag id", example = "")
    private String id;
    @Schema(description = "body", example = "")
    private String body;
    @Schema(description = "태그명", example = "CAREER_PREPARATION, //취업준비에 도움이 돼요\n" +
            "    NETWORKING, //인간관계를 넓힐 수 있어요\n" +
            "    INTEREST_EXPLORATION, //관심분야를 탐구할 수 있어요\n" +
            "    SELF_DEVELOPMENT, //자기 계발에 도움이 돼요\n" +
            "    ACADEMIC_IMPROVEMENT, //전공 실력이나 성적을 높일 수 있어요\n" +
            "    HEALTH_ENHANCEMENT, //건강증진에 도움이 돼요\n" +
            "    DIVERSE_EXPERIENCE //다양한 경험을 할 수 있어요")
    private Icon icon;
    @Schema(description = "rate 태그 비율", example = "")
    private Double rate;

    public static List<TagData> toTagDataList(List<Tag> tags){
        List<TagData> tagDataList = new ArrayList<>();
        for(Tag tag : tags){
            tagDataList.add(TagData.toTagData(tag));
        }
        return tagDataList;
    }

    public static TagData toTagData(Tag tag){
        return TagData.builder()
                .id(Long.toString(tag.getId()))
                .body(tag.getBody())
                .icon(tag.getIcon())
                .build();
    }

    public static List<TagData> toTagDataList(List<Tag> tags, Map<Tag, Double> tagUsagePercentage){
        List<TagData> tagDataList = new ArrayList<>();
        for(Tag tag : tags){
            tagDataList.add(TagData.toTagData(tag, tagUsagePercentage.get(tag)));
        }
        return tagDataList;
    }

    public static TagData toTagData(Tag tag, Double rate){
        return TagData.builder()
                .id(Long.toString(tag.getId()))
                .body(tag.getBody())
                .icon(tag.getIcon())
                .rate(rate)
                .build();
    }
}
