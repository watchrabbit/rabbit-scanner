package com.watchrabbit.scanner.attacker.verify;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public interface VerificationStrategy {

    int verify(RemoteWebDriver driver, long loadMilisec);
}
