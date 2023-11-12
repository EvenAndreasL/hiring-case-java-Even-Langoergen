package no.hnikt.zipcodeservice.api;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tom Berenger
 */
@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public ResponseEntity<HashMap<String, String>> healthCheck() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("service", "up");
        // Warning is expected because zipcode-service tries to emulate an unstable
        // endpoint.
        data.put("health-zipcode-backend", "warning");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
