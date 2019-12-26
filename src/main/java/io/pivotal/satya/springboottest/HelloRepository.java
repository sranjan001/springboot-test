package io.pivotal.satya.springboottest;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HelloRepository {
    public Optional<Person> findByLastName(String lastName) {
        return null;
    }
}
