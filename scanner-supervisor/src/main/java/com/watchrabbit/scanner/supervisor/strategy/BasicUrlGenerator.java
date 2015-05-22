package com.watchrabbit.scanner.supervisor.strategy;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mariusz
 */
public class BasicUrlGenerator implements UrlGenerator {

    private final Random random = new Random();

    private static final List<String> domains = asList("https://gmail.com", "https://yahoo.com", "https://facebook.com", "https://foomail.com");

    @Override
    public String generateUrl() {
        return domains.get(random.nextInt(domains.size()));
    }

}
