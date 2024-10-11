package com.ariari.ariari.commons.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @Value("${kakao.login-uri}")
    private String LOCATION;

    @Value("${kakao.client.id}")
    private String CLIENT_ID;

    @Value("${kakao.redirect-uri}")
    private String REDIRECT_URI;

    @GetMapping("/login/page/kakao")
    public String authPage(Model model) {
        model.addAttribute("location", getWholeUri());
        return "login-page";
    }

    private String getWholeUri() {
        return LOCATION + "?"
                + "client_id=" + CLIENT_ID + "&"
                + "redirect_uri=" + REDIRECT_URI + "&"
                + "response_type=code";
    }

}
