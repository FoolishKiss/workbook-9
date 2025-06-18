package com.pluralsight.NorthwindTradersAPI.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(@RequestParam(required = false) String country) {
        if (country != null) {
            return "Hello " + country;
        } else {
            return "Hello World";
        }
    }

    @GetMapping("/brian")
    public String brianHome() {
            return "Hello Brian";

    }



}
