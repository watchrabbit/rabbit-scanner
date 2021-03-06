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
package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.crawler.driver.factory.RemoteWebDriverFactory;
import com.watchrabbit.crawler.driver.service.LoaderService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mariusz
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    RemoteWebDriverFactory firefoxFactory;

    @Autowired
    SupervisorService supervisorService;

    @Autowired
    LoaderService loaderService;

    @Override
    public void inspectSite(String addressId, String address) {
        RemoteWebDriver driver = firefoxFactory.produceDriver();
        loadPage(address, driver);
        try {
            supervisorService.inspectSite(addressId, address, driver);
            firefoxFactory.returnWebDriver(driver);
        } catch (RuntimeException ex) {
            LOGGER.error("Catched error, closing browser", ex);
            driver.close();
        }
    }

    private void loadPage(String address, RemoteWebDriver driver) {
        driver.get(address);
        loaderService.waitFor(driver);
    }

}
