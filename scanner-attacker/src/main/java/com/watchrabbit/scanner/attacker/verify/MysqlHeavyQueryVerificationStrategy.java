package com.watchrabbit.scanner.attacker.verify;

import com.watchrabbit.scanner.attacker.model.Vulnerability;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public class MysqlHeavyQueryVerificationStrategy implements VerificationStrategy {

    @Override
    public Vulnerability verify(RemoteWebDriver driver, long loadMilisec) {
        if (loadMilisec > 15) {
            return Vulnerability.VERY_HIGH;
        } else if (loadMilisec > 10) {
            return Vulnerability.HIGH;
        } else if (loadMilisec > 5) {
            return Vulnerability.AVERAGE;
        } else {
            return Vulnerability.VERY_LOW;
        }
    }

}
