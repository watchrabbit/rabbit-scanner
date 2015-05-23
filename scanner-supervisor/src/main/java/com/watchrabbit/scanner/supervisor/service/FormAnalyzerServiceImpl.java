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

import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.ElementType;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.FieldType;
import com.watchrabbit.scanner.supervisor.model.Form;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class FormAnalyzerServiceImpl implements FormAnalyzeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FormAnalyzerServiceImpl.class);

    @Override
    public Form prepareStructure(WebElement formElement) throws InvalidFormStructureException {
        Form form = new Form.Builder()
                .withForm(formElement)
                .build();
        locateSendButton(formElement, form);
        List<WebElement> fieldElements = locateElements(formElement);
        for (int i = 0; i < fieldElements.size(); i++) {
            WebElement fieldElement = fieldElements.get(i);
            String label = extractLabel(formElement, i);

            ElementType elementType = ElementType.parse(fieldElement.getTagName().toUpperCase());
            FieldType fieldType = FieldType.parse(fieldElement.getAttribute("type").toUpperCase());

            String placeholder = fieldElement.getAttribute("placeholder");

            form.getFields().add(new Field.Builder()
                    .withElementType(elementType)
                    .withField(fieldElement)
                    .withFieldType(fieldType)
                    .withLabel(label)
                    .withPlaceholder(placeholder)
                    .build()
            );
        }
        return form;
    }

    private void locateSendButton(WebElement formElement, Form form) throws InvalidFormStructureException {
        List<WebElement> submit = formElement.findElements(By.xpath(".//button[@type='submit']"));
        if (submit.isEmpty()) {
            submit = formElement.findElements(By.xpath(".//input[@type='submit']"));
        }
        if (!submit.isEmpty()) {
            form.setSendButton(submit.get(0));
        } else {
            Optional<WebElement> findAny = formElement.findElements(By.xpath(".//input")).stream()
                    .filter(input -> input.isDisplayed())
                    .filter(input -> input.isEnabled())
                    .findAny();
            form.setSendButton(findAny.orElseThrow(() -> new InvalidFormStructureException("Cannot locate send button")));
        }
    }

    private List<WebElement> locateElements(WebElement formElement) {
        return formElement.findElements(By.xpath(".//*[self::input or self::textarea or self::select]"));
    }

    private String extractLabel(WebElement formElement, int i) {
        List<WebElement> labels = formElement.findElements(By.xpath("((.//*[self::input or self::textarea or self::select])[" + (i + 1) + "]/preceding::label)[last()]"));
        if (!labels.isEmpty()) {
            WebElement labelElement = labels.get(0);
            return labelElement.getText();
        }
        return null;
    }

}
