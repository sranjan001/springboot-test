package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.person.Person;
import io.pivotal.satya.springboottest.person.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelloControllerTest {

    private HelloController helloController;

    @Mock
    private PersonRepository helloRepository;

    @Spy
    private PersonRepository spyRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        helloController = new HelloController(helloRepository);
    }

    @Test
    public void shouldReturnFullNameOfThePerson() {
        Person person = new Person("david", "john");

        given(helloRepository.findByLastName("john"))
                .willReturn(Optional.of(person));

        String fullName = helloController.hello("john");

        assertEquals("Hello david john!", fullName);
    }

    @Test
    public void shouldTellIfPersonIsUnknown() {
        given(helloRepository.findByLastName(anyString()))
                .willReturn(Optional.empty());

        String fullName = helloController.hello("harry");
//        assertEquals("Who is this 'harry' you're talking about?", fullName);
        assertThat(fullName, is("Who is this 'harry' you're talking about?"));
    }

    @Test
    public void shouldSaveFirstNameAndLastName() {
        HelloController helloSaveController = new HelloController(spyRepository);
        Person person = new Person("Harry", "Sam");
        helloSaveController.save(person, mock(HttpServletResponse.class));

        //Check if the save method in repository is called
        verify(spyRepository).save(person);
    }
}
