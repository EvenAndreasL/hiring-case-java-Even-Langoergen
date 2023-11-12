package no.hnikt.patgen.api;

import no.hnikt.patgen.component.AgeGenerator;
import no.hnikt.patgen.component.NameGenerator;
import no.hnikt.patgen.config.WebConfig;
import no.hnikt.patgen.enums.SexIso5218;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientGenerator.class)
@Import(WebConfig.class)
class PatientGeneratorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameGenerator nameGenerator;

    @MockBean
    private AgeGenerator ageGenerator;

    @BeforeEach
    void setUp() {
        when(ageGenerator.generateAge()).thenReturn(42);
        when(nameGenerator.maleFirstName()).thenReturn("Jet");
        when(nameGenerator.femaleFirstName()).thenReturn("Nina");
        when(nameGenerator.lastName()).thenReturn("Li");
    }

    @Test
    void generatePatient_returnsPatient_allAttributesIsSet() throws Exception {

        this.mockMvc.perform(get("/generate-patient")).andDo(print()).andExpectAll(
                status().isOk(),
                jsonPath("$.age").value(42),
                jsonPath("$.firstname").value("Jet"),
                jsonPath("$.lastname").value("Li")
        );

        verify(this.ageGenerator, times(1)).generateAge();
        verify(this.nameGenerator, times(1)).maleFirstName();
        verify(this.nameGenerator, times(1)).lastName();
    }

    @Test
    @Disabled("Sjekk på enten mann eller kvinne, navn og kjønn, i oppgave 2.")
    void generatePatient_sexIsNotApplicable_returnsRandomNameAndSex() throws Exception {
        String notApplicable = SexIso5218.NOT_APPLICABLE.getValue().toString();
        String male = SexIso5218.MALE.getValue().toString();
        String female = SexIso5218.FEMALE.getValue().toString();

        this.mockMvc.perform(get("/generate-patient").param("sex", notApplicable)).andDo(print()).andExpectAll(
                status().isOk(),
                jsonPath("$.firstname", Matchers.anyOf(Matchers.is("Jet"), Matchers.is("Nina"))),
                jsonPath("$.sex", Matchers.anyOf(Matchers.is(male), Matchers.is(female)))
        );

        // Tip: Consider to verify that beans have been accessed here.
    }

    @Test
    @Disabled("Hente og oppdatere tilgjengelige etternavn, i oppgave 4.")
    void lastnames_getAll_returnsOk() throws Exception {
        this.mockMvc.perform(get("/lastnames")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    @Disabled("Legge til etternavn, i oppgave 4.")
    void lastnames_add_new_returnsOk() throws Exception {
        this.mockMvc.perform(post("/lastnames")
                        .with(csrf()) // This is required if you decide to activate csrf (cross site scripting) protection in WebConfig.java.
                        .content("Grindstein")
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}