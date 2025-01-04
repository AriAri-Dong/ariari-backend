package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.exception.exceptions.NoSchoolAuthException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.image.DeletedImageService;
import com.ariari.ariari.commons.image.FileManager;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.res.ClubDetailRes;
import com.ariari.ariari.domain.club.dto.req.ClubModifyReq;
import com.ariari.ariari.domain.club.dto.req.ClubSaveReq;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ViewsManager viewsManager;
    private final EntityDeleteManager entityDeleteManager;
    private final FileManager fileManager;
    private final DeletedImageService deletedImageService;

    @Transactional
    public ClubDetailRes findClubDetail(Long reqMemberId, Long clubId, String clientIp) {
        Member reqMember = memberRepository.findById(reqMemberId).orElse(null);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = null;
        if (reqMember != null) {
             reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElse(null);
        }

        // views 처리
        if (!viewsManager.checkForDuplicateView(club, clientIp)) {
            viewsManager.addViews(club);
            viewsManager.addClientIp(club, clientIp);
        }

        return ClubDetailRes.fromEntity(club, reqClubMember, reqMember);
    }

    @Transactional
    public void saveClub(Long reqMemberId, ClubSaveReq saveReq, MultipartFile file) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        School school = null;
        if (saveReq.getAffiliationType().equals(ClubAffiliationType.INTERNAL)) {
            if (reqMember.getSchool() == null) {
                throw new NoSchoolAuthException();
            } else {
                school = reqMember.getSchool();
            }
        }

        Club club = saveReq.toEntity(school);
        clubRepository.save(club);

        // 요청 Member 를 admin ClubMember 로 저장
        ClubMember clubMember = new ClubMember("동아리짱", // 수정 예정
                ClubMemberRoleType.ADMIN,
                reqMember,
                club);
        clubMemberRepository.save(clubMember);

        // 이미지 파일 처리
        if (file != null) {
            String uri = fileManager.saveFile(file, "club");
            club.setProfileUri(uri);
        }

    }

    // 디자인 x
    @Transactional
    public void modifyClub(Long reqMemberId, Long clubId, ClubModifyReq modifyReq, MultipartFile profileFile, MultipartFile bannerFile) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NotFoundEntityException::new);

        // ClubMember 권한 검증
        if (clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        modifyReq.modifyEntity(club);

        // 이미지 처리
        if (profileFile != null) {
            String profileUri = fileManager.saveFile(profileFile, "club");
            deletedImageService.deleteImageLogically(profileUri);
            club.setProfileUri(profileUri);
        }

        if (bannerFile != null) {
            String bannerUri = fileManager.saveFile(bannerFile, "club");
            deletedImageService.deleteImageLogically(bannerUri);
            club.setBannerUri(bannerUri);
        }

    }

    @Transactional
    public void removeClub(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember clubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        // ClubMember 권한 검증
        if (!clubMember.getClubMemberRoleType().equals(ClubMemberRoleType.ADMIN)) {
            throw new NoClubAuthException();
        }

        entityDeleteManager.deleteEntity(club);
    }

}
