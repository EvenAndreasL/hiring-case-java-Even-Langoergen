package no.hnikt.patgen.api;

import no.hnikt.patgen.component.AddressGenerator;
import no.hnikt.patgen.component.BirthdayGenerator;
import no.hnikt.patgen.component.FileStore;
import no.hnikt.patgen.component.NameGenerator;
import no.hnikt.patgen.component.PostalCodeGenerator;
import no.hnikt.patgen.config.WebConfig;
import no.hnikt.patgen.enums.SexIso5218;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDate;

@WebMvcTest(PatientGenerator.class)
@Import(WebConfig.class)
class PatientGeneratorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameGenerator nameGenerator;

    @MockBean
    private BirthdayGenerator birthdayGenerator;

    @MockBean
    private AddressGenerator addressGenerator;

    @MockBean
    private PostalCodeGenerator postalCodeGenerator;

    @MockBean
    private FileStore fileStore;

    @BeforeEach
    void setUp() {
        when(birthdayGenerator.generateBirthday()).thenReturn(LocalDate.now());
        when(nameGenerator.maleFirstName()).thenReturn("Jet");
        when(nameGenerator.femaleFirstName()).thenReturn("Nina");
        when(nameGenerator.lastName()).thenReturn("Li");
        when(addressGenerator.streetNameAndNumber()).thenReturn("Hollywood 42");
        when(postalCodeGenerator.postalCode()).thenReturn("1337");
    }

    @Test
    void generatePatient_returnsPatient_allAttributesIsSet() throws Exception {

        this.mockMvc.perform(get("/generate-patient")).andDo(print()).andExpectAll(
                status().isOk(),
                //jsonPath("$.age").value(42),
                jsonPath("$.firstname", Matchers.anyOf(Matchers.is("Jet"), Matchers.is("Nina"))),
                jsonPath("$.sex", Matchers.anyOf(Matchers.is("1"), Matchers.is("2"))),
                jsonPath("$.lastname").value("Li"),
                jsonPath("$.streetNameAndNumber").value("Hollywood 42"),
                jsonPath("$.postalCode").value("1337")
        );

        verify(this.birthdayGenerator, times(1)).generateBirthday();
        //verify(this.nameGenerator, times(1)).maleFirstName();
        verify(this.nameGenerator, times(1)).lastName();
        verify(this.addressGenerator, times(1)).streetNameAndNumber();
        verify(this.postalCodeGenerator, times(1)).postalCode();
    }

    @Test
    //("Sjekk på enten mann eller kvinne, navn og kjønn, i oppgave 2.")
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
    //("Hente og oppdatere tilgjengelige etternavn, i oppgave 4.")
    void lastnames_getAll_returnsOk() throws Exception {
        this.mockMvc.perform(get("/lastnames")).andDo(print()).andExpect(status().isOk());
    }


    @Test
    //("Legge til etternavn, i oppgave 4.")
    void lastnames_add_new_returnsOk() throws Exception {
        this.mockMvc.perform(post("/lastnames")
                        .with(csrf()) // This is required if you decide to activate csrf (cross site scripting) protection in WebConfig.java.
                        .content("Grindstein")
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}