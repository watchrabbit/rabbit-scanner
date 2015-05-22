package com.watchrabbit.scanner.supervisor.strategy;

import java.util.List;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public interface FallbackValueGeneratorStrategy {

    List<String> prepareValues(RemoteWebDriver driver);
}
