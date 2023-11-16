package no.hnikt.patgen.api;

import no.hnikt.patgen.component.FileStore;
import no.hnikt.patgen.component.AddressGenerator;
import no.hnikt.patgen.component.BirthdayGenerator;
import no.hnikt.patgen.component.NameGenerator;
import no.hnikt.patgen.component.PostalCodeGenerator;
import no.hnikt.patgen.enums.SexIso5218;
import no.hnikt.patgen.exception.NotFoundException;
import no.hnikt.patgen.model.LastNamesPutRequest;
import no.hnikt.patgen.model.PatientDto;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Duration;
import java.util.List;
import java.util.Random;


/**
 * @author Chuck Norris
 */
@RestController
public class PatientGenerator {

    private final WebClient localApiClient;

    
    private static final Logger LOG = LoggerFactory.getLogger(PatientGenerator.class);
    
    private Random random = new Random(System.currentTimeMillis());
    
    @Autowired
    private NameGenerator nameGenerator;
    
    @Autowired 
    private AddressGenerator addressGenerator;
    
    @Autowired 
    private PostalCodeGenerator postalCodeGenerator;

    @Autowired
    private BirthdayGenerator birthDateGenerator;

    @Autowired
    private FileStore fileStore;
    
    public PatientGenerator() {
        String zipCodeServiceUrl = System.getenv("ZIPCODESERVICE_EXTERNAL_URL");
        this.localApiClient = WebClient.create(zipCodeServiceUrl);
    }

    @GetMapping("/generate-patient")
    public PatientDto generatePatient(@RequestParam(value = "desiredSex", required = false) String desiredSex) {

        LocalDate birthDate = birthDateGenerator.generateBirthday();
        
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

        String city = fetchCity(postalCode);

        return new PatientDto(firstname, lastname, sex, birthDate, streetNameAndNumber, postalCode, city);
    }

    @GetMapping("/lastnames")
    public ResponseEntity<List<String>> getLastnames() {
        LOG.debug("Received GET to /lastnames.");
        try {
            List<String> list =  fileStore.readAllItems("lastnames.txt");
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/lastnames")
    public ResponseEntity<List<String>> addLastname(@RequestBody String lastname) {
        LOG.debug("Received POST {} to /lastnames.", lastname);
        try {
            fileStore.writeItemIfNotExist("lastnames.txt", lastname);
            List<String> list =  fileStore.readAllItems("lastnames.txt");
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/lastnames")
    public ResponseEntity<List<String>> deleteLastname(@RequestBody String lastname) {
        LOG.debug("Received DELETE {} to /lastnames.", lastname);
        try {
            fileStore.deleteItem("lastnames.txt", lastname);
            List<String> list =  fileStore.readAllItems("lastnames.txt");
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/lastnames")
    public ResponseEntity<List<String>> updateLastname(@RequestBody LastNamesPutRequest request) {
        LOG.debug("Received PUT {} to /lastnames.", request.oldName);
        try {
            fileStore.updateItem("lastnames.txt", request.oldName, request.newName);
            List<String> list =  fileStore.readAllItems("lastnames.txt");
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String fetchCity(String postalCode){
        String result = localApiClient
            .get()
            .uri("/zipcode-details?zipCode=" + postalCode)
            .retrieve()
            .onStatus(HttpStatus.NOT_FOUND::equals, res -> Mono.error(new NotFoundException("")))
            .bodyToMono(String.class)
            .onErrorResume(NotFoundException.class, res -> Mono.empty())
            .retryWhen(Retry.fixedDelay(15, Duration.ofMillis(100))
            .filter(this::is5xxServerError))
            .block();
        return result == null ? "" : result;
    }

    private boolean is5xxServerError(Throwable throwable) {
        return throwable instanceof WebClientResponseException && 
        ((WebClientResponseException) throwable).getStatusCode().is5xxServerError();
    }

}
