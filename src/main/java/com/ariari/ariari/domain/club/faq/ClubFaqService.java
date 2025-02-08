package com.ariari.ariari.domain.club.faq;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.faq.dto.req.ClubFaqSaveReq;
import com.ariari.ariari.domain.club.faq.dto.res.ClubFaqListRes;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubFaqService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubFaqRepository clubFaqRepository;

    public ClubFaqListRes findClubFaqs(Long clubId, Pageable pageable) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        Page<ClubFaq> page = clubFaqRepository.findByClub(club, pageable);
        return ClubFaqListRes.fromEntities(page);
    }

    @Transactional
    public void saveClubFaq(Long reqMemberId, Long clubId, ClubFaqSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        ClubFaq clubFaq = saveReq.toEntity(club, reqClubMember);
        clubFaqRepository.save(clubFaq);
    }

    @Transactional
    public void removeClubFaq(Long reqMemberId, Long clubFaqId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubFaq clubFaq = clubFaqRepository.findById(clubFaqId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubFaq.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        clubFaqRepository.delete(clubFaq);
    }

}
