package no.hnikt.patgen.component;

import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * @author Steven Seagal
 */
@Component
public class SimpleAgeGenerator implements AgeGenerator {

    private Random random = new Random(System.currentTimeMillis());

    @Override
    public Integer generateAge() {
        return random.nextInt(100) + 1;
    }

}
