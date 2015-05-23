package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.commons.clock.Stopwatch;
import com.watchrabbit.commons.exception.SystemException;
import com.watchrabbit.crawler.driver.service.LoaderService;
import com.watchrabbit.scanner.attacker.model.Vulnerability;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import com.watchrabbit.scanner.supervisor.model.ElementType;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.FieldType;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class AttackerServiceImpl implements AttackerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttackerServiceImpl.class);

    private final Random random = new Random();

    @Autowired
    LoaderService loaderService;

    @Override
    public AttackResult performAttack(String originalAdress, RemoteWebDriver driver, AttackData data) {
        data.getForm().getFields().stream()
                .filter(field -> field.getField().isDisplayed())
                .filter(field -> field.getField().isEnabled())
                .forEach(this::fill);
        data.getFields().forEach(this::fillAttacked);

        Stopwatch stopwatch = Stopwatch.createStarted(() -> {
            data.getForm().getSendButton().click();
            loaderService.waitFor(driver);
        });
        Vulnerability vulnerability = data.getVerificationStrategy()
                .verify(driver, stopwatch.getExecutionTime(TimeUnit.MILLISECONDS));

        boolean sent = isFormSent(originalAdress, driver.getCurrentUrl());
        return new AttackResult.Builder()
                .withAttackData(data)
                .withResultAddress(driver.getCurrentUrl())
                .withVulnerability(vulnerability)
                .withFormSent(sent)
                .build();
    }

    private void fill(Field field) {
        if (field.getElementType().equals(ElementType.SELECT)) {
            fillSelect(field);
        } else if (field.getFieldType().equals(FieldType.CHECKBOX)) {
            fillCheckbox(field);
        } else {
            fillTextField(field);
        }
    }

    private void fillSelect(Field field) {
        Select select = new Select(field.getField());

        long validSelectedOptions = select.getAllSelectedOptions().stream()
                .map(element -> element.getAttribute("value"))
                .filter(label -> !"-".equals(label))
                .filter(StringUtils::isNotBlank)
                .count();

        if (validSelectedOptions == 0) {
            int indexToSelect = random.nextInt(select.getOptions().size());
            select.selectByIndex(indexToSelect);
        }
    }

    private void fillCheckbox(Field field) {
        field.getField().sendKeys(Keys.SPACE);
    }

    private void fillAttacked(Field field) {
        try {
            field.getField().clear();
        } catch (WebDriverException ex) {
            LOGGER.error("Cannot clear input!", ex);
        }
        fillTextField(field);
    }

    private void fillTextField(Field field) {
        if (StringUtils.isNotBlank(field.getValue())) {
            field.getField().sendKeys(field.getValue());
        }
    }

    private boolean isFormSent(String originalAdress, String currentUrl) {
        try {
            return !new URI(originalAdress.replaceAll("#.*", "").replaceAll("\\?.*", ""))
                    .equals(new URI(currentUrl.replaceAll("#.*", "").replaceAll("\\?.*", "")));
        } catch (URISyntaxException ex) {
            throw new SystemException("Wrong uri: " + originalAdress + " " + currentUrl, ex);
        }
    }

}
