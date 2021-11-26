package com.example.jants;

import org.springframework.web.bind.annotation.RequestMapping;

public class Error {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
