/*
 * Copyright 2015 Mariusz.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
