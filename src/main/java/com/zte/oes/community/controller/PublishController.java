package com.zte.oes.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishController {

    @GetMapping("/publish")
    public String toPublish() {
        return "publish";
    }
}
