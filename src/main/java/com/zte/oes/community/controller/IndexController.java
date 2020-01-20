package com.zte.oes.community.controller;

import com.zte.oes.community.mapper.UserMapper;
import com.zte.oes.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private final UserMapper userMapper;

    public IndexController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return "index";
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                User user = userMapper.findByToken(cookie.getValue());
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        return "index";
    }
}
