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
