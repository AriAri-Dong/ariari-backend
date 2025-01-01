package com.ariari.ariari.domain.club.passreview.service;

import com.ariari.ariari.domain.club.passreview.dto.PassReviewData;
import com.ariari.ariari.domain.club.passreview.dto.req.PassReviewSaveReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PassReviewService {
    // search목록 find디테일 save저장 modify수정 remove제거
    public List<PassReviewData> searchPassReviewPage(Long reqMemberId, Pageable pageable){
        return null;
    }

    public PassReviewData findPassReviewDetail(Long reqMemberId, Long passReviewId){
        return null;
    }

    public void savePassReview(Long reqMemberId, PassReviewSaveReq passReviewSaveReq){
        return;
    }

    //@Transactional(readOnly = false)
}
