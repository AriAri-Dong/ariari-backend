package com.ariari.ariari.domain.club.activity.mapper;

import com.ariari.ariari.domain.club.activity.dto.ClubActivityData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityImageData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubActivityMapper {
    /// 활동후기 별 이미지 개수
    int findClubActivityImageCount(Long clubActivityId);

    /// 활동후기 별 이미지 리스트 불러오기
    List<ClubActivityImageData> findClubActivityImageByClubActivityId(Long clubActivityId);

    /// 아리아리 비회원 동아리별 활동후기 페이지네이션 조회
    List<ClubActivityData> findClubActivityForNotMember(Long clubId, int size, int offset);

    /// 아리아리 비회원, 클럽비회원 동아리별 활동후기 총 개수
    int findClubActivityForNotMemberAndNotClubMemberCount(Long clubId, int size, int offset);

    /// 동아리 비회원 동아리별 활동후기 조회
    List<ClubActivityData> findClubActivityForNotClubMember(Long clubId, Long memberId, int size, int offset);

    /// 동아리 회원 동아리별 활동후기 페이지네이션 조회
    List<ClubActivityData> findClubActivityForClubMember(Long clubId, Long memberId, int size, int offset);

    /// 동아리 회원 동아리별 활동후기 총 개수
    int findClubActivityForClubMemberCount(Long clubId, int size, int offset);
}
