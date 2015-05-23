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
package com.watchrabbit.scanner.attacker.model;

import com.watchrabbit.scanner.attacker.verify.VerificationStrategy;
import java.util.function.Function;

/**
 *
 * @author Mariusz
 */
public class Attack {

    private String id;

    private VerificationStrategy verificationStrategy;

    private Function<String, String> attackGenerator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VerificationStrategy getVerificationStrategy() {
        return verificationStrategy;
    }

    public void setVerificationStrategy(VerificationStrategy verificationStrategy) {
        this.verificationStrategy = verificationStrategy;
    }

    public Function<String, String> getAttackGenerator() {
        return attackGenerator;
    }

    public void setAttackGenerator(Function<String, String> attackGenerator) {
        this.attackGenerator = attackGenerator;
    }

    public static class Builder {

        private final Attack item;

        public Builder() {
            this.item = new Attack();
        }

        public Builder withId(final String id) {
            this.item.id = id;
            return this;
        }

        public Builder withVerificationStrategy(final VerificationStrategy verificationStrategy) {
            this.item.verificationStrategy = verificationStrategy;
            return this;
        }

        public Builder withAttackGenerator(final Function<String, String> attackGenerator) {
            this.item.attackGenerator = attackGenerator;
            return this;
        }

        public Attack build() {
            return this.item;
        }
    }

}
