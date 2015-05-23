package com.watchrabbit.scanner.supervisor.model;

import org.openqa.selenium.WebElement;

/**
 *
 * @author Mariusz
 */
public class Field {

    private WebElement field;

    private String label;

    private String placeholder;

    private ElementType elementType;

    private FieldType fieldType;

    private String value;

    private Formality formality;

    public WebElement getField() {
        return field;
    }

    public void setField(WebElement field) {
        this.field = field;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Formality getFormality() {
        return formality;
    }

    public void setFormality(Formality formality) {
        this.formality = formality;
    }

    public static class Builder {

        private final Field item;

        public Builder() {
            this.item = new Field();
        }

        public Builder withField(final WebElement field) {
            this.item.field = field;
            return this;
        }

        public Builder withLabel(final String label) {
            this.item.label = label;
            return this;
        }

        public Builder withPlaceholder(final String placeholder) {
            this.item.placeholder = placeholder;
            return this;
        }

        public Builder withElementType(final ElementType elementType) {
            this.item.elementType = elementType;
            return this;
        }

        public Builder withFieldType(final FieldType fieldType) {
            this.item.fieldType = fieldType;
            return this;
        }

        public Builder withValue(final String value) {
            this.item.value = value;
            return this;
        }

        public Builder withFormality(final Formality formality) {
            this.item.formality = formality;
            return this;
        }

        public Field build() {
            return this.item;
        }
    }

}
