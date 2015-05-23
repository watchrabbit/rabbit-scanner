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
package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.crawler.driver.factory.RemoteWebDriverFactory;
import com.watchrabbit.scanner.supervisor.ContextTestBase;
import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.ResultProcessingStrategy;
import java.net.URISyntaxException;
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

    @Autowired
    AttackGeneratorService attackGeneratorService;

    @Autowired
    AttackerService attackerService;

    @Autowired
    ResultProcessingStrategy resultProcessingStrategy;

    @Test
    public void shouldAnalyzeForm() throws InvalidFormStructureException {
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

    @Test
    public void shouldPerformAttacks() throws InvalidFormStructureException, URISyntaxException {
        RemoteWebDriver driver = firefoxFactory.produceDriver();
        try {
            driver.get("https://ssl.allegro.pl/fnd/registration/");
            List<WebElement> findElements = driver.findElements(By.xpath("//form"));

            Form prepareStructure = formAnalyzeService.prepareStructure(findElements.get(0));
            formDataGeneratorStrategy.generateFormData(prepareStructure, asList("Test"));

            AttackData prepareData = attackGeneratorService.prepareData("adsa1232", prepareStructure);

            AttackResult attackResult = attackerService.performAttack("https://ssl.allegro.pl/fnd/registration/", driver, prepareData);

            resultProcessingStrategy.onTestComplete("adsa1232", "https://ssl.allegro.pl/fnd/registration/", (attackResult));
            assertThat(prepareStructure.getFields()).isNotEmpty();
            assertThat(attackResult.isFormSent()).isFalse();
        } finally {
            firefoxFactory.returnWebDriver(driver);
        }

    }
}
