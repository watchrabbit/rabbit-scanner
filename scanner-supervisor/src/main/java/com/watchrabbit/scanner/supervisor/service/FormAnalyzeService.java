package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.exception.InvalidFormStructureException;
import com.watchrabbit.scanner.supervisor.model.Form;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Mariusz
 */
public interface FormAnalyzeService {

    public Form prepareStructure(WebElement form) throws InvalidFormStructureException;

}
