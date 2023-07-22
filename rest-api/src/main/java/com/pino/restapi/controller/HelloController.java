package com.pino.restapi.controller;

import com.pino.restapi.model.ContentDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping
    public ContentDTO hello() {
        return ContentDTO.of("Hello~");
    }

}
