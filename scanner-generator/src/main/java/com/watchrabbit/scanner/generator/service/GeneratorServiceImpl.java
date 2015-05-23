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
package com.watchrabbit.scanner.generator.service;

import com.watchrabbit.scanner.generator.model.FieldValue;
import com.watchrabbit.scanner.generator.strategy.EmailGenerator;
import com.watchrabbit.scanner.generator.strategy.FallbackGenerator;
import com.watchrabbit.scanner.generator.strategy.PasswordGenerator;
import com.watchrabbit.scanner.generator.strategy.UrlGenerator;
import com.watchrabbit.scanner.generator.strategy.ValueGenerator;
import static java.util.Collections.emptyList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired(required = false)
    List<ValueGenerator> valueGenerators = emptyList();

    @Autowired
    FallbackGenerator fallbackGenerator;

    @Autowired
    UrlGenerator urlGenerator;

    @Autowired
    EmailGenerator emailGenerator;

    @Autowired
    PasswordGenerator passwordGenerator;

    @Override
    public boolean isEmail(List<String> descriptions) {
        return emailGenerator.accepts(descriptions);
    }

    @Override
    public boolean isPassword(List<String> descriptions) {
        return passwordGenerator.accepts(descriptions);
    }

    @Override
    public FieldValue generateValue(List<String> descriptions, List<String> words) {
        if (emailGenerator.accepts(descriptions)) {
            return emailGenerator.generate(descriptions, words);
        }
        if (urlGenerator.accepts(descriptions)) {
            return urlGenerator.generate(descriptions, words);
        }
        if (passwordGenerator.accepts(descriptions)) {
            return passwordGenerator.generate(descriptions, words);
        }

        Optional<ValueGenerator> optionalGenerator = valueGenerators.stream()
                .filter(generator -> generator.accepts(descriptions))
                .findFirst();
        if (optionalGenerator.isPresent()) {
            return optionalGenerator.get().generate(descriptions, words);
        }

        return fallbackGenerator.generate(descriptions, words);
    }

}
