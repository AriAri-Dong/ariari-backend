package com.ariari.ariari.domain.club.passreview;

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
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
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
        List<PassReviewRes> passReviewResList = passReviewMapper.findByClubAndReqMember(clubId, reqMemberId);
        Integer totalSize = passReviewMapper.countByClubAndReqMember(reqMemberId, clubId);
        return PassReviewListRes.fromPassReviewResList(passReviewResList, totalSize, pageable.getPageSize());
    }

    public PassReviewData findPassReviewDetail(Long passReviewId){
        PassReview passReview = passReviewRepository.findById(passReviewId).orElseThrow(RuntimeException::new);
        List<PassReviewNote> documentPassReviewNoteList = passReviewNoteRepository.
                findByPassReviewAndNoteType(passReview, NoteType.DOCUMENT).orElseThrow(RuntimeException::new);
        List<PassReviewNote> interviewPassReviewNoteList = passReviewNoteRepository.
                findByPassReviewAndNoteType(passReview, NoteType.INTERVIEW).orElseThrow(RuntimeException::new);
        return PassReviewData.fromEntity(passReview, PassReviewNoteData.fromEntities(documentPassReviewNoteList),
                PassReviewNoteData.fromEntities(interviewPassReviewNoteList));
    }

    public void accessPassReivew(Long reqMemberId, Long passReviewId){
        PassReview passReview = passReviewRepository.findById(passReviewId).orElseThrow(RuntimeException::new);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(RuntimeException::new);
        PassReviewAccess passReviewAccess = new PassReviewAccess(passReview, member);
        passReviewAccessRepository.save(passReviewAccess);
    }

    public void savePassReview(Long reqMemberId, PassReviewSaveReq passReviewSaveReq, Long clubId){
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, reqMemberId).orElseThrow(RuntimeException::new);
        PassReview passReview = passReviewSaveReq.toEntity(clubMember);
        passReviewRepository.save(passReview);
    }

    //@Transactional(readOnly = false)
}
