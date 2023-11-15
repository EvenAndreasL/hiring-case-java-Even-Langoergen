package no.hnikt.patgen.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplePostalCodeGeneratorTest {
    private PostalCodeGenerator postalCodeGenerator;

    @BeforeEach
    void setUp(){
        this.postalCodeGenerator = new SimplePostalCodeGenerator();
    }
    
    @Test
    void generatePostalCode_returnsPostalCode_PostalCodeIsWithinRange() {
        String pCode = this.postalCodeGenerator.postalCode();
        int actual = Integer.parseInt(pCode);
        System.out.print("Random Postal code: " + pCode);

        assertTrue(actual >= 1 && actual <= 9999, String.format("Postal code %s is not within 1 and 9999", actual));
    }
}
