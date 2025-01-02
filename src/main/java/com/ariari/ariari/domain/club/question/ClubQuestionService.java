package com.ariari.ariari.domain.club.question;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.question.dto.req.ClubQuestionSaveReq;
import com.ariari.ariari.domain.club.question.dto.res.ClubQnaListRes;
import com.ariari.ariari.domain.club.question.exception.NoClubQuestionAuthException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubQuestionService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubQuestionRepository clubQuestionRepository;
    private final EntityDeleteManager entityDeleteManager;

    public ClubQnaListRes findClubQuestions(Long clubId, Pageable pageable) {
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        Page<ClubQuestion> page = clubQuestionRepository.findByClub(club, pageable);
        return ClubQnaListRes.fromEntities(page);
    }

    @Transactional(readOnly = false)
    public void saveClubQuestion(Long reqMemberId, Long clubId, ClubQuestionSaveReq saveReq) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        ClubQuestion clubQuestion = saveReq.toEntity(club, reqMember);
        clubQuestionRepository.save(clubQuestion);
    }

    @Transactional(readOnly = false)
    public void removeClubQuestion(Long reqMemberId, Long questionId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubQuestion clubQuestion = clubQuestionRepository.findById(questionId).orElseThrow(NotFoundEntityException::new);

        if (!clubQuestion.getMember().equals(reqMember)) {
            Club club = clubQuestion.getClub();
            ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubQuestionAuthException::new);
            if ( reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
                throw new NoClubQuestionAuthException();
            }
        }

        entityDeleteManager.deleteEntity(clubQuestion);
    }

}