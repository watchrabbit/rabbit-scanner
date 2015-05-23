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
package com.watchrabbit.scanner.generator.strategy;

import com.watchrabbit.scanner.generator.model.FieldValue;
import com.watchrabbit.scanner.generator.model.Formality;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mariusz
 */
public class BasicEmailGenerator implements EmailGenerator {

    private final Random random = new Random();

    private static final List<String> domains = asList("gmail.com", "yahoo.com", "facebook.com");

    private static final List<String> names = asList("johnsmith", "mary.cooper", "leonard", "joseph.moody");

    @Override
    public boolean accepts(List<String> descriptions) {
        return descriptions.stream()
                .anyMatch(description -> description.contains("mail"));
    }

    @Override
    public FieldValue generate(List<String> descriptions, List<String> words) {
        String name = selectName();
        int number = selectNumber();
        String domain = selectDomain();
        return new FieldValue.Builder()
                .withFormality(Formality.HIGH)
                .withValue(name + number + "@" + domain)
                .build();
    }

    private String selectName() {
        return names.get(random.nextInt(names.size()));
    }

    private String selectDomain() {
        return domains.get(random.nextInt(domains.size()));
    }

    private int selectNumber() {
        return random.nextInt(30);
    }

}
