package com.watchrabbit.scanner.supervisor.util;

import com.google.common.base.Predicate;
import com.watchrabbit.crawler.driver.util.LoadingStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mariusz
 */
@Component
public class AnypageLoadingStrategy implements LoadingStrategy {

    @Override
    public boolean shouldWait(RemoteWebDriver driver) {
        return true;
    }

    @Override
    public int getWaitTime() {
        return 1;
    }

    @Override
    public Predicate<WebDriver> hasFinishedProcessing() {
        return (WebDriver driver) -> false;
    }

}
