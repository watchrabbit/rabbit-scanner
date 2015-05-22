package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.crawler.driver.factory.RemoteWebDriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RemoteWebDriverFactory firefoxFactory;

    @Autowired
    SupervisorService supervisorService;

    @Override
    public void inspectSite(String addressId, String address) {
        RemoteWebDriver driver = firefoxFactory.produceDriver();
        try {
            supervisorService.inspectSite(addressId, address, driver);
        } finally {
            firefoxFactory.returnWebDriver(driver);
        }
    }

}
