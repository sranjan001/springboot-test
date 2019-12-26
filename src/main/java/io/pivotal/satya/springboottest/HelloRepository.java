package io.pivotal.satya.springboottest;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HelloRepository {
    public Optional<Person> getFirstName(String lastName) {
        return null;
    }
}
