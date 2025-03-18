package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.ClubAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.question.dto.req.ClubQuestionSaveReq;
import com.ariari.ariari.domain.club.question.dto.res.ClubQnaListRes;
import com.ariari.ariari.domain.club.question.exception.NoClubQuestionAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubQuestionService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubAlarmManger clubAlarmManger;

    public ClubQnaListRes findClubQuestions(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        Page<ClubQuestion> page = clubQuestionRepository.findByClub(club, pageable);
        return ClubQnaListRes.fromEntities(page);
    }

    @Transactional
    public void saveClubQuestion(Long reqMemberId, Long clubId, ClubQuestionSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        ClubQuestion clubQuestion = saveReq.toEntity(club, reqMember);
        clubQuestionRepository.save(clubQuestion);
        clubAlarmManger.sendClubQA(club);
    }

    @Transactional
    public void removeClubQuestion(Long reqMemberId, Long questionId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubQuestion clubQuestion = clubQuestionRepository.findById(questionId).orElseThrow(NotFoundEntityException::new);

        if (!clubQuestion.getMember().equals(reqMember)) {
            Club club = clubQuestion.getClub();
            ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubQuestionAuthException::new);
            if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
                throw new NoClubQuestionAuthException();
            }
        }

        clubQuestionRepository.delete(clubQuestion);
    }

    @Transactional
    public void changeStateByMember(Long reqMemberId){
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<ClubQuestion> clubQuestions = clubQuestionRepository.findByMember(reqMember);

        clubQuestions.stream().forEach( question  -> {
            question.setMember(null);
        });

    }


}
