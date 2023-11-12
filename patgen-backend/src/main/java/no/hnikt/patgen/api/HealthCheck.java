package no.hnikt.patgen.api;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Danny Trejo
 */
@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public ResponseEntity<HashMap<String, String>> healthCheck() {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("service", "up");
        data.put("health-patgen-backend", "ok");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
