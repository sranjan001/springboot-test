package io.pivotal.satya.springboottest;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ESeleniumTest {

    private WebDriver driver;

    @LocalServerPort
    private int localPort;

    @BeforeAll
    public static void setUpClass() throws Exception {
        ChromeDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.close();
    }

    //@Test
    public void helloPageHasTextHelloWorld() {
        driver.get(String.format("http://127.0.0.1:%s/hello", localPort));

        assertThat(driver.findElement(By.tagName("body")).getText(), containsString("Hello World!"));
    }
}

