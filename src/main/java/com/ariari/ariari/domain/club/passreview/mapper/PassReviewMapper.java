package com.ariari.ariari.domain.club.passreview.mapper;

import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PassReviewMapper {
    List<PassReviewRes> findPassReviewOfClub(Long clubId, int size, int offset);
    int findPassReviewOfClubCount(Long clubId);
}