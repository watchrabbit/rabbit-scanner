package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.crawler.driver.factory.RemoteWebDriverFactory;
import com.watchrabbit.scanner.supervisor.ContextTestBase;
import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
import static java.util.Arrays.asList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class FormAnalyzerServiceIT extends ContextTestBase {

    @Autowired
    RemoteWebDriverFactory firefoxFactory;

    @Autowired
    FormAnalyzeService formAnalyzeService;

    @Autowired
    FormDataGeneratorStrategy formDataGeneratorStrategy;

    @Test
    public void should() throws InvalidFormStructureException {
        RemoteWebDriver driver = firefoxFactory.produceDriver();
        try {
            driver.get("https://reg.ebay.pl/reg/FullReg");
            List<WebElement> findElements = driver.findElements(By.xpath("//form"));

            Form prepareStructure = formAnalyzeService.prepareStructure(findElements.get(0));
            formDataGeneratorStrategy.generateFormData(prepareStructure, asList("Test"));

            assertThat(prepareStructure.getFields()).isNotEmpty();
        } finally {
            firefoxFactory.returnWebDriver(driver);
        }

    }
}
