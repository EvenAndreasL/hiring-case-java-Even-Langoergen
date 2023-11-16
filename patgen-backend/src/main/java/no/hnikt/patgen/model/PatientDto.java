package no.hnikt.patgen.model;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class for testpatient with randomized personal details
 * 
 * @author Chuck Norris
 *
 */
public class PatientDto {
	
	private String firstname; 
	private String lastname;
    private String sex;
	private LocalDate birthDate;
    private String streetNameAndNumber;
    private String postalCode;
	
	public PatientDto(String firstname, String lastname, String sex, LocalDate birthDate,
     String streetNameAndNumber, String postalCode) {
		if (firstname == null || lastname == null || sex == null || birthDate == null
        || streetNameAndNumber == null || postalCode == null) {
			throw new IllegalArgumentException("null is not valid");
		}
		this.firstname = firstname;
		this.lastname = lastname;
        this.sex = sex;
		this.birthDate = birthDate;
        this.streetNameAndNumber = streetNameAndNumber;
        this.postalCode = postalCode;
	}
	
	public String getFullName() {
		return firstname + " " + lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

    public String getSex(){
        return sex;
    }

    public LocalDate getBirthdate(){
        return birthDate;
    }
    
	public Integer getAge() {
		return Period.between(birthDate, LocalDate.now()).getYears();
	}

    public String getStreetNameAndNumber() {
        return streetNameAndNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
}
