package no.hnikt.patgen.component;

public interface NameGenerator {

	/**
	 * Generate a random male first/given name.
	 * @return a randomized male name
	 */
	String maleFirstName();

	/**
	 * Generate a random female first/given name.
	 * @return a randomized female name
	 */

	String femaleFirstName();

	/**
	 * Generate a random last name/family name/surname.
	 * @return a randomized last name
	 */

	String lastName();

}