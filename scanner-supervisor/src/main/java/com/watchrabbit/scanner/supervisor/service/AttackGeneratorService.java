package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.Form;
import java.util.List;

/**
 *
 * @author Mariusz
 */
public interface AttackGeneratorService {

    List<AttackData> prepareData(String pageId, Form form);
}
