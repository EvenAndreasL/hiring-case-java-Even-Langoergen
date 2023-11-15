package no.hnikt.patgen.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleAgeGeneratorTest {
    private AgeGenerator simpleAgeGenerator;

    @BeforeEach
    void setUp() {
        this.simpleAgeGenerator = new SimpleAgeGenerator();
    }

    @Test
    void generateAge_returnsAge_ageIsWithinRange() {
        int actual = this.simpleAgeGenerator.generateAge();
        System.out.printf("Random age: %s%n", actual);

        assertTrue(actual >= 0 && actual <= 100, String.format("Age %s is not within 0 and 100", actual));
    }
}