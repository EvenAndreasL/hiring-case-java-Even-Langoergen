package no.hnikt.patgen.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PatientDtoTest {

    @Test
    public void ctor_populatedAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", 4, "Eventyrland 1", "0001");

        assertEquals("Kyrre", actual.getFirstname());
        assertEquals("Kanin", actual.getLastname());
        assertEquals(Integer.valueOf(4), actual.getAge());
        assertEquals("Eventyrland 1", actual.getStreetNameAndNumber());
        assertEquals("0001", actual.getPostalCode());
    }

    @Test
    public void ctor_fullnameAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", 4, "Eventyrland 1", "0001");

        assertEquals("Kyrre Kanin", actual.getFullName());
    }

    @Test
    public void ctor_nullArgumentsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> new PatientDto(null, null, null, null, null));
    }

}
