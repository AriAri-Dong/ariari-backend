package com.ariari.ariari.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${server-secret.host}")
    private String SERVER_HOST;

    @Value("${server-secret.port}")
    private String SERVER_PORT;

    private final String TEST_SERVER_URL = "http://" + SERVER_HOST + ":" + SERVER_PORT + "/";

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

    private String getServerUrl() {
        return SERVER_HOST + ":" + SERVER_PORT;
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
                .packagesToScan("com.ariari.ariari.commons.auth")
                .build();
    }

    @Bean
    public GroupedOpenApi member() {
        return GroupedOpenApi.builder()
                .group("04. 회원 API")
                .packagesToScan("com.ariari.ariari.domain.member.member")
                .build();
    }

    @Bean
    public GroupedOpenApi school() {
        return GroupedOpenApi.builder()
                .group("05. 학교 API")
                .packagesToScan("com.ariari.ariari.domain.school")
                .build();
    }

    @Bean
    public GroupedOpenApi schoolAuth() {
        return GroupedOpenApi.builder()
                .group("06. 학교 인증 API")
                .packagesToScan("com.ariari.ariari.domain.school.auth")
                .build();
    }

    @Bean
    public GroupedOpenApi club() {
        return GroupedOpenApi.builder()
                .group("07. 동아리 API")
                .packagesToScan("com.ariari.ariari.domain.club.club")
                .build();
    }

    @Bean
    public GroupedOpenApi clubBookmark() {
        return GroupedOpenApi.builder()
                .group("08. 동아리 북마크 API")
                .packagesToScan("com.ariari.ariari.domain.club.bookmark")
                .build();
    }

    @Bean
    public GroupedOpenApi clubMember() {
        return GroupedOpenApi.builder()
                .group("09. 동아리 회원 API")
                .packagesToScan("com.ariari.ariari.domain.club.clubmember")
                .build();
    }

    @Bean
    public GroupedOpenApi recruitment() {
        return GroupedOpenApi.builder()
                .group("10. 모집 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.recruitment")
                .build();
    }

    @Bean
    public GroupedOpenApi recruitmentBookmark() {
        return GroupedOpenApi.builder()
                .group("11. 모집 북마크 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.bookmark")
                .build();
    }

    @Bean
    public GroupedOpenApi apply() {
        return GroupedOpenApi.builder()
                .group("12. 지원서 API")
                .packagesToScan("com.ariari.ariari.domain.recruitment.apply")
                .build();
    }

    @Bean
    public GroupedOpenApi clubActivity() {
        return GroupedOpenApi.builder()
                .group("13. 동아리 활동내역 API")
                .packagesToScan("com.ariari.ariari.domain.club.activity")
                .build();
    }

    @Bean
    public GroupedOpenApi clubEvent() {
        return GroupedOpenApi.builder()
                .group("14. 동아리 일정 API")
                .packagesToScan("com.ariari.ariari.domain.club.event")
                .build();
    }

    @Bean
    public GroupedOpenApi clubFinancial() {
        return GroupedOpenApi.builder()
                .group("15. 동아리 회계내역 API")
                .packagesToScan("com.ariari.ariari.domain.club.financial")
                .build();
    }

    @Bean
    public GroupedOpenApi clubQnA() {
        return GroupedOpenApi.builder()
                .group("16. 동아리 Q&A API")
                .packagesToScan("com.ariari.ariari.domain.club.question")
                .build();
    }

    @Bean
    public GroupedOpenApi clubFaq() {
        return GroupedOpenApi.builder()
                .group("17. 동아리 FAQ API")
                .packagesToScan("com.ariari.ariari.domain.club.faq")
                .build();
    }

    @Bean
    public GroupedOpenApi passReview() {
        return GroupedOpenApi.builder()
                .group("18. 동아리 합격 후기 API")
                .packagesToScan("com.ariari.ariari.domain.club.passreview")
                .build();
    }

    @Bean
    public GroupedOpenApi clubReview() {
        return GroupedOpenApi.builder()
                .group("19. 동아리 후기 API")
                .packagesToScan("com.ariari.ariari.domain.club.review")
                .build();
    }

    @Bean
    public GroupedOpenApi clubNotice() {
        return GroupedOpenApi.builder()
                .group("20. 동아리 알림 API")
                .packagesToScan("com.ariari.ariari.domain.club.notice")
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
                ,"com.ariari.ariari.domain.recruitment.report")
                .build();
    }

    @Bean
    public GroupedOpenApi memberAlarm() {
        return GroupedOpenApi.builder()
                .group("22. 멤버 알림 API")
                .packagesToScan("com.ariari.ariari.domain.member.alarm")
                .build();
    }


    @Bean
    public GroupedOpenApi clubAlarm() {
        return GroupedOpenApi.builder()
                .group("23. 동아리 알림 API")
                .packagesToScan("com.ariari.ariari.domain.club.alarm")
                .build();
    }

}
