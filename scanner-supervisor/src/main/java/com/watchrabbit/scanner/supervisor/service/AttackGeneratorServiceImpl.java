package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.attacker.model.Attack;
import com.watchrabbit.scanner.attacker.service.AttackService;
import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.Field;
import com.watchrabbit.scanner.supervisor.model.Form;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class AttackGeneratorServiceImpl implements AttackGeneratorService {

    @Autowired
    AttackService attackService;

    @Override
    public List<AttackData> prepareData(String pageId, Form form) {
        return asList(
                prepareXSS(pageId, form),
                prepareInjection(pageId, form)
        );
    }

    private AttackData prepareInjection(String pageId, Form form) {
        Attack attack = attackService.getRandomInjectionAttack();
        return prepareAttack(form, attack);
    }

    private AttackData prepareXSS(String pageId, Form form) {
        Attack attack = attackService.getRandomXSSAttack();
        return prepareAttack(form, attack);
    }

    private AttackData prepareAttack(Form form, Attack attack) {
        Field targetField = form.getFields().stream()
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
