package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.bookmark.ClubBookmark;
import com.ariari.ariari.domain.club.bookmark.ClubBookmarkRepository;
import com.ariari.ariari.domain.club.club.dto.res.ClubListRes;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
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
import java.util.UUID;

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
        School school = club.getSchool();
        GlobalValidator.eqSchoolAuth(reqMember, club.getSchool());

        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        ClubMember reqClubMember = null;
        if (reqMember != null) {
            reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElse(null);
        }

        return ClubDetailRes.fromEntity(club, school, reqClubMember, reqMember);
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

        ClubMember clubMember = ClubMember.createAdmin(reqMember, club, saveReq.getClubMemberName());
        clubMemberRepository.save(clubMember);

        if (file != null) {
            String uri = fileManager.saveFile(file, "club");
            club.setProfileUri(uri);
        }

        ApplyForm applyForm = new ApplyForm(club, false, new ArrayList<>());
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
            String profileUri = club.getProfileUri();
            if (profileUri != null) {
                fileManager.deleteFile(profileUri);
            }

            String newProfileUri = fileManager.saveFile(profileFile, "club");
            club.setProfileUri(newProfileUri);
        }

        if (bannerFile != null) {
            String bannerUri = club.getBannerUri();
            if (bannerUri != null) {
                fileManager.deleteFile(bannerUri);
            }

            String newBannerUri = fileManager.saveFile(bannerFile, "club");
            club.setBannerUri(newBannerUri);
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


        List<Member> memberList = clubBookmarkRepository.findAllByClub(club).stream()
                .map(ClubBookmark::getMember)
                .toList();
        memberAlarmManger.sendClubBookmarkClosedAlarm(memberList, club.getName());
        clubRepository.delete(club);

    }

    @Transactional(readOnly = true)
    public ClubListRes findSchoolAdminClub(Long reqMemberId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        List<ClubMember> clubMembers = clubMemberRepository.findByMemberAndClubMemberRoleTypeAndClub_SchoolIsNotNull(reqMember, ClubMemberRoleType.ADMIN);
        return ClubListRes.fromClubMemberList(clubMembers);
    }

}
