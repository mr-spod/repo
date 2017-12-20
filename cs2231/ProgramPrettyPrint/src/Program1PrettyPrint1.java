import components.program.Program;
import components.program.Program1;
import components.map.Map;
import components.statement.Statement;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Program}.
 */
public final class Program1PrettyPrint1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Program} the program read from the given
     * input file.
     *
     * @param fileName
     *            the name of the file containing the program
     * @param p
     *            the constructed program
     * @replaces p
     * @requires [fileName is the name of a file containing a valid BL program]
     * @ensures p = [program from file fileName]
     */
    private static void loadProgram(String fileName, Program p) {
        SimpleReader in = new SimpleReader1L(fileName);
        p.parse(in);
        in.close();
    }

    /**
     * Prints the given number of spaces to the given output stream.
     *
     * @param out
     *            the output stream
     * @param numSpaces
     *            the number of spaces to print
     * @updates out.content
     * @requires out.is_open and spaces >= 0
     * @ensures out.content = #out.content * [numSpaces spaces]
     */
    private static void printSpaces(SimpleWriter out, int numSpaces) {
        for (int i = 0; i < numSpaces; i++) {
            out.print(' ');
        }
    }
    
    /**
     * Refactors the given {@code Program} by renaming instruction
     * {@code oldName}, and every call to it, to {@code newName}. Everything
     * else is left unmodified.
     * 
     * @param p
     *            the {@code Program}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates p
     * @requires <pre>
     * oldName is in DOMAIN(p.context)  and
     * [newName is a valid IDENTIFIER]  and
     * newName is not in DOMAIN(p.context)
     * </pre>
     * @ensures <pre>
     * p = [#p refactored so that instruction oldName and every call
     *   to it are replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Program p, String oldName,
            String newName) {
    		
    		Map<String, Statement> pContext = p.replaceContext(p.newContext());
    		if (pContext.hasKey(oldName)) {
    			Map.Pair<String, Statement> oldPair = pContext.remove(oldName);
    			pContext.add(newName, oldPair.value());
    		}
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    @Override
    public void prettyPrint(SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        // Print "PROGRAM..."
        out.println("PROGRAM " + this.name() + " IS");
        out.println();
        
        // Assignment paragraph
         Map<String, Statement> context = this.replaceContext(this.newContext());
         for (Map.Pair<String, Statement> pair : context) {
        	 	Statement s = pair.value();
        	 	String name = pair.key();
        	 	printSpaces(out, INDENT_SIZE);
        	 	
        	 	// Print "INSTRUCTION" + statements
        	 	out.println("INSTRUCTION " + name + " IS");
        	 	s.prettyPrint(out, 2 * INDENT_SIZE);
        	 	printSpaces(out, INDENT_SIZE);
        	 	out.println("END " + name);
        	 	out.println();
         }
         this.replaceContext(context);
         
         // Print "BEGIN..."
         out.println("BEGIN");
         
         // Assignment line
         Statement body = this.replaceBody(this.newBody());
         
         // Print statement in "BEGIN"
         body.prettyPrint(out, INDENT_SIZE);
         out.println("END " + this.name());
         this.replaceBody(body);

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("");
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Program p1 = new Program1();
        loadProgram(fileName, p1);
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        p1.prettyPrint(ppOut);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Program p2 = new Program1PrettyPrint1();
        loadProgram(fileName, p2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        p2.prettyPrint(ppOut);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the program
         */
        if (p2.equals(p1)) {
            out.println("Program value restored correctly.");
        } else {
            out.println("Error: program value was not restored.");
        }

        in.close();
        out.close();
    }

}
