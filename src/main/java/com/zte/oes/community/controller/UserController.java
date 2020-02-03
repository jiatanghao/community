package com.zte.oes.community.controller;

import com.zte.oes.community.mapper.UserMapper;
import com.zte.oes.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin("*")
public class UserController {

    private final UserMapper mapper;

    public UserController(UserMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping("/user/{token}")
    public @ResponseBody User getUserByToken(@PathVariable("token") String token, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return mapper.findByToken(token);
    }
}
