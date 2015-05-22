package com.watchrabbit.scanner.supervisor.strategy;

import com.watchrabbit.scanner.supervisor.model.Form;
import java.util.List;

/**
 *
 * @author Mariusz
 */
public interface FormDataGeneratorStrategy {

    Form generateFormData(Form form, List<String> words);
}
