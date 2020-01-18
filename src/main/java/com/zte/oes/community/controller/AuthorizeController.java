package com.zte.oes.community.controller;

import com.zte.oes.community.dto.AccessTokenDto;
import com.zte.oes.community.dto.GithubUser;
import com.zte.oes.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    private final GithubProvider provider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirect-uri}")
    private String redirectUri;

    public AuthorizeController(GithubProvider provider) {
        this.provider = provider;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state, HttpServletRequest request) {
        AccessTokenDto dto = new AccessTokenDto(clientId, clientSecret, code);
        dto.setState(state);
        dto.setRedirectUri(redirectUri);
        String token = provider.getAccessToken(dto);
        GithubUser user = provider.getGithubUser(token);
        if (user != null) {
            // 登录成功，写session和cookie
            request.getSession().setAttribute("user", user);
        }
        return "redirect:/";
    }
}
