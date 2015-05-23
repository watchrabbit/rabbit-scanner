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
