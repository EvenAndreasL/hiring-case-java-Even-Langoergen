package no.hnikt.patgen.enums;

/**
 * Definition of sex according to ISO-5218
 * 
 * @author Bruce Lee
 *
 */
public enum SexIso5218 {
	UNKNOWN(0),
	MALE(1),
	FEMALE(2),
	NOT_APPLICABLE(9);
	
	private Integer value;
	
	SexIso5218(Integer value) {
		this.value = value; 
	}
	
	public Integer getValue() {
		return value; 
	}
}
