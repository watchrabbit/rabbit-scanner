package com.watchrabbit.scanner.supervisor.strategy;

import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Mariusz
 */
public class BasicPasswordGenerator implements PasswordGenerator {

    @Override
    public String generatePassword() {
        return RandomStringUtils.randomAscii(8);
    }

}
