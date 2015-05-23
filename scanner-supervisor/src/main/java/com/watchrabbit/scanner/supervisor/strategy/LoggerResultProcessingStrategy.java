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

import com.watchrabbit.scanner.supervisor.model.AttackResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mariusz
 */
public class LoggerResultProcessingStrategy implements ResultProcessingStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerResultProcessingStrategy.class);

    @Override
    public void onTestComplete(String addressId, String address, AttackResult result) {
        String description = "Attack Result: " + result.getVulnerability() + " of attack: " + result.getAttackData().getAttackId();

        LOGGER.debug("Results of testing {} \n" + description);
    }

}
