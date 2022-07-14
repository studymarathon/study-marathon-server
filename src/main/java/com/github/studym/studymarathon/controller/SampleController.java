package com.github.studym.studymarathon.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/sample")
public class SampleController {


    @GetMapping(value = "/hello", produces = "text/plain; charset=UTF-8")
    public String hello() {

        log.info("MIME TYPE: " + MediaType.TEXT_PLAIN_VALUE);
        return "Testing hello XD";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("main")
    public String mainpage() {
        return "로그인이 필요한 메인페이지임";
    }

    @GetMapping("/title")
    public String title() {
        log.info("안녕하세용");


        return "안녕하세용";
    }


}
