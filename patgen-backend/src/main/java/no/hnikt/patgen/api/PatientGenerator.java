package no.hnikt.patgen.api;

import no.hnikt.patgen.component.AgeGenerator;
import no.hnikt.patgen.component.NameGenerator;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chuck Norris
 */
@RestController
public class PatientGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PatientGenerator.class);

    @Autowired
    private AgeGenerator ageGenerator;

    @Autowired
    private NameGenerator nameGenerator;

    @GetMapping("/generate-patient")
    public PatientDto generatePatient() {
        Integer age = ageGenerator.generateAge();

        String firstname = nameGenerator.maleFirstName();
        String lastname = nameGenerator.lastName();
        return new PatientDto(firstname, lastname, age);
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
