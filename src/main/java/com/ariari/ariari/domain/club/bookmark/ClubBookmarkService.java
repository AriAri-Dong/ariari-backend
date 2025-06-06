package com.ariari.ariari.domain.club.bookmark;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.club.ClubRepository;
import com.ariari.ariari.domain.club.bookmark.exception.AlreadyExistsClubBookmarkException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClubBookmarkService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubBookmarkRepository clubBookmarkRepository;

    public void saveBookmark(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        if (clubBookmarkRepository.existsByMemberAndClub(reqMember, club)) {
            throw new AlreadyExistsClubBookmarkException();
        }

        ClubBookmark clubBookmark = new ClubBookmark(reqMember, club);
        clubBookmarkRepository.save(clubBookmark);
    }

    public void removeBookmark(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        ClubBookmark clubBookmark = clubBookmarkRepository.findByMemberAndClub(reqMember, club).orElseThrow(NotFoundEntityException::new);
        clubBookmarkRepository.delete(clubBookmark);
    }

    public void removeAllBookmarks(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        clubBookmarkRepository.deleteByMember(reqMember);
    }

}
