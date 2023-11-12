package no.hnikt.zipcodeservice.api;

import no.hnikt.zipcodeservice.config.WebConfig;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZipCodeDeathTales.class)
@Import(WebConfig.class)
public class ZipCodeDeathTalesTest {

    @Autowired
    private MockMvc mockMvc;

    private List<String> possibleTowns = Arrays.asList("Arendal", "Bergen", "Drammen", "Egersund", "Fredrikstad",
            "Horten", "Kragerø", "Lillesand", "Mosjøen");

    @Test
    void zipCodeDetails_zipCodeWithCharacters_400BadRequest() throws Exception {
        this.mockMvc.perform(get("/zipcode-details").param("zipCode", "abcd"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @RepeatedTest(10)
    void zipCodeDetails_validZipCode_mightOrMightNot() throws Exception {
        List<Integer> expectedStatuses = Arrays.asList(200, 404, 500);
        MvcResult result = this.mockMvc.perform(get("/zipcode-details").param("zipCode", "1234")).andDo(print())
                .andReturn();

        assertTrue(expectedStatuses.contains(result.getResponse().getStatus()),
                String.format("Status is one of %s", expectedStatuses));
        if (result.getResponse().getStatus() == 200) {
            String resultBody = result.getResponse().getContentAsString();
            assertTrue(possibleTowns.contains(resultBody), "Returned town.");
        }
    }
}