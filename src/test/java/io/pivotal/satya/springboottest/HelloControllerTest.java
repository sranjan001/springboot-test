package io.pivotal.satya.springboottest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

public class HelloControllerTest {

    private HelloController helloController;

    @Mock
    private HelloRepository helloRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        helloController = new HelloController(helloRepository);
    }

    @Test
    public void shouldReturnFirstName() {
        Person person = new Person("david", "john");

        given(helloRepository.findByLastName("john"))
                .willReturn(Optional.of(person));

        String firstName = helloController.hello("john");

        assertEquals("Hello david john!", firstName);
    }
}
