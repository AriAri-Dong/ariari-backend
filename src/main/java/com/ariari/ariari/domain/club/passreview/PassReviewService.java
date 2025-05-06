package com.ariari.ariari.domain.club.passreview;

import com.ariari.ariari.commons.exception.exceptions.DuplicateDataCreateException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewData;
import com.ariari.ariari.domain.club.passreview.dto.PassReviewNoteData;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewListRes;
import com.ariari.ariari.domain.club.passreview.dto.res.PassReviewRes;
import com.ariari.ariari.domain.club.passreview.enums.NoteType;
import com.ariari.ariari.domain.club.passreview.mapper.PassReviewMapper;
import com.ariari.ariari.domain.club.passreview.note.PassReviewNote;
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
    private final ClubRepository clubRepository;

    public PassReviewListRes searchPassReviewPage(Long clubId, Pageable pageable){
        //page : 현재 page? size : 페이지당 항목개수
        List<PassReviewRes> passReviewResList = passReviewMapper.findPassReviewOfClub(clubId,
                pageable.getPageSize(), pageable.getPageNumber() * pageable.getPageSize());
        int totalSize = passReviewMapper.findPassReviewOfClubCount(clubId);
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

    public void savePassReview(Long reqMemberId, PassReviewSaveReq passReviewSaveReq, Long clubId){
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        Member member = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        if (passReviewRepository.existsByClubAndMember(club, member)){
            throw new DuplicateDataCreateException("이미 합격후기를 작성하여, 작성할 수 없습니다."); // 중복 작성 예외처리
        }

        PassReview passReview = passReviewSaveReq.toEntity(passReviewSaveReq, club, member);
        passReviewRepository.save(passReview);
    }

}
