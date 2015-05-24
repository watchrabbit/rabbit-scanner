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

import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.strategy.FallbackValueGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.ResultProcessingStrategy;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class SupervisorServiceImpl implements SupervisorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupervisorServiceImpl.class);

    private static final List<String> ATTACT_VECTOR_TYPES = asList("search", "text", "url");

    private final Random random = new Random();

    @Autowired
    FallbackValueGeneratorStrategy fallbackValueGeneratorStrategy;

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

    @Override
    public void inspectSite(String addressId, String address, RemoteWebDriver driver) {
        LOGGER.debug("Page {} loaded", address);
        List<String> values = fallbackValueGeneratorStrategy.prepareValues(driver);

        List<WebElement> elements = driver.findElements(By.xpath("//form"));
        shuffle(elements);
        elements.stream()
                .filter(this::isOpenForAttack)
                .findAny()
                .ifPresent(form -> inspectForm(addressId, form, address, driver, values));
    }

    private boolean isOpenForAttack(WebElement form) {
        return form.findElements(By.xpath(".//input")).stream()
                .filter(field -> field.isDisplayed())
                .filter(field -> field.isEnabled())
                .anyMatch(input -> isAnAttackVector(input));
    }

    private boolean isAnAttackVector(WebElement input) {
        return ATTACT_VECTOR_TYPES.contains(input.getAttribute("type"));
    }

    private void inspectForm(String addressId, WebElement form, String address, RemoteWebDriver driver, List<String> values) {
        LOGGER.debug("Inspecting form on site {}", address);
        try {
            Form formStructure = formAnalyzeService.prepareStructure(form);
            formDataGeneratorStrategy.generateFormData(formStructure, values);
            AttackData data = attackGeneratorService.prepareData(addressId, formStructure);

            AttackResult result = attackerService.performAttack(address, driver, data);
            resultProcessingStrategy.onTestComplete(addressId, address, result);

        } catch (Exception ex) {
            LOGGER.error("Failed to analyze form", ex);
        }
    }

}
