package no.hnikt.patgen.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PatientDtoTest {

    @Test
    public void ctor_populatedAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", 4);

        assertEquals("Kyrre", actual.getFirstname());
        assertEquals("Kanin", actual.getLastname());
        assertEquals(Integer.valueOf(4), actual.getAge());
    }

    @Test
    public void ctor_fullnameAsExcpected() {
        PatientDto actual = new PatientDto("Kyrre", "Kanin", 4);

        assertEquals("Kyrre Kanin", actual.getFullName());
    }

    @Test
    public void ctor_nullArgumentsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> new PatientDto(null, null, null));
    }

}
