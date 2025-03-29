package com.ariari.ariari.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("customAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("customAuth"));
    }

    @Bean
    public GroupedOpenApi test() {
        return GroupedOpenApi.builder()
                .group("01. 테스트 API")
                .packagesToScan("com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder()
                .group("02. 전체 API")
                .packagesToScan("com.ariari.ariari")
                .build();
    }

    @Bean
    public GroupedOpenApi auth() {
        return GroupedOpenApi.builder()
                .group("03. 인증(로그인) API")
                .packagesToScan("com.ariari.ariari.commons.auth", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi member() {
        return GroupedOpenApi.builder()
                .group("04. 회원 API")
                .packagesToScan("com.ariari.ariari.domain.member.member", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi school() {
        return GroupedOpenApi.builder()
                .group("05. 학교 API")
                .packagesToScan("com.ariari.ariari.domain.school", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi schoolAuth() {
        return GroupedOpenApi.builder()
                .group("06. 학교 인증 API")
                .packagesToScan("com.ariari.ariari.domain.school.auth", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi club() {
        return GroupedOpenApi.builder()
                .group("07. 동아리 API")
                .packagesToScan("com.ariari.ariari.domain.club.club", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubBookmark() {
        return GroupedOpenApi.builder()
                .group("08. 동아리 북마크 API")
                .packagesToScan("com.ariari.ariari.domain.club.bookmark", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubMember() {
        return GroupedOpenApi.builder()
                .group("09. 동아리 회원 API")
                .packagesToScan("com.ariari.ariari.domain.club.clubmember", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi recruitment() {
        return GroupedOpenApi.builder()
                .group("10. 모집 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.recruitment", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi recruitmentBookmark() {
        return GroupedOpenApi.builder()
                .group("11. 모집 북마크 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.bookmark", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi apply() {
        return GroupedOpenApi.builder()
                .group("12. 지원서 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.apply", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubActivity() {
        return GroupedOpenApi.builder()
                .group("13. 동아리 활동내역 API")
                .packagesToScan("com.ariari.ariari.domain.club.activity", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubEvent() {
        return GroupedOpenApi.builder()
                .group("14. 동아리 일정 API")
                .packagesToScan("com.ariari.ariari.domain.club.event", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubFinancial() {
        return GroupedOpenApi.builder()
                .group("15. 동아리 회계내역 API")
                .packagesToScan("com.ariari.ariari.domain.club.financial", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubQnA() {
        return GroupedOpenApi.builder()
                .group("16. 동아리 Q&A API")
                .packagesToScan("com.ariari.ariari.domain.club.question", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubFaq() {
        return GroupedOpenApi.builder()
                .group("17. 동아리 FAQ API")
                .packagesToScan("com.ariari.ariari.domain.club.faq", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi passReview() {
        return GroupedOpenApi.builder()
                .group("18. 동아리 합격 후기 API")
                .packagesToScan("com.ariari.ariari.domain.club.passreview", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubReview() {
        return GroupedOpenApi.builder()
                .group("19. 동아리 후기 API")
                .packagesToScan("com.ariari.ariari.domain.club.review", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi clubNotice() {
        return GroupedOpenApi.builder()
                .group("20. 동아리 알림 API")
                .packagesToScan("com.ariari.ariari.domain.club.notice", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi memberReport() {
        return GroupedOpenApi.builder()
                .group("21. 신고 API")
                .packagesToScan("com.ariari.ariari.domain.member.report"
                ,"com.ariari.ariari.domain.club.activity.report"
                ,"com.ariari.ariari.domain.club.passreview.report"
                ,"com.ariari.ariari.domain.club.report"
                ,"com.ariari.ariari.domain.club.activity.comment.report"
                ,"com.ariari.ariari.domain.club.review.report"
                ,"com.ariari.ariari.domain.club.question.report"
                ,"com.ariari.ariari.domain.recruitment.apply.report"
                ,"com.ariari.ariari.domain.recruitment.report"
                , "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi memberAlarm() {
        return GroupedOpenApi.builder()
                .group("22. 멤버 알림 API")
                .packagesToScan("com.ariari.ariari.domain.member.alarm", "com.ariari.ariari.test")
                .build();
    }


    @Bean
    public GroupedOpenApi clubAlarm() {
        return GroupedOpenApi.builder()
                .group("23. 동아리 알림 API")
                .packagesToScan("com.ariari.ariari.domain.club.alarm", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi SystemNotice() {
        return GroupedOpenApi.builder()
                .group("24. 시스템 공지사항 API")
                .packagesToScan("com.ariari.ariari.domain.system", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi systemFaq() {
        return GroupedOpenApi.builder()
                .group("25. 시스템 FAQ API")
                .packagesToScan("com.ariari.ariari.domain.system.faq", "com.ariari.ariari.test")
                .build();
    }

    @Bean
    public GroupedOpenApi applyForm() {
        return GroupedOpenApi.builder()
                .group("26. 동아리 지원 형식 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.applyform", "com.ariari.ariari.test")
                .build();
    }

}
