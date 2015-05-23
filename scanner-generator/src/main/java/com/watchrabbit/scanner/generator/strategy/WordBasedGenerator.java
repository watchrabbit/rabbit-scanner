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
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mariusz
 */
public class WordBasedGenerator implements FallbackGenerator {

    private final Random random = new Random();

    @Override
    public FieldValue generate(List<String> descriptions, List<String> words) {
        return new FieldValue.Builder()
                .withFormality(Formality.LOW)
                .withValue(words.get(random.nextInt(words.size())))
                .build();
    }

}
