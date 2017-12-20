import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.set.Set1L;
import components.set.Set;

/**
 * Simple program to exercise EmailAccount functionality.
 */
public final class EmailAccountMain {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private EmailAccountMain() {
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        
        Set<EmailAccount> accounts = new Set1L<EmailAccount>();
        out.print("Give me a full name: ");
        String nextLine = in.nextLine();
        while (!nextLine.equals("")) {

        		int spaceIndex = nextLine.indexOf(" ");
        		String firstName = nextLine.substring(0, spaceIndex);
        		String lastName = nextLine.substring(spaceIndex + 1);
        		EmailAccount myAccount = new EmailAccount1(firstName, lastName);
        		/*
        		 * Should print: Brutus Buckeye
        		 */
        		out.println(myAccount.name());
        		/*
        		 * Should print: buckeye.1@osu.edu
        		 */
        		out.println(myAccount.emailAddress());
        		/*
        		 * Should print: Name: Brutus Buckeye, Email: buckeye.1@osu.edu
        		 */
        		out.println(myAccount);
        		
        		accounts.add(myAccount);
        		
        		out.println("All emails entered so far:");
        		for (EmailAccount e: accounts) {
        			out.println(e);
        		}
        		

        		out.print("Give me another full name: ");
        		nextLine = in.nextLine();
        		
        }
        in.close();
        out.close();
    }

}
