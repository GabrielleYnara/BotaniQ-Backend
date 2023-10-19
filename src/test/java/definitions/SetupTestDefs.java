package definitions;

import com.example.bontaniq.BontaniqApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BontaniqApplication.class)
public class SetupTestDefs {
    protected static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    protected String port;
}
