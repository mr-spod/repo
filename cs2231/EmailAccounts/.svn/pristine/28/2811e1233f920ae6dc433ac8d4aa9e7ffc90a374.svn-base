import components.map.Map1L;
import components.map.Map;

/**
 * Implementation of {@code EmailAccount}.
 * 
 * @author Sean O'Donnell
 * 
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */
	
	private String firstName;
	private String lastName;
	private String emailAddress;
	
	private static Map<String, Integer> nameMap;
	
	private static int registerName(String lastName) {
		int count = 1;
		if (nameMap.hasKey(lastName)) {
			count = nameMap.value(lastName);
			count++;
			nameMap.remove(lastName);
		}
		nameMap.add(lastName, count);
		return count;
	}
	

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     * 
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        if (nameMap == null) {
        		nameMap = new Map1L<String, Integer>();
        }
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        int count = registerName(this.lastName);
        this.emailAddress = this.lastName + "." + count + "@osu.edu";
    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1) + " " + this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1);
    }

    @Override
    public String emailAddress() {

        return this.emailAddress;
    }

    @Override
    public String toString() {

        return "Name: " + this.name() + ", Email Address: " + this.emailAddress;
    }

}
