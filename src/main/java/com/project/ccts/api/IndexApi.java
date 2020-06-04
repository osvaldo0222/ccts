package com.project.ccts.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/")
public class IndexApi {

    @GetMapping("/")
    public String index() {
        return "Hello World index";
    }
}
