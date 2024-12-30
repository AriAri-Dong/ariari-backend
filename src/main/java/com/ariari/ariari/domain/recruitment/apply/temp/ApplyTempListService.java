package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.apply.dto.res.ApplyListRes;
import com.ariari.ariari.domain.recruitment.apply.temp.dto.res.ApplyTempListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplyTempListService {

    private final MemberRepository memberRepository;
    private final ApplyTempRepository applyTempRepository;

    public ApplyTempListRes findMyApplyTemps(Long reqMemberId, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<ApplyTemp> page = applyTempRepository.searchByMember(reqMember, pageable);
        return ApplyTempListRes.fromPage(page);
    }
}
