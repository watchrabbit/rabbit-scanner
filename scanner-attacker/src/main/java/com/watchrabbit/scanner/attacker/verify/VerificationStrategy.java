package com.watchrabbit.scanner.attacker.verify;

import com.watchrabbit.scanner.attacker.model.Vulnerability;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public interface VerificationStrategy {

    Vulnerability verify(RemoteWebDriver driver, long loadMilisec);
}
