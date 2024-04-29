package ggudock.domain.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/login/adult")
//    public String kakaoLoginURL() {
//
//        String REST_API_KEY = "025c046de1c1a4c49a6243124c8a312c";
//        String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/login";
//
//        String kakaoLoginURL = String.format("kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code", REST_API_KEY, REDIRECT_URI);
//        return kakaoLoginURL;
//
//    }
}
