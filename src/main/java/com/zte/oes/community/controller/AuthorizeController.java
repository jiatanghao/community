package com.zte.oes.community.controller;

import com.zte.oes.community.dto.AccessTokenDto;
import com.zte.oes.community.dto.GithubUser;
import com.zte.oes.community.mapper.UserMapper;
import com.zte.oes.community.model.User;
import com.zte.oes.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    private final GithubProvider provider;

    private final UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirect-uri}")
    private String redirectUri;

    public AuthorizeController(GithubProvider provider, UserMapper userMapper) {
        this.provider = provider;
        this.userMapper = userMapper;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDto dto = new AccessTokenDto(clientId, clientSecret, code);
        dto.setState(state);
        dto.setRedirectUri(redirectUri);
        String token = provider.getAccessToken(dto);
        GithubUser githubUser = provider.getGithubUser(token);
        if (githubUser != null) {
            // 登录成功，写session和cookie
            User user = new User();
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            user.setAccountId(String.valueOf(githubUser.getId()));
            userMapper.insertUser(user);
            Cookie cookie = new Cookie("token", user.getToken());
            cookie.setMaxAge(60);
            response.addCookie(cookie);
        }
        return "redirect:http://localhost:4200";
    }
}
