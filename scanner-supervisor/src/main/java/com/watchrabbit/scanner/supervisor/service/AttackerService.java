package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.model.AttackData;
import com.watchrabbit.scanner.supervisor.model.AttackResult;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author Mariusz
 */
public interface AttackerService {

    AttackResult performAttack(RemoteWebDriver driver, AttackData attackData);
}
