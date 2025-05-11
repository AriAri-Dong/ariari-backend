package com.ariari.ariari.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SentryTestController {

    @GetMapping("/error")
    public String errorTest() {
        throw new RuntimeException("테스트용 예외입니다.");
    }
}
