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

import com.watchrabbit.scanner.attacker.model.Attack;
import com.watchrabbit.scanner.attacker.service.AttackService;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.Form;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.shuffle;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class AttackGeneratorServiceImpl implements AttackGeneratorService {

    @Autowired
    AttackService attackService;

    private static final List<String> ATTACT_VECTOR_TYPES = asList("search", "text", "url");

    @Override
    public AttackData prepareData(String pageId, Form form) {
        Attack attack = attackService.getRandomAttack();
        return prepareAttack(form, attack);
    }

    private AttackData prepareAttack(Form form, Attack attack) {
        List<Field> shuffled = new ArrayList<>(form.getFields());
        shuffle(shuffled);
        Field targetField = shuffled.stream()
                .filter(field -> field.getField().isDisplayed())
                .filter(field -> field.getField().isEnabled())
                .filter(field -> ATTACT_VECTOR_TYPES.contains(field.getField().getAttribute("type")))
                .filter(field -> field.getFormality() != null)
                .sorted((field1, field2) -> field1.getFormality().getFactor().compareTo(field2.getFormality().getFactor()))
                .findFirst()
                .get();
        return new AttackData.Builder()
                .withFields(asList(
                                produceAttackedField(targetField, attack.getAttackGenerator())
                        )
                ).withForm(form)
                .withVerificationStrategy(attack.getVerificationStrategy())
                .withAttackId(attack.getId())
                .build();
    }

    private Field produceAttackedField(Field original, Function<String, String> attackGenerator) {
        return new Field.Builder()
                .withElementType(original.getElementType())
                .withField(original.getField())
                .withFieldType(original.getFieldType())
                .withLabel(original.getLabel())
                .withValue(attackGenerator.apply(original.getValue()))
                .build();
    }

}
