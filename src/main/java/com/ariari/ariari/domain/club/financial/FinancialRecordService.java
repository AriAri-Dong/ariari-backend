package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordListRes;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordSaveReq;
import com.ariari.ariari.domain.club.notice.image.exception.NotBelongInClubNoticeException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FinancialRecordService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FinancialRecordRepository financialRecordRepository;

    public Long findBalance(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if (clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NotBelongInClubException();
        }

        return financialRecordRepository.findTotalByClub(club);
    }

    @Transactional
    public void saveFinancialRecord(Long reqMemberId, Long clubId, FinancialRecordSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubNoticeException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        FinancialRecord financialRecord = saveReq.toEntity(club);
        financialRecordRepository.save(financialRecord);
    }

    public FinancialRecordListRes findFinancialRecords(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if (clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NotBelongInClubException();
        }

        Page<FinancialRecord> page = financialRecordRepository.findByClubOrderByRecordDateTimeDesc(club, pageable);
        List<FinancialRecord> financialRecords = page.getContent();
        FinancialRecord lastRecord = financialRecords.get(financialRecords.size() - 1);

        Long totalBeforeLast = financialRecordRepository.findTotalByClubBefore(club, lastRecord.getRecordDateTime());

        return FinancialRecordListRes.fromPage(page, totalBeforeLast);
    }

    /* ================== 비활성화 기능 ================== */

    @Transactional
    public void modifyFinancialRecord(Long reqMemberId, Long clubId, Long financialRecordId, FinancialRecordSaveReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubNoticeException::new);
        FinancialRecord financialRecord = financialRecordRepository.findById(financialRecordId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        modifyReq.modifyEntity(financialRecord);
    }

    @Transactional
    public void removeFinancialRecord(Long reqMemberId, Long clubId, Long financialRecordId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubNoticeException::new);
        FinancialRecord financialRecord = financialRecordRepository.findById(financialRecordId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);


        financialRecordRepository.delete(financialRecord);
    }

}
