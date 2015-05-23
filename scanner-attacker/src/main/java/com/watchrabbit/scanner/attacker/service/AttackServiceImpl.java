package com.watchrabbit.scanner.attacker.service;

import com.watchrabbit.scanner.attacker.model.Attack;
import com.watchrabbit.scanner.attacker.verify.BasicXSSVerificationStrategy;
import com.watchrabbit.scanner.attacker.verify.MysqlHeavyQueryVerificationStrategy;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class AttackServiceImpl implements AttackService {

    private final Random random = new Random();

    @Override
    public Attack getRandomAttack() {
        if (random.nextBoolean()) {
            return getRandomInjectionAttack();
        } else {
            return getRandomXSSAttack();
        }
    }

    private Attack getRandomXSSAttack() {
        return new Attack.Builder()
                .withAttackGenerator((value) -> value + "<img src=\"asd.png?\" onerror=\"window.w$ = 'xss1'\"/>")
                .withId("xss1")
                .withVerificationStrategy(new BasicXSSVerificationStrategy())
                .build();
    }

    private Attack getRandomInjectionAttack() {
        return new Attack.Builder()
                .withAttackGenerator(value
                        -> value + "' and (select count(*) from information_schema.columns, information_schema.columns T1, information_schema.columns T2) and 1='1"
                ).withId("injectionMysql1")
                .withVerificationStrategy(new MysqlHeavyQueryVerificationStrategy())
                .build();
    }

}
