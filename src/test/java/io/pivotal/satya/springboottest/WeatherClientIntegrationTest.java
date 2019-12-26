package io.pivotal.satya.springboottest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.pivotal.satya.springboottest.helper.FileLoader;
import io.pivotal.satya.springboottest.weather.WeatherClient;
import io.pivotal.satya.springboottest.weather.WeatherResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WeatherClientIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherClient weatherClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() throws IOException {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        setupStub();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    public void setupStub() throws IOException {
        wireMockServer.stubFor(get(WireMock.urlEqualTo("/some-test-api-key/53.5511,9.9937"))
                .willReturn(aResponse()
                        .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));
    }

    @Test
    public void shouldCallWeatherService() throws Exception {
        Optional<WeatherResponse> weatherResponse = weatherClient.fetchWeather();

        Optional<WeatherResponse> expectedResponse = Optional.of(new WeatherResponse("Rain"));
        assertThat(weatherResponse, is(expectedResponse));
    }

}
