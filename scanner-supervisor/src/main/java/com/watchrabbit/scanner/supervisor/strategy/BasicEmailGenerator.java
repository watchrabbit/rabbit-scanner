package com.watchrabbit.scanner.supervisor.strategy;

import static java.util.Arrays.asList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mariusz
 */
public class BasicEmailGenerator implements EmailGenerator {

    private final Random random = new Random();

    private static final List<String> domains = asList("gmail.com", "yahoo.com", "facebook.com", "foomail.com");

    private static final List<String> names = asList("johnsmith", "mary.cooper", "leonard", "joseph.moody");

    @Override
    public String generateEmail() {
        String name = selectName();
        int number = selectNumber();
        String domain = selectDomain();
        return name + number + domain;
    }

    private String selectName() {
        return names.get(random.nextInt(names.size()));
    }

    private String selectDomain() {
        return domains.get(random.nextInt(domains.size()));
    }

    private int selectNumber() {
        return random.nextInt(30);
    }

}
