package io.pivotal.satya.springboottest;

import io.pivotal.satya.springboottest.helper.FileLoader;
import io.pivotal.satya.springboottest.weather.WeatherClient;
import io.pivotal.satya.springboottest.weather.WeatherResponse;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class WeatherClientMockRestServerIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherClient weatherClient;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetWeather() throws Exception {
        System.out.println(mockServer);
        mockServer.expect(once(), requestTo("http://localhost:8089/some-test-api-key/53.5511,9.9937"))
                .andRespond(withSuccess(FileLoader.read("classpath:weatherApiResponse.json"), MediaType.APPLICATION_JSON));

        Optional<WeatherResponse> weatherResponse = weatherClient.fetchWeather();

        System.out.println(weatherResponse.get());

        Optional<WeatherResponse> expectedResponse = Optional.of(new WeatherResponse("Rain"));
        assertThat(weatherResponse, is(expectedResponse));

        mockServer.verify();
    }
}
