package com.ariari.ariari.domain.club.review;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.review.access.ClubReviewAccess;
import com.ariari.ariari.domain.club.review.dto.ClubReviewData;
import com.ariari.ariari.domain.club.review.dto.TagData;
import com.ariari.ariari.domain.club.review.dto.req.ClubReviewSaveReq;
import com.ariari.ariari.domain.club.review.enums.IconType;
import com.ariari.ariari.domain.club.review.repository.ClubReviewAccessRepository;
import com.ariari.ariari.domain.club.review.repository.ClubReviewRepository;
import com.ariari.ariari.domain.club.review.repository.ClubReviewTagRepository;
import com.ariari.ariari.domain.club.review.repository.TagRepository;
import com.ariari.ariari.domain.club.review.reviewtag.ClubReviewTag;
import com.ariari.ariari.domain.club.review.tag.Tag;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubReviewService {
    private final ClubReviewRepository clubReviewRepository;
    private final MemberRepository memberRepository;
    private final ClubReviewAccessRepository clubReviewAccessRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final TagRepository tagRepository;
    private final ClubReviewTagRepository clubReviewTagRepository;
    private final ClubRepository clubRepository;

    // 활동후기 목록 조회
    public List<ClubReviewData> searchClubReviewPage(Long reqMemberId, Long clubId, Pageable pageable){
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        Page<ClubReview> clubReviews = clubReviewRepository.findByClubMember_Club(club, pageable);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        return ClubReviewData.fromEntities(clubReviews, member);
    }

    // 활동후기 상세 조회
    public ClubReviewData findClubReviewDetail(Long reqMemberId, Long clubReviewId){
        ClubReview clubReview = clubReviewRepository.findById(clubReviewId).orElseThrow(NotFoundEntityException::new);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubReviewAccess clubReviewAccess = clubReviewAccessRepository.findByClubReviewAndMember(clubReview, member)
                .orElseThrow(NotFoundEntityException::new); // 권한 없음
        List<ClubReviewTag> clubReviewTags = clubReviewTagRepository.findByClubReview(clubReview);
        List<Tag> tags = clubReviewTags.stream().map(ClubReviewTag::getTag).toList();
        List<TagData> tagDataList = TagData.toTagDataList(tags);
        return ClubReviewData.toClubReviewData(clubReview, tagDataList);
    }

    // 클럽의 태그 통계 리스트
    public List<TagData> findTagStatisticsAtClub(Long clubId){
        List<ClubReviewTag> clubReviewTags = clubReviewTagRepository.findByClubId(clubId);
        List<Tag> tags = tagRepository.findByIconType(IconType.CLUBREVIEW);

        // 갯수 해시맵
        Map<Tag, Long> tagUsageCount = clubReviewTags.stream()
                .map(ClubReviewTag::getTag)
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));

        // 총 사이즈
        long totalTags = clubReviewTags.size();

        // 퍼센티지 해시맵
        Map<Tag, Double> tagUsagePercentage = tags.stream()
                .collect(Collectors.toMap(
                        tag -> tag,
                        tag -> tagUsageCount.getOrDefault(tag, 0L) * 100.0 / totalTags
                ));
        return TagData.toTagDataList(tags, tagUsagePercentage);
    }

    // 활동후기 작성시 필요한 태그 목록 반환
    public List<TagData> searchClubReviewTag(){
        List<Tag> tags = tagRepository.findByIconType(IconType.CLUBREVIEW);
        return TagData.toTagDataList(tags);
    }

    // 활동후기 접근 권한 생성
    public void accessClubReview(Long reqMemberId, Long clubReviewId){
        ClubReview clubReview = clubReviewRepository.findById(clubReviewId).orElseThrow(NotFoundEntityException::new);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (clubReviewAccessRepository.existsByClubReviewAndMember(clubReview, member)){
            throw new RuntimeException(); // 이미 권한 있는 경우 exception추가해야함
        }
        ClubReviewAccess clubReviewAccess = new ClubReviewAccess(member, clubReview);
        clubReviewAccessRepository.save(clubReviewAccess);
    }

    // 활동후기 생성
    public void saveClubReview(Long reqMemberId, ClubReviewSaveReq clubReviewSaveReq, Long clubId){
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, reqMemberId).orElseThrow(NotFoundEntityException::new);
        if(clubReviewRepository.existsByClubMember(clubMember)){
            throw new RuntimeException(); // 중복 작성 exception 추가해야함
        }
        List<Tag> tags = tagRepository.findByIconIn(clubReviewSaveReq.getIcons()).orElseThrow(NotFoundEntityException::new);
        ClubReview clubReview = clubReviewSaveReq.toEntity(clubMember, tags);
        clubReviewRepository.save(clubReview);
    }
}
