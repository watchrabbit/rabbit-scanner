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
package com.watchrabbit.scanner.supervisor;

import com.watchrabbit.crawler.driver.EnableWebDrivers;
import com.watchrabbit.scanner.attacker.EnableAttackerService;
import com.watchrabbit.scanner.supervisor.strategy.BasicEmailGenerator;
import com.watchrabbit.scanner.supervisor.strategy.BasicFormDataGenerator;
import com.watchrabbit.scanner.supervisor.strategy.BasicPasswordGenerator;
import com.watchrabbit.scanner.supervisor.strategy.BasicUrlGenerator;
import com.watchrabbit.scanner.supervisor.strategy.ContentBasedValueGenerator;
import com.watchrabbit.scanner.supervisor.strategy.EmailGenerator;
import com.watchrabbit.scanner.supervisor.strategy.FallbackValueGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.FormDataGeneratorStrategy;
import com.watchrabbit.scanner.supervisor.strategy.PasswordGenerator;
import com.watchrabbit.scanner.supervisor.strategy.UrlGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Mariusz
 */
@Configuration
@EnableAttackerService
@EnableWebDrivers
@ComponentScan
public class SupervisorServiceConfig {

    @Bean
    @ConditionalOnMissingBean
    FallbackValueGeneratorStrategy fallbackValueGeneratorStrategy() {
        return new ContentBasedValueGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    FormDataGeneratorStrategy formDataGeneratorStrategy() {
        return new BasicFormDataGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    EmailGenerator emailGenerator() {
        return new BasicEmailGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    PasswordGenerator passwordGenerator() {
        return new BasicPasswordGenerator();
    }

    @Bean
    @ConditionalOnMissingBean
    UrlGenerator urlGenerator() {
        return new BasicUrlGenerator();
    }

}
