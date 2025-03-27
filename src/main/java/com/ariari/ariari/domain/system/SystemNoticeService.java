package com.ariari.ariari.domain.system;

import com.ariari.ariari.commons.entity.image.ImageRepository;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.manager.file.FileManager;
import com.ariari.ariari.domain.club.notice.image.exception.NotBelongInClubNoticeException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.system.dto.req.SystemNoticeModifyReq;
import com.ariari.ariari.domain.system.dto.req.SystemNoticeSaveReq;
import com.ariari.ariari.domain.system.dto.res.SystemNoticeDetailRes;
import com.ariari.ariari.domain.system.dto.res.SystemNoticeListRes;
import com.ariari.ariari.domain.system.image.SystemNoticeImage;
import com.ariari.ariari.domain.system.image.SystemNoticeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemNoticeService {

    private final MemberRepository memberRepository;
    private final SystemNoticeRepository systemNoticeRepository;
    private final FileManager fileManager;
    private final SystemNoticeImageRepository systemNoticeImageRepository;
    private final ImageRepository imageRepository;
    private final MemberAlarmManger memberAlarmManger;


    public SystemNoticeListRes findSystemNotices() {
        List<SystemNotice> systemNoticeDataList = systemNoticeRepository.findAllByOrderByCreatedAtDesc();
        return  SystemNoticeListRes.create(systemNoticeDataList);
    }


    @Transactional
    public void saveSystemNotice(Long reqMemberId, SystemNoticeSaveReq saveReq, List<MultipartFile> files) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        // 검증 로직이 필요함

        SystemNotice systemNotice = saveReq.toEntity(saveReq.getTitle(), saveReq.getBody(), reqMember);
        systemNoticeRepository.save(systemNotice);

        if (files != null) {
            List<SystemNoticeImage> images = new ArrayList<>();
            for (MultipartFile file : files) {
                String filePath = fileManager.saveFile(file, "club_notice_image");
                images.add(new SystemNoticeImage(filePath, systemNotice));
            }

            systemNotice.setSystemNoticeImages(images);

        }

        // 알림 로직 추가
        List<Member> memberList = memberRepository.findAll();
        memberAlarmManger.sendSystemNotification(memberList);
    }

    @Transactional
    public void modifySystemNotice(Long reqMemberId, Long systemNoticeId, SystemNoticeModifyReq modifyReq, List<MultipartFile> files) {

        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        SystemNotice systemNotice = systemNoticeRepository.findById(systemNoticeId).orElseThrow(NotFoundEntityException::new);

        // 검증 로직 추가

        modifyReq.modifyEntity(systemNotice);

        List<Long> deletedImageIds = modifyReq.getDeletedImageIds();
        if (deletedImageIds != null) {
            List<SystemNoticeImage> deletedImages = systemNoticeImageRepository.findAllById(deletedImageIds);
            for (SystemNoticeImage deletedImage : deletedImages) {
                if (!deletedImage.getSystemNotice().equals(systemNotice)) {
                    throw new NotBelongInClubNoticeException();
                }
                imageRepository.delete(deletedImage);
            }
        }

        if (files != null) {
            List<SystemNoticeImage> newImages = new ArrayList<>();
            for (MultipartFile file : files) {
                String filePath = fileManager.saveFile(file, "club_notice_image");
                newImages.add(new SystemNoticeImage(filePath, systemNotice));
            }
            systemNotice.getSystemNoticeImages().addAll(newImages);
        }
    }


    @Transactional
    public void removeSystemNotice(Long reqMemberId, Long systemNoticeId) {

        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        SystemNotice systemNotice = systemNoticeRepository.findById(systemNoticeId).orElseThrow(NotFoundEntityException::new);

        // 검증 로직 추가

        systemNoticeRepository.delete(systemNotice);
    }

    @Transactional
    public SystemNoticeDetailRes findSystemNoticeDetail(Long systemNoticeId) {
        SystemNotice systemNotice = systemNoticeRepository.findById(systemNoticeId).orElseThrow(NotFoundEntityException::new);
        return SystemNoticeDetailRes.createRes(systemNotice);
    }

}
