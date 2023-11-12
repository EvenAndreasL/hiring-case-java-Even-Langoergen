package no.hnikt.patgen.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import no.hnikt.patgen.config.WebConfig;

@WebMvcTest(HealthCheck.class)
@Import(WebConfig.class)
public class HealthCheckTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHealthCheck_everythingIsNormal_returnsOk() throws Exception {

        this.mockMvc.perform(get("/health-check")).andDo(print()).andExpectAll(
                status().isOk(),
                jsonPath("$.service").value("up"),
                jsonPath("$.health-patgen-backend").value("ok"));
    }
}
