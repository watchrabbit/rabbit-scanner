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
package com.watchrabbit.scanner.generator.model;

/**
 *
 * @author Mariusz
 */
public class FieldValue {

    private String value;

    private Formality formality;

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

        private final FieldValue item;

        public Builder() {
            this.item = new FieldValue();
        }

        public Builder withValue(final String value) {
            this.item.value = value;
            return this;
        }

        public Builder withFormality(final Formality formality) {
            this.item.formality = formality;
            return this;
        }

        public FieldValue build() {
            return this.item;
        }
    }

}
