package com.ariari.ariari.configs;

import com.ariari.ariari.domain.system.SystemNotice;
import com.ariari.ariari.domain.system.faq.SystemFaq;
import com.ariari.ariari.domain.system.faq.SystemFaqRepository;
import com.ariari.ariari.domain.system.faq.enums.SystemFaqStatusType;
import com.ariari.ariari.domain.system.notice.SystemNoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerSupportDataConfig {

    private final SystemFaqRepository systemFaqRepository;
    private final SystemNoticeRepository systemNoticeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void systemFaqDataInit(){
        if(systemFaqRepository.count() > 0){
            return;
        }
        systemFaqRepository.save(SystemFaq.create("카카오 로그인이 안 돼요.", "카카오 로그인 오류 시 브라우저 쿠키 및 캐시를 삭제한 후 다시 시도해보세요. " +
                "문제가 지속되면 아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.ACCOUNT));
        systemFaqRepository.save(SystemFaq.create("회원가입은 어떻게 하나요?", "아리아리는 회원 계정의 신뢰성을 위해서," +
                "카카오를 통한 소셜로그인만 제공하고 있습니다. 카카오 로그인을 통해 회원가입을 진행할 수 있습니다.", SystemFaqStatusType.ACCOUNT));
        systemFaqRepository.save(SystemFaq.create("학교인증은 어떻게 진행되나요?", "회원가입 시 혹은 회원 정보 수정 페이지에서," +
                "학교 이메일을 통한 방식으로 학교인증이 가능합니다.", SystemFaqStatusType.ACCOUNT));
        systemFaqRepository.save(SystemFaq.create("회원탈퇴는 어떻게 하나요?", "회원 정보 수정 페이지에서" +
                " 서비스 탈퇴하기를 통하여 진행하실 수 있습니다.", SystemFaqStatusType.ACCOUNT));
        systemFaqRepository.save(SystemFaq.create("프로필 아이콘은 어떻게 변경하나요?", "회원 정보 수정 페이지에서 변경할 수 있습니다.", SystemFaqStatusType.ACCOUNT));
        systemFaqRepository.save(SystemFaq.create("닉네임은 어떻게 변경하나요?", "회원 정보 수정 페이지에서 변경할 수 있습니다.", SystemFaqStatusType.ACCOUNT));



        systemFaqRepository.save(SystemFaq.create("동아리는 어떻게 만드나요?", "상단의 동아리 만들기 버튼을 통해서" +
                "동아리를 개설하실 수 있습니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 활동명은 무엇인가요?", "동아리 내에서 사용할 닉네임이라고 생각하시면 됩니다.\n" +
                "예를들어, 홍길동과 같이 설정할 수도 있으며, 1기 홍길동, 아리아리 1기 홍길동, KEVIN 등과 같이 설정하실 수도 있습니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 정보 수정이 불가능한가요?", "동아리명, 동아리 소속, 동아리 분야, 동아리 지역, 동아리 대상과 " +
                "같은 정보들은 수정할 수 없습니다. 동아리 상세 페이지를 통하여 동아리 관리자는 동아리 프로필 사진 및 동아리 한 줄 소개만 수정할 수 있습니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 모집 공고를 여러 개 올릴 수도 있나요?", "아리아리에서는 기본적으로 1개의" +
                "동아리 모집 공고만 활성화시킬 수 있습니다. 동아리 모집 공고를 여러 개 올릴 수도 있으나, 중복된 날짜로는 업로드가 불가능합니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 모집 마감일을 연장할 수 있나요?", "모집 마감일 연장은 불가능합니다. 모집을 계속하고 싶다면 " +
                "새로운 모집공고를 작성해주세요.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 지원양식을 수정했음에도 반영되지 않는 오류가 있는 것 같아요.", "동아리 지원양식은 모집공고를" +
                "게시하기 전의 기준으로 적용됩니다. 따라서, 동아리 지원 양식은 항상 내가 반영하고 싶은 모집공고 작성 전에 수정을 완료해주세요.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("모집공고 수정이 가능한가요?", "모집공고 수정은 불가능합니다. 모집 공고를 수정하고 싶다면," +
                "모집 종료 후 새로운 모집공고를 작성해야 합니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 지원자는 어떻게 확인하나요?", "동아리 상세페이지에서 확인하실 수 있습니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("내 동아리 지원 목록은 어떻게 확인하나요?", "동아리 지원페이지에서 확인하실 수 있습니다.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("지원자에게 개별 연락은 어떻게 하나요?", "현재는 플랫폼 내 메시지 기능을 제공하지 않습니다. " +
                "지원자에게 정보를 수집하여 개별 연락해주세요.", SystemFaqStatusType.CLUB));
        systemFaqRepository.save(SystemFaq.create("동아리 폐쇄는 어떻게 하나요?", "동아리 상세페이지에서 확인하실 수 있습니다.", SystemFaqStatusType.CLUB));

        systemFaqRepository.save(SystemFaq.create("파일 업로드가 안 돼요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));
        systemFaqRepository.save(SystemFaq.create("페이지 로딩이 너무 느려요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));
        systemFaqRepository.save(SystemFaq.create("모바일에서 일부 기능이 제한되어 있어요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));
        systemFaqRepository.save(SystemFaq.create("일부 브라우저에서 오류가 있어요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));
        systemFaqRepository.save(SystemFaq.create("이런 기능을 추가해주세요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));
        systemFaqRepository.save(SystemFaq.create("수정/삭제하고 싶은 데이터가 있어요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.TECHNICAL));

        systemFaqRepository.save(SystemFaq.create("학교인증에 어떤 학교들이 지원되나요?", "현재 대부분의 4년제 대학교를 대상으로 지원하고 있습니다. " +
                "학교인증 시 오류가 발생한다면 아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.SERVICE));
        systemFaqRepository.save(SystemFaq.create("학교 인증을 진행하려고 하는데, 오류가 발생해요.", "아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.SERVICE));
        systemFaqRepository.save(SystemFaq.create("중/고등학교 동아리도 이용할 수 있나요?", "교내 동아리가 아닌 연합 동아리라는 카테고리로 동아리를 개설 후," +
                "이용하실 수 있습니다.", SystemFaqStatusType.SERVICE));

        systemFaqRepository.save(SystemFaq.create("아리아리는 무료 서비스인가요?", "네, 무료로 제공하는 플랫폼 서비스입니다.", SystemFaqStatusType.POLICY));
        systemFaqRepository.save(SystemFaq.create("부적절한 콘텐츠 신고는 어떻게 하나요?", "아리아리의 신고버튼을 통해서 신고를 진행해주시면," +
                "확인 후 조치를 취할 예정입니다. 신고 버튼이 없다면 아리아리 서비스 메일로 문의바랍니다.", SystemFaqStatusType.POLICY));

        systemFaqRepository.save(SystemFaq.create("서비스 업데이트 소식은 어디서 확인하나요?", "공지사항을 통해 서비스 업데이트 및 새로운 기능 소식을 " +
                "확인하실 수 있습니다.", SystemFaqStatusType.GENERAL));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void systemNoticeDataInit(){
        if(systemNoticeRepository.count() > 0){
            return;
        }

        systemNoticeRepository.save(SystemNotice.create(
                "안녕하세요? 아리아리입니다!",
                "동아리의 모집부터 지원, 관리까지,\n\n" +
                        "동아리의 A~Z를 모두 다루는 동아리 통합 플랫폼\n" +
                        "아리아리입니다.\n\n" +
                        "✅ 10분이면 모집공고 완성-!\n" +
                        "✅ 지원서 양식도 자동 추천-!\n" +
                        "✅ 우리 학교 동아리만 모아 보고-!\n" +
                        "✅ 동아리 관리의 기타 편의 서비스까지 모두-!\n" +
                        "✅ 카카오 로그인으로 간편하게-!\n\n" +
                        "아리아리에서 모든 것을 해결하세요✨✨✨"
        ));
    }

}
