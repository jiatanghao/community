package com.zte.oes.community.controller;

import com.zte.oes.community.dto.AccessTokenDto;
import com.zte.oes.community.dto.GithubUser;
import com.zte.oes.community.provider.GithubProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    private final GithubProvider provider;

    public AuthorizeController(GithubProvider provider) {
        this.provider = provider;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDto dto = new AccessTokenDto("25ebf453af2fb8dc34c8", "8ca5c4291ec823e94d89de2bb8f25a7e87994c42", code);
        dto.setState(state);
        dto.setRedirectUri("http://localhost/callback");
        String token = provider.getAccessToken(dto);
        GithubUser user = provider.getGithubUser(token);
        System.out.println(user.getName());
        return "index";
    }
}
