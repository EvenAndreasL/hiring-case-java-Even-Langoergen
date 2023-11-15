package no.hnikt.patgen.component;

import java.util.Random;

import org.springframework.stereotype.Component;

/*
 * @author Even Langørgen
 */
@Component
public class SimplePostalCodeGenerator implements PostalCodeGenerator {

    private Random random = new Random(System.currentTimeMillis());
    
    public String postalCode() {
        int pCode = random.nextInt(9999) + 1;
        return String.format("%04d", pCode);
    }

    
}
