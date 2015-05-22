package com.watchrabbit.scanner.supervisor.model;

/**
 *
 * @author Mariusz
 */
public class AttackResult {

    private AttackData attackData;

    private int result;

    public AttackData getAttackData() {
        return attackData;
    }

    public void setAttackData(AttackData attackData) {
        this.attackData = attackData;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

        public Builder withResult(final int result) {
            this.item.result = result;
            return this;
        }

        public AttackResult build() {
            return this.item;
        }
    }

}
