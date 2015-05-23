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
package com.watchrabbit.scanner.supervisor.model;

import com.watchrabbit.scanner.attacker.model.Vulnerability;

/**
 *
 * @author Mariusz
 */
public class AttackResult {

    private AttackData attackData;

    private String resultAddress;

    private boolean formSent;

    private Vulnerability vulnerability;

    public AttackData getAttackData() {
        return attackData;
    }

    public void setAttackData(AttackData attackData) {
        this.attackData = attackData;
    }

    public String getResultAddress() {
        return resultAddress;
    }

    public void setResultAddress(String resultAddress) {
        this.resultAddress = resultAddress;
    }

    public boolean isFormSent() {
        return formSent;
    }

    public void setFormSent(boolean formSent) {
        this.formSent = formSent;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

    public static class Builder {

        private final AttackResult item;

        public Builder() {
            this.item = new AttackResult();
        }

        public Builder withAttackData(final AttackData attackData) {
            this.item.attackData = attackData;
            return this;
        }

        public Builder withResultAddress(final String resultAddress) {
            this.item.resultAddress = resultAddress;
            return this;
        }

        public Builder withFormSent(final boolean formSent) {
            this.item.formSent = formSent;
            return this;
        }

        public Builder withVulnerability(final Vulnerability vulnerability) {
            this.item.vulnerability = vulnerability;
            return this;
        }

        public AttackResult build() {
            return this.item;
        }
    }

}
