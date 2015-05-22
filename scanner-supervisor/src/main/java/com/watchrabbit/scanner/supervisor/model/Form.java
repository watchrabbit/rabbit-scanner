package com.watchrabbit.scanner.supervisor.model;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Mariusz
 */
public class Form {

    private WebElement form;

    private List<Field> fields = new ArrayList<>();

    private WebElement sendButton;

    public WebElement getForm() {
        return form;
    }

    public void setForm(WebElement form) {
        this.form = form;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public WebElement getSendButton() {
        return sendButton;
    }

    public void setSendButton(WebElement sendButton) {
        this.sendButton = sendButton;
    }

    public static class Builder {

        private final Form item;

        public Builder() {
            this.item = new Form();
        }

        public Builder withForm(final WebElement form) {
            this.item.form = form;
            return this;
        }

        public Builder withFields(final List<Field> fields) {
            this.item.fields = fields;
            return this;
        }

        public Builder withSendButton(final WebElement sendButton) {
            this.item.sendButton = sendButton;
            return this;
        }

        public Form build() {
            return this.item;
        }
    }

}
