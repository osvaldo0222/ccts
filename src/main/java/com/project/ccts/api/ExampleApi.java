package com.project.ccts.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExampleApi {

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ_PRIVILEGE')")
    public String helloAdmin() {
        return "Hello World ADMIN";
    }

    @GetMapping("/node")
    @PreAuthorize("hasAnyAuthority('NODE_READ_PRIVILEGE')")
    public String helloNode() {
        return "Hello World Node";
    }

}
