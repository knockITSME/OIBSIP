package com.obisip.digital_library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/books")
    public String books() {
        return "books";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard() {
        return "admin";
    }

    @GetMapping("/admin/books")
    public String manageBooks() {
        return "admin/books";
    }

    @GetMapping("/admin/members")
    public String manageMembers() {
        return "admin/members";
    }

    @GetMapping("/admin/borrowed")
    public String borrowedBooks() {
        return "admin/borrowed";
    }

    @GetMapping("/admin/queries")
    public String queries() {
        return "admin/queries";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}