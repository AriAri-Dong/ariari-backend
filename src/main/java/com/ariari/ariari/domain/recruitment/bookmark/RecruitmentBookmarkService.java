package com.ariari.ariari.domain.recruitment.bookmark;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.MemberAlarmManger;
import com.ariari.ariari.commons.validator.GlobalValidator;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.member.MemberRepository;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import com.ariari.ariari.domain.recruitment.recruitment.RecruitmentRepository;
import com.ariari.ariari.domain.recruitment.bookmark.exception.AlreadyExistsRecruitmentBookmarkException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentBookmarkService {

    private final MemberRepository memberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final RecruitmentBookmarkRepository recruitmentBookmarkRepository;
    private final MemberAlarmManger memberAlarmManger;

    public void saveBookmark(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        GlobalValidator.eqSchoolAuth(reqMember, recruitment.getClub().getSchool());

        if (recruitmentBookmarkRepository.existsByMemberAndRecruitment(reqMember, recruitment)) {
            throw new AlreadyExistsRecruitmentBookmarkException();
        }

        RecruitmentBookmark recruitmentBookmark = new RecruitmentBookmark(recruitment, reqMember);
        recruitmentBookmarkRepository.save(recruitmentBookmark);
    }

    public void removeBookmark(Long reqMemberId, Long recruitmentId) {
        Member reqMember = memberRepository.findById(reqMemberId).orElseThrow(NotFoundEntityException::new);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId).orElseThrow(NotFoundEntityException::new);

        RecruitmentBookmark recruitmentBookmark = recruitmentBookmarkRepository.findByMemberAndRecruitment(reqMember, recruitment).orElseThrow(NotFoundEntityException::new);
        recruitmentBookmarkRepository.delete(recruitmentBookmark);
    }

    // 관심모집 마감임박(D-1) 알림
    @Scheduled(cron ="0 0 0 * * ?")
    @Transactional(readOnly = true)
    public void sendRecruitmentBookMarkReminder(){
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        List<RecruitmentBookmark> recruitmentBookmarkList = recruitmentBookmarkRepository.findAllByWithinRecruitment(LocalDateTime.now(), endTime);
        Map<Long, List<RecruitmentBookmark>> groupByRecruitmentId = recruitmentBookmarkList.stream()
                .collect(Collectors.groupingBy(recruitmentBookmark -> recruitmentBookmark.getRecruitment().getId()));

        groupByRecruitmentId.forEach((id, recruitmentBookmarks) ->{
            if(recruitmentBookmarks.isEmpty()){
                return;
            }

            List<Member> memberList = recruitmentBookmarks.stream().map(RecruitmentBookmark::getMember).toList();
            String title = recruitmentBookmarks.get(0).getRecruitment().getTitle();
            Long recruitmentId = recruitmentBookmarks.get(0).getId();
            memberAlarmManger.sendRecruitmentBookMarkReminder(memberList, title, recruitmentId);
        });
    }

}
