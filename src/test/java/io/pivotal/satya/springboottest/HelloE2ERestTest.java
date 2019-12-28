package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.person.Person;
import io.pivotal.satya.springboottest.person.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ERestTest {

    @Autowired
    private PersonRepository personRepository;

    @LocalServerPort
    private int port;

    @AfterEach
    public void tearDown() throws Exception {
        personRepository.deleteAll();
    }

    @Test
    public void shouldReturn200Ok() throws Exception {
        when()
                .get(String.format("http://localhost:%s/hello/Pan", port))
                .then()
                .statusCode(is(200));
    }

    @Test
    public void shouldReturnGreeting() throws Exception {
        Person peter = new Person("Peter", "Pan");
        personRepository.save(peter);

        when()
                .get(String.format("http://localhost:%s/hello/Pan", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello Peter Pan!"));
    }

    @Test
    public void shouldSavePerson() throws Exception {
        Person harry = new Person("Harry", "Sam");
        personRepository.save(harry);

        given()
                .body(harry)
                .contentType("application/json")
        .when()
                .post(String.format("http://localhost:%s/hello", port))
                .then()
                .statusCode(is(201));

        when()
                .get(String.format("http://localhost:%s/hello/Sam", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello Harry Sam!"));
    }
}
