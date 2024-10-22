package com.ariari.ariari.test;

import com.ariari.ariari.commons.manager.CustomEmailManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class CustomMailTestController {
    private final CustomEmailManager customEmailManager;

    @PostMapping("/text")
    public void sendMailByText() {
        customEmailManager.makeAuthCodeMail("iuholic83@naver.com");
    }

    @PostMapping("/html")
    public void sendMailByHtml() {
        customEmailManager.makeAuthCodeTemplateMail("iuholic83@naver.com");
    }
}
