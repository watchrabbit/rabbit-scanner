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
