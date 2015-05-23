package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.Form;

/**
 *
 * @author Mariusz
 */
public interface AttackGeneratorService {

    AttackData prepareData(String pageId, Form form);
}
