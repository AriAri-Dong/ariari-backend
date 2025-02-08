package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.passreview.access.PassReviewAccess;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewData;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewNoteData;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.mapper.PassReviewMapper;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewAccessRepository;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewNoteRepository;
import com.ariari.ariari.domain.club.passreview.repository.PassReviewRepository;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassReviewService {
    private final PassReviewRepository passReviewRepository;
    private final PassReviewMapper passReviewMapper;
    private final PassReviewNoteRepository passReviewNoteRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final MemberRepository memberRepository;
    private final PassReviewAccessRepository passReviewAccessRepository;

    // search목록 find디테일 save저장 modify수정 remove제거
    public PassReviewListRes searchPassReviewPage(Long reqMemberId, Long clubId, Pageable pageable){
        //page : 현재 page? size : 페이지당 항목개수
        List<PassReviewRes> passReviewResList = passReviewMapper.findByClubAndReqMember(clubId, reqMemberId,
                pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
        Integer totalSize = passReviewMapper.countByClubAndReqMember(reqMemberId, clubId);
        return PassReviewListRes.fromPassReviewResList(passReviewResList, totalSize, pageable.getPageSize());
    }

    public PassReviewData findPassReviewDetail(Long passReviewId){
        PassReview passReview = passReviewRepository.findById(passReviewId).orElseThrow(NotFoundEntityException::new);
        List<PassReviewNote> documentPassReviewNoteList = passReviewNoteRepository.
                findByPassReviewAndNoteType(passReview, NoteType.DOCUMENT).orElseThrow(NotFoundEntityException::new);
        List<PassReviewNote> interviewPassReviewNoteList = passReviewNoteRepository.
                findByPassReviewAndNoteType(passReview, NoteType.INTERVIEW).orElseThrow(NotFoundEntityException::new);
        return PassReviewData.fromEntity(passReview, PassReviewNoteData.fromEntities(documentPassReviewNoteList),
                PassReviewNoteData.fromEntities(interviewPassReviewNoteList));
    }

    public void accessPassReivew(Long reqMemberId, Long passReviewId){
        PassReview passReview = passReviewRepository.findById(passReviewId).orElseThrow(NotFoundEntityException::new);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (passReviewAccessRepository.existsByPassReviewAndMember(passReview, member)){
            throw new RuntimeException(); // 이미 접근 권한 존재 예외처리
        }
        PassReviewAccess passReviewAccess = new PassReviewAccess(passReview, member);
        passReviewAccessRepository.save(passReviewAccess);
    }

    public void savePassReview(Long reqMemberId, PassReviewSaveReq passReviewSaveReq, Long clubId){
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, reqMemberId).orElseThrow(NotFoundEntityException::new);
        if (passReviewRepository.existsByClubMember(clubMember)){
            throw new RuntimeException(); // 중복 작성 예외처리
        }
        PassReview passReview = passReviewSaveReq.toEntity(clubMember);
        passReviewRepository.save(passReview);
    }

    //@Transactional(readOnly = false)
}
