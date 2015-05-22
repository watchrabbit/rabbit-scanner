package com.watchrabbit.scanner.supervisor.model;

import com.watchrabbit.scanner.attacker.verify.VerificationStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mariusz
 */
public class AttackData {

    private Form form;

    private List<Field> fields = new ArrayList<>();

    private String attackId;

    private VerificationStrategy verificationStrategy;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getAttackId() {
        return attackId;
    }

    public void setAttackId(String attackId) {
        this.attackId = attackId;
    }

    public VerificationStrategy getVerificationStrategy() {
        return verificationStrategy;
    }

    public void setVerificationStrategy(VerificationStrategy verificationStrategy) {
        this.verificationStrategy = verificationStrategy;
    }

    public static class Builder {

        private final AttackData item;

        public Builder() {
            this.item = new AttackData();
        }

        public Builder withForm(final Form form) {
            this.item.form = form;
            return this;
        }

        public Builder withFields(final List<Field> fields) {
            this.item.fields = fields;
            return this;
        }

        public Builder withAttackId(final String attackId) {
            this.item.attackId = attackId;
            return this;
        }

        public Builder withVerificationStrategy(final VerificationStrategy verificationStrategy) {
            this.item.verificationStrategy = verificationStrategy;
            return this;
        }

        public AttackData build() {
            return this.item;
        }
    }

}
