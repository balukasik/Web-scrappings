package com.example.restservice;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import Movies.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/movie")
    public String movie(@RequestParam(value = "name", defaultValue = "World") String name) {
        Movie m = new Movie(name,new Date(), new String[]{"11:10", "12:13"});
        return m.toString();
    }
}