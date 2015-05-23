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

import com.watchrabbit.scanner.supervisor.model.ElementType;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.FieldType;
import com.watchrabbit.scanner.supervisor.model.Form;
import com.watchrabbit.scanner.supervisor.model.Formality;
import java.util.List;
import java.util.Random;
import static java.util.stream.Collectors.toList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class BasicFormDataGenerator implements FormDataGeneratorStrategy {

    private final Random random = new Random();

    @Autowired
    EmailGenerator emailGenerator;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Autowired
    UrlGenerator urlGenerator;

    @Override
    public Form generateFormData(Form form, List<String> words) {
        fillPasswordFields(form);
        fillEmailFields(form);
        form.getFields().stream()
                .filter(field -> !field.getElementType().equals(ElementType.SELECT))
                .filter(field -> StringUtils.isEmpty(field.getValue()))
                .forEach(field -> fillField(field, words));
        return form;
    }

    private void fillPasswordFields(Form form) {
        List<Field> passwordInputs = form.getFields().stream()
                .filter(field -> field.getFieldType().equals(FieldType.PASSWORD))
                .collect(toList());
        if (!passwordInputs.isEmpty()) {
            String password = passwordGenerator.generatePassword();
            passwordInputs.forEach(field -> field.setValue(password));
            passwordInputs.forEach(field -> field.setFormality(Formality.AVERAGE));
        }
    }

    private void fillEmailFields(Form form) {
        List<Field> emailInputs = form.getFields().stream()
                .filter(field -> field.getFieldType().equals(FieldType.EMAIL)
                        || (field.getLabel() != null && field.getLabel().toLowerCase().contains("mail"))
                        || (field.getPlaceholder() != null && field.getPlaceholder().toLowerCase().contains("mail"))
                ).collect(toList());
        if (!emailInputs.isEmpty()) {
            String email = emailGenerator.generateEmail();
            emailInputs.forEach(field -> field.setValue(email));
            emailInputs.forEach(field -> field.setFormality(Formality.HIGH));
        }
    }

    private void fillField(Field field, List<String> words) {
        if (field.getFieldType().equals(FieldType.URL)) {
            field.setValue(urlGenerator.generateUrl());
            field.setFormality(Formality.AVERAGE);
        } else if (field.getFieldType().equals(FieldType.TEXT) || field.getFieldType().equals(FieldType.OTHER)) {
            field.setValue(selectWord(words));
            field.setFormality(Formality.LOW);
        }
    }

    private String selectWord(List<String> words) {
        return words.get(random.nextInt(words.size()));
    }

}
