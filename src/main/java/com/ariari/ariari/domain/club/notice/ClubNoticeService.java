package com.ariari.ariari.domain.club.notice;

import com.ariari.ariari.commons.entity.image.ImageRepository;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.ClubRepository;
import com.ariari.ariari.domain.club.clubmember.ClubMember;
import com.ariari.ariari.domain.club.clubmember.ClubMemberRepository;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.exception.NoClubAuthException;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeDetailRes;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeListRes;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeModifyReq;
import com.ariari.ariari.domain.club.notice.dto.ClubNoticeSaveReq;
import com.ariari.ariari.domain.club.notice.exceptions.TooManyFixedClubNoticeException;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImage;
import com.ariari.ariari.domain.club.notice.image.ClubNoticeImageRepository;
import com.ariari.ariari.domain.club.notice.image.exception.NotBelongInClubNoticeException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubNoticeService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubNoticeRepository clubNoticeRepository;
    private final ClubNoticeImageRepository clubNoticeImageRepository;
    private final ImageRepository imageRepository;
    private final FileManager fileManager;

    @Transactional
    public void saveClubNotice(Long reqMemberId, Long clubId, ClubNoticeSaveReq saveReq, List<MultipartFile> files) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(club, reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        ClubNotice clubNotice = saveReq.toEntity(club, reqClubMember);
        clubNoticeRepository.save(clubNotice);

        if (files != null) {
            List<ClubNoticeImage> images = new ArrayList<>();
            for (MultipartFile file : files) {
                String filePath = fileManager.saveFile(file, "club_notice_image");
                images.add(new ClubNoticeImage(filePath, clubNotice));
            }

            clubNotice.setClubNoticeImages(images);
        }
    }

    @Transactional
    public void modifyClubNotice(Long reqMemberId, Long clubNoticeId, ClubNoticeModifyReq modifyReq, List<MultipartFile> files) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubNotice clubNotice = clubNoticeRepository.findById(clubNoticeId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubNotice.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        modifyReq.modifyEntity(clubNotice);

        List<Long> deletedImageIds = modifyReq.getDeletedImageIds();
        if (deletedImageIds != null) {
            List<ClubNoticeImage> deletedImages = clubNoticeImageRepository.findAllById(deletedImageIds);
            for (ClubNoticeImage deletedImage : deletedImages) {
                if (!deletedImage.getClubNotice().equals(clubNotice)) {
                    throw new NotBelongInClubNoticeException();
                }
                imageRepository.delete(deletedImage);
            }
        }

        if (files != null) {
            List<ClubNoticeImage> newImages = new ArrayList<>();
            for (MultipartFile file : files) {
                String filePath = fileManager.saveFile(file, "club_notice_image");
                newImages.add(new ClubNoticeImage(filePath, clubNotice));
            }

            clubNotice.getClubNoticeImages().addAll(newImages);
        }
    }

    @Transactional
    public void removeClubNotice(Long reqMemberId, Long clubNoticeId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubNotice clubNotice = clubNoticeRepository.findById(clubNoticeId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubNotice.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        clubNoticeRepository.delete(clubNotice);
    }

    @Transactional
    public void toggleClubNoticeFix(Long reqMemberId, Long clubNoticeId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubNotice clubNotice = clubNoticeRepository.findById(clubNoticeId).orElseThrow(NotFoundEntityException::new);
        ClubMember reqClubMember = clubMemberRepository.findByClubAndMember(clubNotice.getClub(), reqMember).orElseThrow(NoClubAuthException::new);

        if (reqClubMember.getClubMemberRoleType().equals(ClubMemberRoleType.GENERAL)) {
            throw new NoClubAuthException();
        }

        if (clubNotice.getIsFixed().equals(Boolean.FALSE) && clubNoticeRepository.findFixedByClub(clubNotice.getClub()).size() >= 3) {
            throw new TooManyFixedClubNoticeException();
        }

        clubNotice.controlIsFixed();
    }

    public ClubNoticeDetailRes findClubNoticeDetail(Long reqMemberId, Long clubNoticeId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        ClubNotice clubNotice = clubNoticeRepository.findById(clubNoticeId).orElseThrow(NotFoundEntityException::new);

        if (clubMemberRepository.findByClubAndMember(clubNotice.getClub(), reqMember).isEmpty()) {
            throw new NoClubAuthException();
        }

        return ClubNoticeDetailRes.createRes(clubNotice);
    }

    public ClubNoticeListRes findFixedClubNotices(Long reqMemberId, Long clubId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if (clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NoClubAuthException();
        }

        List<ClubNotice> clubNotices = clubNoticeRepository.findFixedByClub(club);
        return ClubNoticeListRes.createRes(clubNotices);
    }

    public ClubNoticeListRes findClubNotices(Long reqMemberId, Long clubId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Club club = clubRepository.findById(clubId).orElseThrow(NotFoundEntityException::new);

        if (clubMemberRepository.findByClubAndMember(club, reqMember).isEmpty()) {
            throw new NoClubAuthException();
        }

        Page<ClubNotice> page = clubNoticeRepository.findUnfixedByClub(club, pageable);
        return ClubNoticeListRes.createRes(page);
    }

}
