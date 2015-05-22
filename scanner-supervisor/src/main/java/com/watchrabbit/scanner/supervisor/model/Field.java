package com.watchrabbit.scanner.supervisor.model;

import org.openqa.selenium.WebElement;

/**
 *
 * @author Mariusz
 */
public class Field {

    private WebElement field;

    private String label;

    private ElementType elementType;

    private FieldType fieldType;

    private String value;

    private Formality formality;

    public Formality getFormality() {
        return formality;
    }

    public void setFormality(Formality formality) {
        this.formality = formality;
    }

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

        public Field build() {
            return this.item;
        }
    }

}
