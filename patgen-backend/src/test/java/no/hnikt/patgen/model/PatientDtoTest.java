package no.hnikt.patgen.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import no.hnikt.patgen.enums.SexIso5218;

public class PatientDtoTest {

    @Test
    public void ctor_populatedAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", SexIso5218.MALE.getValue().toString(), LocalDate.now(),
         "Eventyrland 1", "0001", "Bergen");

        assertEquals("Kyrre", actual.getFirstname());
        assertEquals("Kanin", actual.getLastname());
        assertEquals(SexIso5218.MALE.getValue().toString(), actual.getSex());
        assertEquals(Integer.valueOf(0), actual.getAge());
        assertEquals("Eventyrland 1", actual.getStreetNameAndNumber());
        assertEquals("0001", actual.getPostalCode());
    }

    @Test
    public void ctor_fullnameAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", SexIso5218.MALE.getValue().toString(), LocalDate.now(), "Eventyrland 1", "0001", "Bergen");

        assertEquals("Kyrre Kanin", actual.getFullName());
    }

    @Test
    public void ctor_nullArgumentsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> new PatientDto(null, null, null, null, null, null, null));
    }

}
