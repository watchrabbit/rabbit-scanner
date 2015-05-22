package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.crawler.driver.service.LoaderService;
import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.strategy.FallbackValueGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
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

    private static final List<String> ATTACT_VECTOR_TYPES = asList("email", "hidden", "password", "search", "text", "url");

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
    ResultService resultService;

    @Override
    public void inspectSite(String addressId, String address, RemoteWebDriver driver) {
        loadPage(address, driver);
        LOGGER.debug("Page {} loaded", address);
        int count = countForms(driver);
        LOGGER.debug("Found {} forms with attack vectors on page {}", count, address);
        if (count > 0) {
            List<String> values = fallbackValueGeneratorStrategy.prepareValues(driver);

            for (int formNo = 1; formNo <= count; formNo++) {
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
                .filter(form -> form.findElements(By.xpath(".//input")).stream()
                        .anyMatch(input -> isAnAttackVector(input))
                ).collect(toList())
                .size();
    }

    private boolean isAnAttackVector(WebElement input) {
        return ATTACT_VECTOR_TYPES.contains(input.getAttribute("type"));
    }

    private void inspectForm(String addressId, int formNo, String address, RemoteWebDriver driver, List<String> values) throws InvalidFormStructureException {
        LOGGER.debug("Inspecting form number {} on site {}", formNo, address);
        loadPage(address, driver);

        WebElement form = driver.findElement(By.xpath("//form[" + formNo + "]"));
        Form formStructure = formAnalyzeService.prepareStructure(form);
        formDataGeneratorStrategy.generateFormData(formStructure, values);
        List<AttackData> attacksDatas = attackGeneratorService.prepareData(addressId, formStructure);
        List<AttackResult> results = attacksDatas.stream()
                .map(data -> {
                    loadPage(address, driver);
                    return attackerService.performAttack(driver, data);
                }).collect(toList());
        resultService.onTestComplete(addressId, address, results);
    }

}
