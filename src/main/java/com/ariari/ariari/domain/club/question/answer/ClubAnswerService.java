package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.club.question.answer.dto.req.ClubAnswerSaveReq;
import com.ariari.ariari.domain.club.question.answer.exception.NoClubAnswerException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubAnswerService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubAnswerRepository clubAnswerRepository;

    public void saveClubAnswer(Long reqMemberId, Long clubQuestionId, ClubAnswerSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubQuestion clubQuestion = clubQuestionRepository.findById(clubQuestionId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubQuestion.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        ClubAnswer clubAnswer = saveReq.toEntity(clubQuestion, reqClubMember);
        clubAnswerRepository.save(clubAnswer);
    }


    public void modifyClubAnswer(Long reqMemberId, Long clubQuestionId, ClubAnswerSaveReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubQuestion clubQuestion = clubQuestionRepository.findById(clubQuestionId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubQuestion.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        Optional<ClubAnswer> clubAnswerOptional = clubAnswerRepository.findByClubQuestion(clubQuestion);
        if (clubAnswerOptional.isEmpty()) {
            throw new NoClubAnswerException();
        }

        ClubAnswer clubAnswer = clubAnswerOptional.get();
        modifyReq.modifyEntity(clubAnswer);
    }

}
