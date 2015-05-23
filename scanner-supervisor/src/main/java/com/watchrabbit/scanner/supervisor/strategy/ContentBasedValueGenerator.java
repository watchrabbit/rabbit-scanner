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
package com.watchrabbit.scanner.supervisor.strategy;

import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public class ContentBasedValueGenerator implements FallbackValueGeneratorStrategy {

    @Override
    public List<String> prepareValues(RemoteWebDriver driver) {
        String pageText = driver.findElement(By.tagName("body")).getText();
        return Stream.of(pageText.split("\\s"))
                .map(wordWithPunctation -> wordWithPunctation.replaceAll("[^a-zA-Z]", ""))
                .map(mixCaseWord -> mixCaseWord.toLowerCase())
                .filter(anyWord -> anyWord.length() > 3 && anyWord.length() < 20)
                .distinct()
                .collect(toList());
    }

}
