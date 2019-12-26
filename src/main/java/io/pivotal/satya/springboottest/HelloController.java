package io.pivotal.satya.springboottest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private HelloRepository repository;

    public HelloController(HelloRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello/{lastname}")
    public String hello(@PathVariable String lastName) {
        return repository.getFirstName(lastName).get().getFirstName();
    }
}
