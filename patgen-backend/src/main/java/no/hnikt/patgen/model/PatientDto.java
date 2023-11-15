package no.hnikt.patgen.model;

/**
 * Class for testpatient with randomized personal details
 * 
 * @author Chuck Norris
 *
 */
public class PatientDto {
	
	private String firstname; 
	private String lastname;
	private Integer age;
    private String streetNameAndNumber;
    private String postalCode;
	
	public PatientDto(String firstname, String lastname, Integer age,
     String streetNameAndNumber, String postalCode) {
		if (firstname == null || lastname == null || age == null
        || streetNameAndNumber == null || postalCode == null) {
			throw new IllegalArgumentException("null is not valid");
		}
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
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

	public Integer getAge() {
		return age;
	}

    public String getStreetNameAndNumber() {
        return streetNameAndNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
}
