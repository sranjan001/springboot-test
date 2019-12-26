package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.person.Person;
import io.pivotal.satya.springboottest.person.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloController {

    private PersonRepository repository;

    public HelloController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello/{lastname}")
    public String hello(@PathVariable String lastName) {
        Optional<Person> foundPerson = repository.findByLastName(lastName);

        return foundPerson
                .map(person -> String.format("Hello %s %s!",
                        person.getFirstName(),
                        person.getLastName()))
                .orElse(String.format("Who is this '%s' you're talking about?",
                        lastName));
    }
}
