package definitions;

import com.example.bontaniq.BontaniqApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BontaniqApplication.class)
public class SetupTestDefs {
    protected static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    protected String port;

    protected HttpHeaders createAuthenticatedHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + token);
        return headers;
    }
}
