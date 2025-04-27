package com.ariari.ariari.commons.manager;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.dto.AlarmContent;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberRoleType;
import com.ariari.ariari.domain.club.clubmember.enums.ClubMemberStatusType;
import com.ariari.ariari.domain.club.question.ClubQuestion;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.alarm.MemberAlarm;
import com.ariari.ariari.domain.member.alarm.MemberAlarmRepository;
import com.ariari.ariari.domain.member.alarm.dto.res.MemberAlarmListRes;
import com.ariari.ariari.domain.member.alarm.enums.MemberAlarmType;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEvent;
import com.ariari.ariari.domain.member.alarm.event.MemberAlarmEventList;
import com.ariari.ariari.domain.recruitment.apply.enums.ApplyStatusType;
import com.ariari.ariari.domain.recruitment.apply.temp.ApplyTemp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberAlarmManger {

    private final ApplicationEventPublisher eventPublisher;

    // 시스템 공지사항 추가
    public void sendSystemNotification(List<Member> memberList){
        String title = "새로운 아리아리 플랫폼 공지사항이 등록되었습니다. 서비스 관련 중요한 내용을 확인해 보세요.";
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title
        ,"/service-notices", memberList);
        sendList(memberAlarmEventList);
    }

    // 동아리 공지사항 추가
    public void sendClubNotification(List<Member> memberList, String clubName, Long clubId) {
        String title = String.format("%s에 공지사항이 추가 되었습니다.", clubName);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title
                ,"/club/management/activity/notice?clubId="+clubId, memberList);
        sendList(memberAlarmEventList);
    }



    //  임시저장된 지원서 모집마감임박
    public void sendApplyTempReminder(List<Member> memberList) {
        String title = "임시 저장된 지원서의 모집 마감이 하루 남았습니다! 오늘 안에 제출을 완료해 주세요.";
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title
                ,"/application", memberList);
        sendList(memberAlarmEventList);
    }

    // 임시 저장한 지원서에 해당하는 모집 마감 알림
    public void sendApplyTempClosed(List<Member> memberList, String recruitmentTilte){
        String title = String.format("%S에 대하여 임시 저장된 지원서는 모집이 마감되어 자동 삭제되었습니다.", recruitmentTilte);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(
                title,
               null,
                memberList);
        sendList(memberAlarmEventList);
    }

    //지원한 공고 서류 & 최종 처리 시
    public void sendApplyStateAlarm(ApplyStatusType applyStatusType, Member member, String clubName, Long clubId, String interviewMessage) {
        AlarmContent alarmContent = applyStatusTypeSelect(applyStatusType, clubName, clubId, interviewMessage);
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                alarmContent.getTitle(),
                alarmContent.getUri(),
                member
        );
        sendSingle(memberAlarmEvent);
    }


    // 관심모집 d-1 모집마감임박
    public void sendRecruitmentBookMarkReminder(List<Member> memberList, String recruitmentName) {
        String title = String.format("관심 등록하신 %s의 마감이 하루 남았습니다! 놓치지 말고 지금 확인해 보세요.", recruitmentName);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title
                ,"/myPage/interestRecruitment", memberList);
        sendList(memberAlarmEventList);
    }

    // 관심 모집 마감
    public void sendRecruitmentClosed(List<Member> memberList, String recruitmentName){
        String title = String.format("관심 등록하신 %s이 마감되었습니다. 더 이상 지원이 불가능합니다.", recruitmentName);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title
                ,"null", memberList);
        sendList(memberAlarmEventList);
    }

    // 동아리 회원들 상태
    public void sendClubMembersStatusType(ClubMemberStatusType clubMemberStatusType, List<Member> memberList, Long clubId){
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(clubMemberStatusSelect(clubMemberStatusType)
                ,"/club/management/activity/notice?clubId="+clubId, memberList);
        sendList(memberAlarmEventList);
    }

    // 동아리 회원 상태
    public void sendClubMemberStatusType(ClubMemberStatusType clubMemberStatusType, Member member, Long clubId){
        String title = clubMemberStatusSelect(clubMemberStatusType);
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                title,
                "/club/management/activity/notice?clubId="+clubId,
                member
        );
        sendSingle(memberAlarmEvent);
    }


    // 동아리 회원 추방
    public void sendClubMemberRemove(Member member, String clubName, Long clubId){
        String title = String.format("안타깝게도 %s에서 더 이상 활동할 수 없게 되었습니다. 자세한 사항은 동아리 운영진에게 문의해 주세요.", clubName);
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                title,
                null,
                member
        );
        sendSingle(memberAlarmEvent);
    }

    // 동아리 직책 변경시
    public void sendClubRoleStateAlarm(Member member, ClubMemberRoleType roleType, Long clubId) {

        String title = String.format("관리자에 의해 동아리 내 직책이 %s으로 변경되었습니다.", roleSelect(roleType));
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                title,
                "/club/management/activity/notice?clubId="+clubId,
                member
        );
        sendSingle(memberAlarmEvent);

    }


    //동아리 일정 추가시
    public void sendClubEventAlarm(List<Member> memberList, Long clubId) {
        if (memberList.isEmpty()) {
            return;
        }
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from("동아리 캘린더에 새로운 일정이 추가되었습니다! 알림을 클릭해 확인해 보세요."
                        ,"/club/management/activity/schedule?clubId="+clubId, memberList);
        sendList(memberAlarmEventList);
    }

    // 관심 동아리 모집시
    public void sendClubBookmarkRecruitmentAlarm(List<Member> memberList, String clubName, Long clubId) {

        if (memberList.isEmpty()) {
            return;
        }
        String title = String.format("관심 등록하신 %s의 모집이 시작되었습니다! 지금 확인해 보세요.", clubName);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title, "/club/recruitment?clubId="+clubId, memberList);
        sendList(memberAlarmEventList);
    }

    // 관심 동아리 폐쇄시
    public void sendClubBookmarkClosedAlarm(List<Member> memberList, String clubName) {

        if (memberList.isEmpty()) {
            return;
        }
        String title = String.format("관심 등록하신 %s이 폐쇄되었습니다. 관심 동아리에서 해당 동아리가 삭제됩니다.", clubName);
        MemberAlarmEventList memberAlarmEventList = MemberAlarmEventList.from(title, null, memberList);
        sendList(memberAlarmEventList);
    }

    // 동아리 Q&A 질문 답변시
    public void sendClubAnswerAlarm(Member member, Long clubId) {
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                "작성하신 질문에 대한 답변이 등록되었습니다! 알림을 클릭해 확인해 보세요.",
                "/club/help?clubId="+clubId,
                member
        );
        sendSingle(memberAlarmEvent);
    }

    //작성한 댓글의 답글이 달릴 시 알림
    public void sendClubActivityComment(Long clubId, Member member){
        MemberAlarmEvent memberAlarmEvent = MemberAlarmEvent.from(
                "작성하신 댓글에 답글이 달렸습니다! 알림을 클릭해 확인해 보세요.",
                " /club/activityHistory?clubId="+clubId,
                member
        );
        sendSingle(memberAlarmEvent);
    }

    private String roleSelect(ClubMemberRoleType roleType){
        String role = "";
        if(roleType == ClubMemberRoleType.GENERAL) {
            role = "일반 동아리원";
        }else if(roleType == ClubMemberRoleType.MANAGER) {
            role = "동아리 스탭";
        }else{
            role = "동아리 관리자";
        }
        return role;
    }

    private AlarmContent applyStatusTypeSelect(ApplyStatusType applyStatusType, String clubName, Long clubId, String interviewMessage) {
        String title = "";
        String uri = null;

        if (applyStatusType == ApplyStatusType.APPROVE) {
            title = String.format("지원하신 %s에 합격하셨습니다! 지금 바로 가입된 동아리를 확인해 보세요.", clubName);
            uri = "/club/activityHistory?clubId=" + clubId;
        } else if (applyStatusType == ApplyStatusType.REFUSAL) {
            title = String.format("지원하신 %s에 아쉽게도 합류할 수 없게 되었습니다. 다음 기회에 다시 도전해 주세요.", clubName);
        } else if (applyStatusType == ApplyStatusType.INTERVIEW) {
            title = String.format(
                    "지원하신 %s이 면접 전형으로 전환되었습니다. 알림을 클릭하여 동아리 운영진의 메시지를 확인해 주세요. / %s",
                    clubName,
                    interviewMessage
            );

        }

        return AlarmContent.from(title, uri);
    }

    private String clubMemberStatusSelect(ClubMemberStatusType clubMemberStatusType) {
        String title = "";
        if(clubMemberStatusType == ClubMemberStatusType.ACTIVE) {
            title = "관리자에 의해 동아리 활동 상태가 활동 중으로 변경되었습니다.";
        }else if(clubMemberStatusType == ClubMemberStatusType.WITHDRAWN) {
            title ="관리자에 의해 동아리 활동 상태가 종료로 변경되었습니다.";
        }else if(clubMemberStatusType == ClubMemberStatusType.INACTIVE){
            title ="관리자에 의해 동아리 활동 상태가 휴식 중으로 변경되었습니다.";
        }
        return title;
    }



    private void sendSingle(MemberAlarmEvent memberAlarmEvent){
        eventPublisher.publishEvent(memberAlarmEvent);
    }

    private void sendList(MemberAlarmEventList memberAlarmListEvent){
        eventPublisher.publishEvent(memberAlarmListEvent);
    }





}
