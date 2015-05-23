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

import com.watchrabbit.scanner.generator.model.FieldValue;
import com.watchrabbit.scanner.generator.service.GeneratorService;
import com.watchrabbit.scanner.supervisor.model.ElementType;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.FieldType;
import com.watchrabbit.scanner.supervisor.model.Form;
import static java.util.Arrays.asList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class BasicFormDataGenerator implements FormDataGeneratorStrategy {

    @Autowired
    GeneratorService generatorService;

    @Override
    public Form generateFormData(Form form, List<String> words) {
        fillPasswordFields(form, words);
        fillEmailFields(form, words);
        form.getFields().stream()
                .filter(field -> !field.getElementType().equals(ElementType.SELECT))
                .filter(field -> !field.isFilled())
                .forEach(field -> fillField(field, words));
        return form;
    }

    private void fillPasswordFields(Form form, List<String> words) {
        List<Field> passwordInputs = form.getFields().stream()
                .filter(field -> !field.isFilled())
                .filter(field -> generatorService.isPassword(getDescription(field)))
                .collect(toList());
        if (!passwordInputs.isEmpty()) {
            FieldValue password = generatorService.generateValue(getDescription(passwordInputs.get(0)), words);
            passwordInputs.forEach(field -> field.setValue(password.getValue()));
            passwordInputs.forEach(field -> field.setFormality(password.getFormality()));
        }
    }

    private void fillEmailFields(Form form, List<String> words) {
        List<Field> emailInputs = form.getFields().stream()
                .filter(field -> !field.isFilled())
                .filter(field -> generatorService.isEmail(getDescription(field)))
                .collect(toList());
        if (!emailInputs.isEmpty()) {
            FieldValue email = generatorService.generateValue(getDescription(emailInputs.get(0)), words);
            emailInputs.forEach(field -> field.setValue(email.getValue()));
            emailInputs.forEach(field -> field.setFormality(email.getFormality()));
        }
    }

    private List<String> getDescription(Field field) {
        return asList(
                field.getFieldType().name() != null ? field.getFieldType().name().toLowerCase() : "",
                field.getElementType().name() != null ? field.getElementType().name().toLowerCase() : "",
                field.getLabel() != null ? field.getLabel().toLowerCase() : "",
                field.getPlaceholder() != null ? field.getPlaceholder().toLowerCase() : ""
        );
    }

    private void fillField(Field field, List<String> words) {
        if (field.getFieldType().equals(FieldType.URL)
                || field.getFieldType().equals(FieldType.TEXT)
                || field.getFieldType().equals(FieldType.OTHER)) {
            FieldValue fieldValue = generatorService.generateValue(getDescription(field), words);
            field.setValue(fieldValue.getValue());
            field.setFormality(fieldValue.getFormality());
        }
    }

}
