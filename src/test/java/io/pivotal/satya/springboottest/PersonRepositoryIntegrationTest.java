package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.person.Person;
import io.pivotal.satya.springboottest.person.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

//Integration test - Test with database. (H2 for fast test run)

//@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchPerson()  {
        Person person = new Person("john", "david");

        personRepository.save(person);
        Optional<Person> foundPerson = personRepository.findByLastName("david");

        assertThat(foundPerson, is(Optional.of(person)));
    }
}
