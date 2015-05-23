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

import com.watchrabbit.crawler.driver.service.LoaderService;
import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.strategy.FallbackValueGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.ResultProcessingStrategy;
import static java.util.Arrays.asList;
import java.util.List;
import static java.util.stream.Collectors.toList;
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

    @Autowired
    FallbackValueGeneratorStrategy fallbackValueGeneratorStrategy;

    @Autowired
    LoaderService loaderService;

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
        loadPage(address, driver);
        LOGGER.debug("Page {} loaded", address);
        int count = countForms(driver);
        LOGGER.debug("Found {} forms with attack vectors on page {}", count, address);
        if (count > 0) {
            List<String> values = fallbackValueGeneratorStrategy.prepareValues(driver);

            for (int formNo = 0; formNo < count; formNo++) {
                try {
                    inspectForm(addressId, formNo, address, driver, values);
                } catch (InvalidFormStructureException ex) {
                    LOGGER.error("Invalid form structure on page " + address, ex);
                }
            }
        }
    }

    private void loadPage(String address, RemoteWebDriver driver) {
        driver.get(address);
        loaderService.waitFor(driver);
    }

    private int countForms(RemoteWebDriver driver) {
        return driver.findElements(By.xpath("//form")).stream()
                .filter(this::isOpenForAttack)
                .collect(toList())
                .size();
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

    private void inspectForm(String addressId, int formNo, String address, RemoteWebDriver driver, List<String> values) throws InvalidFormStructureException {
        LOGGER.debug("Inspecting form number {} on site {}", formNo, address);
        try {
            loadPage(address, driver);

            WebElement form = driver.findElements(By.xpath("//form")).get(formNo);
            if (isOpenForAttack(form)) {
                Form formStructure = formAnalyzeService.prepareStructure(form);
                formDataGeneratorStrategy.generateFormData(formStructure, values);
                AttackData data = attackGeneratorService.prepareData(addressId, formStructure);

                AttackResult result = attackerService.performAttack(address, driver, data);
                resultProcessingStrategy.onTestComplete(addressId, address, result);
            }
        } catch (Exception ex) {
            LOGGER.error("Failed to analyze form id " + formNo, ex);
        }
    }

}
