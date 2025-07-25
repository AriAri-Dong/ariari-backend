package com.ariari.ariari.domain.club.activity;


import com.ariari.ariari.commons.entity.image.ImageRepository;
import com.ariari.ariari.commons.exception.exceptions.DuplicateDataCreateException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.ClubAlarmManger;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityComment;
import com.ariari.ariari.domain.club.activity.comment.ClubActivityCommentRepository;
import com.ariari.ariari.domain.club.activity.comment.like.ClubActivityCommentLike;
import com.ariari.ariari.domain.club.activity.comment.like.ClubActivityCommentLikeRepository;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityCommentData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityData;
import com.ariari.ariari.domain.club.activity.dto.ClubActivityImageData;
import com.ariari.ariari.domain.club.activity.dto.req.ClubActivityModifyReq;
import com.ariari.ariari.domain.club.activity.dto.req.ClubActivitySaveReq;
import com.ariari.ariari.domain.club.activity.dto.req.CommentReq;
import com.ariari.ariari.domain.club.activity.dto.res.ClubActivityDetailRes;
import com.ariari.ariari.domain.club.activity.dto.res.ClubActivityListRes;
import com.ariari.ariari.domain.club.activity.image.ClubActivityImage;
import com.ariari.ariari.domain.club.activity.image.ClubActivityImageRepository;
import com.ariari.ariari.domain.club.activity.like.ClubActivityLike;
import com.ariari.ariari.domain.club.activity.like.ClubActivityLikeRepository;
import com.ariari.ariari.domain.club.activity.mapper.ClubActivityMapper;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.block.Block;
import com.ariari.ariari.domain.member.block.BlockRepository;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ariari.ariari.domain.club.activity.ClubActivityAssembler.assemble;
import static com.ariari.ariari.domain.club.activity.ClubActivityUtils.initializeFindClubActivityMap;
import static com.ariari.ariari.domain.club.activity.ClubActivityUtils.initializeSetBlockData;


// TODO : 리팩토링 필요

@Service
@Transactional
@RequiredArgsConstructor
public class ClubActivityService {

    private final ClubActivityRepository clubActivityRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FileManager fileManager;
    private final ClubActivityMapper clubActivityMapper;
    private final ClubActivityImageRepository clubActivityImageRepository;
    private final ImageRepository imageRepository;
    private final ClubActivityLikeRepository clubActivityLikeRepository;
    private final ClubActivityCommentRepository clubActivityCommentRepository;
    private final ClubActivityCommentLikeRepository clubActivityCommentLikeRepository;
    private final GroupedOpenApi clubActivity;
    private final BlockRepository blockRepository;
    private final MemberAlarmManger memberAlarmManger;
    private final ClubAlarmManger clubAlarmManger;

    public void saveClubActivityLike(Long reqMemberId, Long clubActivityId){
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);

        Optional<ClubActivityLike> clubActivityLikeOP = clubActivityLikeRepository.findByClubActivityAndMember(clubActivity, reqMember);
        if(clubActivityLikeOP.isPresent()){
            clubActivityLikeRepository.delete(clubActivityLikeOP.get());
        } else{
            ClubActivityLike clubActivityLike = new ClubActivityLike(clubActivity, reqMember);
            clubActivityLikeRepository.save(clubActivityLike);
        }
    }

    public void saveClubActivity(Long reqMemberId, Long clubId, ClubActivitySaveReq clubActivitySaveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);
        GlobalValidator.isLessThanMaxSize(clubActivitySaveReq.getImages(), 10);

        ClubActivity clubActivity = new ClubActivity(club, reqMember, clubActivitySaveReq.getAccessType(), clubActivitySaveReq.getBody());

        if(clubActivitySaveReq.getImages() != null && !clubActivitySaveReq.getImages().isEmpty()) {
            List<ClubActivityImage> images = new ArrayList<>();
            for(MultipartFile multipartFile : clubActivitySaveReq.getImages()) {
                String filePath = fileManager.saveFile(multipartFile, "club_activity_image");
                images.add(new ClubActivityImage(filePath, clubActivity));
            }

            clubActivity.setClubActivityImages(images);
        }

        clubActivityRepository.save(clubActivity);
    }

    public void modifyClubActivity(Long clubActivityId, Long reqMemberId, ClubActivityModifyReq clubActivityModifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        int originImagesCount = clubActivityMapper.findClubActivityImageCount(clubActivityId);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);
        GlobalValidator.isLessThanMaxSize(clubActivityModifyReq.getNewImages(), 10 - originImagesCount + clubActivityModifyReq.getDeletedImageIds().size());

        if(clubActivityModifyReq.getDeletedImageIds() != null && !clubActivityModifyReq.getDeletedImageIds().isEmpty()) {
            List<ClubActivityImage> deletedImages = clubActivityImageRepository.findAllById(clubActivityModifyReq.getDeletedImageIds());
            for(ClubActivityImage deletedImage : deletedImages) {
                if(!deletedImage.getClubActivity().equals(clubActivity)) {
                    throw new NotFoundEntityException();
                }
                clubActivityImageRepository.delete(deletedImage);
            }
        }

        if(clubActivityModifyReq.getNewImages() != null) {
            List<ClubActivityImage> newImages = new ArrayList<>();
            for(MultipartFile multipartFile : clubActivityModifyReq.getNewImages()) {
                String filePath = fileManager.saveFile(multipartFile, "club_activity_image");
                newImages.add(new ClubActivityImage(filePath, clubActivity));
            }
            clubActivity.getClubActivityImages().addAll(newImages);
        }

        clubActivityModifyReq.modifyClubActivity(clubActivity);
        clubActivityRepository.save(clubActivity);
    }

    public void deleteClubActivity(Long reqMemberId, Long clubActivityId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        List<ClubActivityImage> deletedImages = clubActivityImageRepository.findAllById(clubActivity.getClubActivityImages().stream().map(ClubActivityImage::getId).collect(Collectors.toList()));
        clubActivityImageRepository.deleteAll(deletedImages);
        clubActivityRepository.delete(clubActivity);
    }

    public ClubActivityListRes findClubActivityPage(Long reqMemberId, Long clubId, Pageable pageable) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if(reqMemberId == null){ // 비로그인 회원
            List<ClubActivityData> clubActivityDataList = clubActivityMapper.findClubActivityForNotMember(clubId,
                    pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
            int totalSize = clubActivityMapper.findClubActivityForNotMemberAndNotClubMemberCount(clubId,
                    pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
            for(ClubActivityData clubActivityData : clubActivityDataList) {
                List<ClubActivityImageData> clubActivityImageDataList = clubActivityMapper.findClubActivityImageByClubActivityId(clubActivityData.getId());
                clubActivityData.setClubActivityImageDataList(clubActivityImageDataList);
                clubActivityData.setMyLiked(false);
            }
            return ClubActivityListRes.fromClubActivityList(clubActivityDataList, totalSize, pageable.getPageSize());
        }
        else{
            Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

            List<ClubActivityData> clubActivityDataList = new ArrayList<>();
            int totalSize = 0;
            if(clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty() && !reqMember.isSuperAdmin()) { // 동아리 비회원
                clubActivityDataList = clubActivityMapper.findClubActivityForNotClubMember(clubId, reqMemberId,
                        pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
                totalSize = clubActivityMapper.findClubActivityForNotMemberAndNotClubMemberCount(clubId,
                        pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
            } else { // 동아리원
                clubActivityDataList = clubActivityMapper.findClubActivityForClubMember(clubId, reqMemberId,
                        pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
                totalSize = clubActivityMapper.findClubActivityForClubMemberCount(clubId,
                        pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
            }
            for(ClubActivityData clubActivityData : clubActivityDataList) {
                List<ClubActivityImageData> clubActivityImageDataList = clubActivityMapper.findClubActivityImageByClubActivityId(clubActivityData.getId());
                clubActivityData.setClubActivityImageDataList(clubActivityImageDataList);
            }
            return ClubActivityListRes.fromClubActivityList(clubActivityDataList, totalSize, pageable.getPageSize());
        }
    }

    public ClubActivityDetailRes readClubActivityDetail(Long reqMemberId, Long clubActivityId) {
        ClubActivityDetailRes clubActivityDetailRes = new ClubActivityDetailRes();
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);

        Map<ClubActivityComment, List<ClubActivityComment>> clubActivityCommentMap = new LinkedHashMap<>();
        Map<ClubActivityComment, Integer> clubActivityLikeCountMap = new HashMap<>();
        Map<ClubActivityComment, Set<Member>> clubActivityLikeMemberSetMap = new HashMap<>();

        List<ClubActivityComment> clubActivityComments = clubActivityCommentRepository.findByClubActivity(clubActivity);
        List<ClubActivityCommentLike> clubActivityCommentLikes = clubActivityCommentLikeRepository.findByClubActivityComment_ClubActivity(clubActivity);
        List<ClubActivityComment> parentClubActivityComments = clubActivityCommentRepository.findByClubActivityAndParentComment(clubActivity, null);

        initializeFindClubActivityMap(clubActivityCommentMap, clubActivityLikeCountMap, clubActivityLikeMemberSetMap, clubActivityComments, clubActivityCommentLikes);

        Member creatorMember = clubActivity.getMember();

        int likeCount = clubActivityLikeRepository.countByClubActivity(clubActivity);
        int commentCount = clubActivityCommentRepository.countByClubActivity(clubActivity);

        List<ClubMember> clubMembers = clubActivity.getClub().getClubMembers();
        Map<Member, ClubMember> clubMemberMap = clubMembers.stream()
                .collect(Collectors.toMap(ClubMember::getMember, Function.identity(), (existing, replacement) -> existing, LinkedHashMap::new));

        if(reqMemberId == null){
            assemble(clubActivityDetailRes, clubActivity, clubActivityCommentMap, clubActivityLikeCountMap,
                    parentClubActivityComments, creatorMember, likeCount, commentCount, clubMemberMap);
        }else{
            Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
            boolean isMyLiked = clubActivityLikeRepository.findByClubActivityAndMember(clubActivity, reqMember).isPresent();

            Set<Member> blockSet = new HashSet<>();
            List<Block> blockingList = reqMember.getBlockings();
            List<Block> blockedList = reqMember.getBlockeds();
            initializeSetBlockData(blockSet, blockingList, blockedList);

            assemble(clubActivityDetailRes, clubActivity, clubActivityCommentMap, clubActivityLikeCountMap, clubActivityLikeMemberSetMap,
                    parentClubActivityComments, creatorMember, likeCount, commentCount, clubMemberMap, reqMember, isMyLiked, blockSet);
        }

        return clubActivityDetailRes;
    }

    public void saveClubActivityComment(Long reqMemberId, Long clubActivityId, Long parentCommentId, CommentReq commentReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        if(parentCommentId != null){
            ClubActivityComment parentComment = clubActivityCommentRepository.findById(parentCommentId).orElseThrow(NotFoundEntityException::new);
            ClubActivityComment clubActivityComment = new ClubActivityComment(commentReq.getBody(), reqMember, clubActivity, parentComment);
            clubActivityCommentRepository.save(clubActivityComment);
            Member member = parentComment.getMember();
            memberAlarmManger.sendClubActivityComment(clubActivity.getClub().getId(), member);
        } else {
            ClubActivityComment clubActivityComment = new ClubActivityComment(commentReq.getBody(), reqMember, clubActivity);
            clubActivityCommentRepository.save(clubActivityComment);
            clubAlarmManger.sendClubActivity(clubActivity.getClub());
        }
    }

    public void modifyClubActivityComment(Long reqMemberId, Long clubActivityId, Long commentId, CommentReq commentReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        ClubActivityComment comment = clubActivityCommentRepository.findById(commentId).orElseThrow(NotFoundEntityException::new);
        comment.setBody(commentReq.getBody());
        clubActivityCommentRepository.save(comment);
    }

    public void deleteClubActivityComment(Long reqMemberId, Long clubActivityId, Long commentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        ClubActivityComment comment = clubActivityCommentRepository.findById(commentId).orElseThrow(NotFoundEntityException::new);
        if(comment.getParentComment() == null){
            List<ClubActivityComment> deletedComments = clubActivityCommentRepository.findAllByParentComment(comment);

            List<ClubActivityCommentLike> deletedLikes = clubActivityCommentLikeRepository.findByClubActivityCommentIn(deletedComments);
            clubActivityCommentLikeRepository.deleteAll(deletedLikes);

            clubActivityCommentRepository.deleteAll(deletedComments);
            clubActivityCommentRepository.delete(comment);
        } else{
            clubActivityCommentLikeRepository.findByClubActivityCommentAndMember(comment, reqMember)
                    .ifPresent(clubActivityCommentLikeRepository::delete); /// 좋아요가 존재하면 삭제
            clubActivityCommentRepository.delete(comment);
        }
    }

    public void saveClubActivityCommentLike(Long reqMemberId, Long clubActivityId, Long commentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubActivity.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);
        ClubActivityComment comment = clubActivityCommentRepository.findById(commentId).orElseThrow(NotFoundEntityException::new);

        Optional<ClubActivityCommentLike> clubActivityCommentLikeOP = clubActivityCommentLikeRepository.findByClubActivityCommentAndMember(comment, reqMember);
        if(clubActivityCommentLikeOP.isPresent()){
            clubActivityCommentLikeRepository.delete(clubActivityCommentLikeOP.get());
        } else {
            ClubActivityCommentLike clubActivityCommentLike = new ClubActivityCommentLike(comment, reqMember);
            clubActivityCommentLikeRepository.save(clubActivityCommentLike);
        }
    }

    public void saveClubActivityCommentBlock(Long reqMemberId, Long commentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubActivityComment comment = clubActivityCommentRepository.findById(commentId).orElseThrow(NotFoundEntityException::new);

        Optional<Block> blockOP = blockRepository.findByBlockedMemberAndBlockingMember(comment.getMember(), reqMember);
        if(blockOP.isPresent()){
            throw new DuplicateDataCreateException("중복 차단할 수 없습니다.");
        }

        Block block = new Block(reqMember, comment.getMember());
        blockRepository.save(block);
    }
}
