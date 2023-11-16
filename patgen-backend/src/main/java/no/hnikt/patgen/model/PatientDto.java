package no.hnikt.patgen.model;

import java.time.LocalDate;
import java.time.Period;

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
    private String city;
	
    
    public PatientDto(String firstname, String lastname, String sex, LocalDate birthDate,
    String streetNameAndNumber, String postalCode, String city) {
        if (firstname == null || lastname == null || sex == null || birthDate == null
        || streetNameAndNumber == null || postalCode == null || city == null) {
            throw new IllegalArgumentException("null is not valid");
		}
		this.firstname = firstname;
		this.lastname = lastname;
        this.sex = sex;
		this.birthDate = birthDate;
        this.streetNameAndNumber = streetNameAndNumber;
        this.postalCode = postalCode;
        this.city = city;
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
    
    public String getCity() {
        return city;
    }
}
