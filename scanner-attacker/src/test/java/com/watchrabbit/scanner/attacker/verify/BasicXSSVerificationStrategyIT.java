package com.watchrabbit.scanner.attacker.verify;

import com.watchrabbit.commons.clock.Stopwatch;
import com.watchrabbit.crawler.driver.factory.RemoteWebDriverFactory;
import com.watchrabbit.scanner.attacker.ContextTestBase;
import com.watchrabbit.scanner.attacker.model.Vulnerability;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class BasicXSSVerificationStrategyIT extends ContextTestBase {

    @Autowired
    RemoteWebDriverFactory firefoxFactory;

    @Test
    public void shouldFindXss() {
        RemoteWebDriver driver = firefoxFactory.produceDriver();
        try {
            Stopwatch watch = Stopwatch
                    .createStarted(() -> driver.get("http://localhost:8080"));
            Vulnerability vulnerability = new BasicXSSVerificationStrategy()
                    .verify(driver, watch.getExecutionTime(TimeUnit.MILLISECONDS));

            assertThat(vulnerability).isEqualTo(Vulnerability.EXISTS);
        } finally {
            firefoxFactory.returnWebDriver(driver);
        }
    }
}
