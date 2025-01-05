package com.ariari.ariari.domain.club.passreview.mapper;

import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PassReviewMapper {
    List<PassReviewRes> findByClubAndReqMember(Long clubId, Long memberId);
    Integer countByClubAndReqMember(Long clubId, Long memberId);
}