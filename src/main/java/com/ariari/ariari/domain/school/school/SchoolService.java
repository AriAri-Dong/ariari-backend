package com.ariari.ariari.domain.school.school;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.school.School;
import com.ariari.ariari.domain.school.dto.SchoolListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {

    private final MemberRepository memberRepository;
    private final SchoolRepository schoolRepository;

    public SchoolListRes searchSchools(Long reqMemberId, String query, Pageable pageable) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);

        Page<School> page;
        if (query != null) {
            page = schoolRepository.findByNameContains(query, pageable);
        } else {
            page = schoolRepository.findAll(pageable);
        }

        return SchoolListRes.createRes(page);
    }

}
