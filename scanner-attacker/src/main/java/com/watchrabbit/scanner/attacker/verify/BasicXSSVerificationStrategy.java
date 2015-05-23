package com.watchrabbit.scanner.attacker.verify;

import com.watchrabbit.scanner.attacker.model.Vulnerability;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mariusz
 */
public class BasicXSSVerificationStrategy implements VerificationStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicXSSVerificationStrategy.class);

    @Override
    public Vulnerability verify(RemoteWebDriver driver, long loadMilisec) {
        WebDriverWait wait = new WebDriverWait(driver, 1);
        try {
            wait.until((WebDriver predicatedDriver) -> false);
        } catch (TimeoutException ex) {
            LOGGER.info("Timed out on {}", driver.getCurrentUrl());
        }
        String result = (String) driver.executeScript("return window.w$");

        LOGGER.debug("XSS Attack result is {}", result);
        if (StringUtils.isNotBlank(result)) {
            return Vulnerability.EXISTS;
        } else {
            return Vulnerability.NONE;
        }
    }

}
