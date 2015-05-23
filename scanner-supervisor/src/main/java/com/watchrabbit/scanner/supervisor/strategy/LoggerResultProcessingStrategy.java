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
