package com.ariari.ariari.domain.club.financial;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordListRes;
import com.ariari.ariari.domain.club.financial.dto.FinancialRecordSaveReq;
import com.ariari.ariari.domain.club.financial.exception.NegativeBalanceException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FinancialRecordService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FinancialRecordRepository financialRecordRepository;
    private final EntityDeleteManager entityDeleteManager;

    public Long findBalance(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        Optional<ClubMember> reqClubMemberOptional = clubMemberRepository.findByClubAndMember(club, reqMember);

        if (reqClubMemberOptional.isEmpty()) {
            throw new NoClubAuthException();
        }

        return financialRecordRepository.findTotalByClub(club);
    }

    @Transactional
    public void saveFinancialRecord(Long reqMemberId, Long clubId, FinancialRecordSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        // 잔액 음수 검증 -> 일단 비활성화 (잘못된 검증)
//        Long before = financialRecordRepository.findTotalByClub(club);
//        Long after = before + saveReq.getAmount();
//        if (after < 0) {
//            throw new NegativeBalanceException();
//        }

        FinancialRecord financialRecord = saveReq.toEntity(club);
        financialRecordRepository.save(financialRecord);
    }

    public FinancialRecordListRes findFinancialRecords(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        Optional<ClubMember> reqClubMemberOptional = clubMemberRepository.findByClubAndMember(club, reqMember);

        if (reqClubMemberOptional.isEmpty()) {
            throw new NoClubAuthException();
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
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);
        FinancialRecord financialRecord = financialRecordRepository.findById(financialRecordId).orElseThrow(NotFoundEntityException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        Long before = financialRecordRepository.findTotalByClub(club);
        Long after = before - financialRecord.getAmount() + modifyReq.getAmount();
        if (after < 0) {
            throw new NegativeBalanceException();
        }

        modifyReq.modifyEntity(financialRecord);
    }

    @Transactional
    public void removeFinancialRecord(Long reqMemberId, Long clubId, Long financialRecordId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);
        FinancialRecord financialRecord = financialRecordRepository.findById(financialRecordId).orElseThrow(NotFoundEntityException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        Long before = financialRecordRepository.findTotalByClub(club);
        Long after = before - financialRecord.getAmount();
        if (after < 0) {
            throw new NegativeBalanceException();
        }

        entityDeleteManager.deleteEntity(financialRecord);
    }

}
