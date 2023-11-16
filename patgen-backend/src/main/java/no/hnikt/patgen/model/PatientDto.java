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
    private String sex;
	private Integer age;
    private String streetNameAndNumber;
    private String postalCode;
	
	public PatientDto(String firstname, String lastname, String sex, Integer age,
     String streetNameAndNumber, String postalCode) {
		if (firstname == null || lastname == null || sex == null || age == null
        || streetNameAndNumber == null || postalCode == null) {
			throw new IllegalArgumentException("null is not valid");
		}
		this.firstname = firstname;
		this.lastname = lastname;
        this.sex = sex;
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

    public String getSex(){
        return sex;
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
