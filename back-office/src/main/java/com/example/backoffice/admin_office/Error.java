package com.example.backoffice.admin_office;

import org.springframework.web.bind.annotation.RequestMapping;

public class Error {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
