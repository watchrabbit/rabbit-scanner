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
