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
	
	public PatientDto(String firstname, String lastname, Integer age) {
		if (firstname == null || lastname == null || age == null) {
			throw new IllegalArgumentException("null is not valid");
		}
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
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

}
