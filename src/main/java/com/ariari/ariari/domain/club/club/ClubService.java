package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.bookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.clubmember.exception.NotBelongInClubException;
import com.ariari.ariari.domain.club.club.dto.res.ClubDetailRes;
import com.ariari.ariari.domain.club.club.dto.req.ClubModifyReq;
import com.ariari.ariari.domain.club.club.dto.req.ClubSaveReq;
import com.ariari.ariari.domain.club.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.exceptions.RemovingClubException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.applyform.ApplyForm;
import com.ariari.ariari.domain.recruitment.applyform.ApplyFormRepository;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ApplyFormRepository applyFormRepository;
    private final ViewsManager viewsManager;
    private final FileManager fileManager;
    private final MemberAlarmManger memberAlarmManger;
    private final ClubBookmarkRepository clubBookmarkRepository;

    @Transactional
    public ClubDetailRes findClubDetail(Long reqMemberId, Long clubId, String clientIp) {
        Member reqMember = null;
        if (reqMemberId != null) {
            reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        }

        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        ClubMember reqClubMember = null;
        if (reqMember != null) {
            reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElse(null);
        }

        return ClubDetailRes.fromEntity(club, reqClubMember, reqMember);
    }

    @Transactional
    public void saveClub(Long reqMemberId, ClubSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        School school = null;
        if (saveReq.getAffiliationType().equals(ClubAffiliationType.INTERNAL)) {
            GlobalValidator.hasSchoolAuth(reqMember);
            school = reqMember.getSchool();
        }

        Club club = saveReq.toEntity(school);
        clubRepository.save(club);

        ClubMember clubMember = ClubMember.createAdmin(reqMember, club);
        clubMemberRepository.save(clubMember);

        if (file != null) {
            String uri = fileManager.saveFile(file, "club");
            club.setProfileUri(uri);
        }

        ApplyForm applyForm = new ApplyForm(club, new ArrayList<>());
        applyFormRepository.save(applyForm);
    }

    @Transactional
    public void modifyClub(Long reqMemberId, Long clubId, ClubModifyReq modifyReq, MultipartFile profileFile, MultipartFile bannerFile) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.isClubManagerOrHigher(clubMember);

        modifyReq.modifyEntity(club);

        if (profileFile != null) {
            String profileUri = fileManager.saveFile(profileFile, "club");
            fileManager.deleteFile(club.getProfileUri());
            club.setProfileUri(profileUri);
        }

        if (bannerFile != null) {
            String bannerUri = fileManager.saveFile(bannerFile, "club");
            fileManager.deleteFile(club.getBannerUri());
            club.setBannerUri(bannerUri);
        }
    }

    @Transactional
    public void removeClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotBelongInClubException::new);

        GlobalValidator.isClubAdmin(reqClubMember);

        Long remainingCount = clubMemberRepository.countByClub(club);
        if (remainingCount > 1) {
            throw new RemovingClubException();
        }

        clubRepository.delete(club);
        List<Member> memberList = clubBookmarkRepository.findAllByClub(club).stream()
                .map(ClubBookmark::getMember)
                .toList();
        memberAlarmManger.sendClubBookmarkClosedAlarm(memberList, club.getName());
    }

}
