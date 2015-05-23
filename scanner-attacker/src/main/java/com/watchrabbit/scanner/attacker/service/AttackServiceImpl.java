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
package com.watchrabbit.scanner.attacker.service;

import com.watchrabbit.scanner.attacker.model.Attack;
import com.watchrabbit.scanner.attacker.verify.BasicXSSVerificationStrategy;
import com.watchrabbit.scanner.attacker.verify.MySqlHeavyQueryVerificationStrategy;
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
                .withVerificationStrategy(new MySqlHeavyQueryVerificationStrategy())
                .build();
    }

}
