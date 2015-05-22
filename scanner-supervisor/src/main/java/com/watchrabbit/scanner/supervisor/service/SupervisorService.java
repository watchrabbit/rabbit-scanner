package com.watchrabbit.scanner.supervisor.service;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public interface SupervisorService {

    void inspectSite(String addressId, String address, RemoteWebDriver driver);

}
