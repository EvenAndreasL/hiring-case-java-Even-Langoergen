package no.hnikt.zipcodeservice.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import no.hnikt.zipcodeservice.exception.ThisIsJustTooHard;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.hnikt.zipcodeservice.exception.NotFoundException;
import no.hnikt.zipcodeservice.exception.ValidationException;

/**
 * @author Dolph Lundgren
 */
@RestController
@Validated
public class ZipCodeDeathTales {

    private Random random = new Random(System.currentTimeMillis());

    private static List<String> towns;

    static {
        towns = new ArrayList<>();
        towns.add("Arendal");
        towns.add("Bergen");
        towns.add("Drammen");
        towns.add("Egersund");
        towns.add("Fredrikstad");
        towns.add("Horten");
        towns.add("Kragerø");
        towns.add("Lillesand");
        towns.add("Mosjøen");
    }

    @GetMapping("/zipcode-details")
    public String zipCodeDetails(@RequestParam @NotBlank @Size(min = 4, max = 4) String zipCode) {

        validateZipCode(zipCode);

        int n = random.nextInt(5);

        if (n == 0) {
            throw new ThisIsJustTooHard("Agh! This is just too hard.");
        }

        if (n == 1) {
            throw new NotFoundException("Nope, never heard of it.");
        }

        return towns.get(random.nextInt(towns.size()));
    }

    private void validateZipCode(String zipCode) {
        if (!zipCode.matches("[0-9]+")) {
            throw new ValidationException("Numbers only please!");
        }
    }
}
