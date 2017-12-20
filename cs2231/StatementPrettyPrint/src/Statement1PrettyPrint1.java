import components.map.Map;
import components.program.Program;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code prettyPrint} for
 * {@code Statement}.
 */
public final class Statement1PrettyPrint1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constructs into the given {@code Statement} the BLOCK statement read from
     * the given input file.
     *
     * @param fileName
     *            the name of the file containing 0 or more statements
     * @param s
     *            the constructed BLOCK statement
     * @replaces s
     * @requires <pre>
     * [fileName is the name of a file containing 0 or more valid BL statements]
     * </pre>
     * @ensures s = [BLOCK statement from file fileName]
     */
    private static void loadStatement(String fileName, Statement s) {
        SimpleReader in = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(in);
        s.parseBlock(tokens);
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
     * Refactors the given {@code Statement} by renaming every occurrence of
     * instruction {@code oldName} to {@code newName}. Every other statement is
     * left unmodified.
     * 
     * @param s
     *            the {@code Statement}
     * @param oldName
     *            the name of the instruction to be renamed
     * @param newName
     *            the new name of the renamed instruction
     * @updates s
     * @requires [newName is a valid IDENTIFIER]
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of instruction oldName
     *   is replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
    		switch (s.kind()) {
    			case BLOCK: {
    				
    				for (int i = 0; i < s.lengthOfBlock(); i++) {
    					Statement child = s.removeFromBlock(i);
    					renameInstruction(child, oldName, newName);
    					s.addToBlock(i, child);
    				}
    				
    				break;
    			}
    			case IF: {
    				
    				Statement block = s.newInstance();
    				Condition c = s.disassembleIf(block);
    				renameInstruction(block, oldName, newName);
    				s.assembleIf(c, block);
    				
    				break;
    			}
    			case IF_ELSE: {
    				
    				Statement block1 = s.newInstance();
    				Statement block2 = s.newInstance();
    				Condition c = s.disassembleIfElse(block1, block2);
    				renameInstruction(block1, oldName, newName);
    				renameInstruction(block2, oldName, newName);
    				s.assembleIfElse(c, block1, block2);
    				
    				break;
    			}
    			case WHILE: {
    				
    				Statement block = s.newInstance();
    				Condition c = s.disassembleWhile(block);
    				renameInstruction(block, oldName, newName);
    				s.assembleWhile(c, block);
    				
    				break;
    			}
    			case CALL: {
    				
    				String inst = s.disassembleCall();
    				if (inst.equals(oldName)) {
    					inst = newName;
    				}
    				s.assembleCall(inst);
    				
    				break;
    			}
    			default: {
    				/*
    				 * Ain't gonna happen
    				 */
    				
    				break;
    			}
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
    			Statement instrStmt = oldPair.value();
    			renameInstruction(instrStmt, oldName, newName);
    			pContext.add(newName, instrStmt);
    		}
    		p.replaceContext(pContext);
    		
    		Statement body = p.replaceBody(p.newBody());
    		renameInstruction(body, newName, oldName);
    		p.replaceBody(body);
    }

    /**
     * Converts c into the corresponding BL condition.
     *
     * @param c
     *            the Condition to convert
     * @return the BL condition corresponding to c
     * @ensures toStringCondition = [BL condition corresponding to c]
     */
    private static String toStringCondition(Condition c) {
        String result;
        switch (c) {
            case NEXT_IS_EMPTY: {
                result = "next-is-empty";
                break;
            }
            case NEXT_IS_NOT_EMPTY: {
                result = "next-is-not-empty";
                break;
            }
            case NEXT_IS_ENEMY: {
                result = "next-is-enemy";
                break;
            }
            case NEXT_IS_NOT_ENEMY: {
                result = "next-is-not-enemy";
                break;
            }
            case NEXT_IS_FRIEND: {
                result = "next-is-friend";
                break;
            }
            case NEXT_IS_NOT_FRIEND: {
                result = "next-is-not-friend";
                break;
            }
            case NEXT_IS_WALL: {
                result = "next-is-wall";
                break;
            }
            case NEXT_IS_NOT_WALL: {
                result = "next-is-not-wall";
                break;
            }
            case RANDOM: {
                result = "random";
                break;
            }
            case TRUE: {
                result = "true";
                break;
            }
            default: {
                // this will never happen...
                result = "";
                break;
            }
        }
        return result;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1PrettyPrint1() {
        super();
    }

    /*
     * Secondary methods ------------------------------------------------------
     */

    @Override
    public void prettyPrint(SimpleWriter out, int offset) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert offset >= 0 : "Violation of: 0 <= offset";

        switch (this.kind()) {
            case BLOCK: {

                for (int i = 0; i < this.lengthOfBlock(); i++) {
                		Statement s = this.removeFromBlock(i);
                		s.prettyPrint(out, offset);
                		this.addToBlock(i, s);
                }

                break;
            }
            case IF: {

            	   	Statement block = this.newInstance();
            	   	Condition c = this.disassembleIf(block);
            	   	String condition = toStringCondition(c);
            	   	printSpaces(out, offset);
            	   	out.println("IF " + condition + " THEN");
            	   	block.prettyPrint(out, offset + Program.INDENT_SIZE);
            	   	printSpaces(out, offset);
            	   	out.println("END IF");
            	   	this.assembleIf(c, block);

            	   	break;
            }
            case IF_ELSE: {

	            	Statement block1 = this.newInstance();
	            	Statement block2 = this.newInstance();
	        	   	Condition c = this.disassembleIfElse(block1, block2);
	        	   	String condition = toStringCondition(c);
	        	   	printSpaces(out, offset);
	        	   	out.println("IF " + condition + " THEN");
	        	   	block1.prettyPrint(out, offset + Program.INDENT_SIZE);
	        	   	printSpaces(out, offset);
	        	   	out.println("ELSE");
	        	   	block2.prettyPrint(out, offset + Program.INDENT_SIZE);
	        	   	printSpaces(out, offset);
	        	   	out.println("END IF");
	        	   	this.assembleIfElse(c, block1, block2);

                break;
            }
            case WHILE: {

	            	Statement block = this.newInstance();
	        	   	Condition c = this.disassembleWhile(block);
	        	   	String condition = toStringCondition(c);
	        	   	printSpaces(out, offset);
	        	   	out.println("WHILE " + condition + " DO");
	        	   	block.prettyPrint(out, offset + Program.INDENT_SIZE);
	        	   	printSpaces(out, offset);
	        	   	out.println("END WHILE");
	        	   	this.assembleWhile(c, block);

                break;
            }
            case CALL: {

                String instr = this.disassembleCall();
                printSpaces(out, offset);
                out.println(instr);
                this.assembleCall(instr);

                break;
            }
            default: {
                // this will never happen...
                break;
            }
        }
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
        out.print("Enter valid BL statement file name: ");
        String fileName = in.nextLine();
        /*
         * Generate expected output in file "data/expected-output.txt"
         */
        out.println("*** Generating expected output ***");
        Statement s1 = new Statement1();
        loadStatement(fileName, s1);
        SimpleWriter ppOut = new SimpleWriter1L("data/expected-output.txt");
        s1.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Generate actual output in file "data/actual-output.txt"
         */
        out.println("*** Generating actual output ***");
        Statement s2 = new Statement1PrettyPrint1();
        loadStatement(fileName, s2);
        ppOut = new SimpleWriter1L("data/actual-output.txt");
        s2.prettyPrint(ppOut, 2);
        ppOut.close();
        /*
         * Check that prettyPrint restored the value of the statement
         */
        if (s2.equals(s1)) {
            out.println("Statement value restored correctly.");
        } else {
            out.println("Error: statement value was not restored.");
        }

        in.close();
        out.close();
    }

}
