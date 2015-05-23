package com.watchrabbit.scanner.supervisor.service;

import com.watchrabbit.scanner.supervisor.ContextTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mariusz
 */
public class OrderServiceIT extends ContextTestBase {

    @Autowired
    OrderService orderService;

    @Test
    public void shouldProcessAll() {
        orderService.inspectSite("asdas", "https://github.com/");
    }
}
