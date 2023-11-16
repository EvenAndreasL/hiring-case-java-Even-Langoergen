package no.hnikt.patgen.api;

import no.hnikt.patgen.component.AddressGenerator;
import no.hnikt.patgen.component.AgeGenerator;
import no.hnikt.patgen.component.NameGenerator;
import no.hnikt.patgen.component.PostalCodeGenerator;
import no.hnikt.patgen.enums.SexIso5218;
import no.hnikt.patgen.model.PatientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Chuck Norris
 */
@RestController
public class PatientGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PatientGenerator.class);

    private Random random = new Random(System.currentTimeMillis());

    @Autowired
    private AgeGenerator ageGenerator;

    @Autowired
    private NameGenerator nameGenerator;

    @Autowired 
    private AddressGenerator addressGenerator;

    @Autowired 
    private PostalCodeGenerator postalCodeGenerator;

    @GetMapping("/generate-patient")
    public PatientDto generatePatient(@RequestParam(value = "desiredSex", required = false) String desiredSex) {
        Integer age = ageGenerator.generateAge();
        
        String firstname = "";
        String sex = SexIso5218.UNKNOWN.getValue().toString();
        
        if (desiredSex != null && (desiredSex.equalsIgnoreCase("male") 
        || desiredSex.equalsIgnoreCase("female"))) {

            firstname = (desiredSex.equalsIgnoreCase("male")) 
            ? nameGenerator.maleFirstName() : nameGenerator.femaleFirstName();

            sex = (desiredSex.equalsIgnoreCase("male")) 
            ? SexIso5218.MALE.getValue().toString() : SexIso5218.FEMALE.getValue().toString();
    
        } else {
            // If desiredSex is not provided or not valid, generate a random sex
            int n = random.nextInt(2) + 1;
            firstname = (n == 1) ? nameGenerator.maleFirstName() 
            : nameGenerator.femaleFirstName();
            sex = (n == 1) ? SexIso5218.MALE.getValue().toString() 
            : SexIso5218.FEMALE.getValue().toString();
        }
                
        String lastname = nameGenerator.lastName();
        
        String streetNameAndNumber = addressGenerator.streetNameAndNumber();
        String postalCode = postalCodeGenerator.postalCode();

        return new PatientDto(firstname, lastname, sex, age, streetNameAndNumber, postalCode);
    }

    @GetMapping("/lastnames")
    public ResponseEntity<List<String>> getLastnames() {
        LOG.debug("Received GET to /lastnames.");
        return new ResponseEntity<>(lastnamesMockData(), HttpStatus.OK);
    }

    @PostMapping("/lastnames")
    public ResponseEntity<List<String>> addLastname(@RequestBody String lastname) {
        LOG.debug("Received POST {} to /lastnames.", lastname);
        return new ResponseEntity<>(lastnamesMockData(), HttpStatus.OK);
    }

    @DeleteMapping("/lastnames")
    public ResponseEntity<ArrayList<String>> deleteLastname(@RequestBody String lastname) {
        LOG.debug("Received DELETE {} to /lastnames.", lastname);
        return new ResponseEntity<>(lastnamesMockData(), HttpStatus.OK);
    }

    private ArrayList<String> lastnamesMockData() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Nilsen");
        data.add("Olsen");
        return data;
    }

}
