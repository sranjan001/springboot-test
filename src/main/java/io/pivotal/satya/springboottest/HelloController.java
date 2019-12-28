package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.person.Person;
import io.pivotal.satya.springboottest.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class HelloController {

    private PersonRepository repository;

    @Autowired
    public HelloController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/hello/{lastName}")
    public String hello(@PathVariable String lastName) {
        Optional<Person> foundPerson = repository.findByLastName(lastName);

        return foundPerson
                .map(person -> String.format("Hello %s %s!",
                        person.getFirstName(),
                        person.getLastName()))
                .orElse(String.format("Who is this '%s' you're talking about?",
                        lastName));
    }

    @PostMapping(path = "/hello", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(Person person, HttpServletResponse response) {
        repository.save(person);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
