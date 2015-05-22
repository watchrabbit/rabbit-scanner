package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.model.AttackResult;
import java.util.List;

/**
 *
 * @author Mariusz
 */
public interface ResultService {

    void onTestComplete(String addressId, String address, List<AttackResult> results);
}
