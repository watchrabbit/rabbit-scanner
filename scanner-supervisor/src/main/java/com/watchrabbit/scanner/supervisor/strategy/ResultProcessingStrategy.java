package com.watchrabbit.scanner.supervisor.strategy;

import com.watchrabbit.scanner.supervisor.model.AttackResult;

/**
 *
 * @author Mariusz
 */
public interface ResultProcessingStrategy {

    void onTestComplete(String addressId, String address, AttackResult results);
}
