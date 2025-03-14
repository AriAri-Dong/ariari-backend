package com.ariari.ariari.domain.club.question.answer;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.club.question.ClubQuestionRepository;
import com.ariari.ariari.domain.club.question.answer.dto.req.ClubAnswerSaveReq;
import com.ariari.ariari.domain.club.question.answer.exception.ExistingClubAnswerException;
import com.ariari.ariari.domain.club.question.answer.exception.NoClubAnswerException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubAnswerService {

    private final MemberRepository memberRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final ClubAnswerRepository clubAnswerRepository;
    private final MemberAlarmManger memberAlarmManger;

        public void saveClubAnswer(Long reqMemberId, Long clubQuestionId, ClubAnswerSaveReq saveReq) {
            Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
            ClubQuestion clubQuestion = clubQuestionRepository.findById(clubQuestionId).orElseThrow(NotFoundEntityException::new);
            ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubQuestion.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

            GlobalValidator.isClubManagerOrHigher(reqClubMember);

            if (clubAnswerRepository.findByClubQuestion(clubQuestion).isPresent()) {
                throw new ExistingClubAnswerException();
            }

            ClubAnswer clubAnswer = saveReq.toEntity(clubQuestion);
            clubAnswerRepository.save(clubAnswer);

            //  MemberAlarmEvent 통해 알림 생성
            memberAlarmManger.sendClubAnswerAlarm(clubQuestion.getMember(), clubQuestion.getClub().getId());
        }


    public void modifyClubAnswer(Long reqMemberId, Long clubQuestionId, ClubAnswerSaveReq modifyReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubQuestion clubQuestion = clubQuestionRepository.findById(clubQuestionId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubQuestion.getClub(), reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubManagerOrHigher(reqClubMember);

        ClubAnswer clubAnswer = clubAnswerRepository.findByClubQuestion(clubQuestion).orElseThrow(NoClubAnswerException::new);
        modifyReq.modifyEntity(clubAnswer);
    }

}
