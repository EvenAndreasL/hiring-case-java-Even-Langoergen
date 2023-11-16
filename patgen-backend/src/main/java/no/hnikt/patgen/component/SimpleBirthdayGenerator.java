package no.hnikt.patgen.component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class SimpleBirthdayGenerator implements BirthdayGenerator{

    public LocalDate generateBirthday() {
        return LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 100))));
    }
  
}
