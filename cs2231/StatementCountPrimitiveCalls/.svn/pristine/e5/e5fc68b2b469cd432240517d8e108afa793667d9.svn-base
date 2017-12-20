import components.statement.Statement;
import components.statement.StatementKernel.Condition;

/**
 * Utility class with method to count the number of calls to primitive
 * instructions (move, turnleft, turnright, infect, skip) in a given
 * {@code Statement}.
 * 
 * @author Put your name here
 * 
 */
public final class CountPrimitiveCalls {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CountPrimitiveCalls() {
    }

    /**
     * Reports the number of calls to primitive instructions (move, turnleft,
     * turnright, infect, skip) in a given {@code Statement}.
     * 
     * @param s
     *            the {@code Statement}
     * @return the number of calls to primitive instructions in {@code s}
     * @ensures <pre>
     * countOfPrimitiveCalls =
     *  [number of calls to primitive instructions in s]
     * </pre>
     */
    public static int countOfPrimitiveCalls(Statement s) {
        int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

            		for (int i = 0; i < s.lengthOfBlock(); i++) {
            			Statement stmt = s.removeFromBlock(i);
            			count += countOfPrimitiveCalls(stmt);
            			s.addToBlock(i, stmt);
            		}

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleIf(block);
                count += countOfPrimitiveCalls(block);
                s.assembleIf(c, block);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement block1 = s.newInstance();
                Statement block2 = s.newInstance();
                Condition c = s.disassembleIfElse(block1, block2);
                count += countOfPrimitiveCalls(block1) + countOfPrimitiveCalls(block2);
                s.assembleIfElse(c, block1, block2);
                

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleWhile(block);
                count += countOfPrimitiveCalls(block);
                s.assembleWhile(c, block);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String instr = s.disassembleCall();
                if (instr.equals("turnright") 
                		|| instr.equals("turnleft") 
                		|| instr.equals("move") 
                		|| instr.equals("infect") 
                		|| instr.equals("skip")) {
                			count++;
                }
                s.assembleCall(instr);

                break;
            }
            default: {
                	// this will never happen because this switch stmt 
            		// contains a case for each value defined 
            		// in the StatementKernel.Kind enum
                break;
            }
        }
        return count;
    }
    
    /**
     * Reports the number of calls to a given instruction, {@code instr},
     * in a given {@code Statement}.
     * 
     * @param s
     *            the {@code Statement}
     * @param instr
     *            the instruction name
     * @return the number of calls to {@code instr} in {@code s}
     * @ensures countOfInstructionCalls = [number of calls to instr in s]
     */
    public static int countOfInstructionCalls(Statement s, String instr) {
    		int count = 0;
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

            		for (int i = 0; i < s.lengthOfBlock(); i++) {
            			Statement stmt = s.removeFromBlock(i);
            			count += countOfInstructionCalls(stmt, instr);
            			s.addToBlock(i, stmt);
            		}

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleIf(block);
                count += countOfInstructionCalls(block, instr);
                s.assembleIf(c, block);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement block1 = s.newInstance();
                Statement block2 = s.newInstance();
                Condition c = s.disassembleIfElse(block1, block2);
                count += countOfInstructionCalls(block1, instr) + countOfInstructionCalls(block2, instr);
                s.assembleIfElse(c, block1, block2);
                

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleWhile(block);
                count += countOfInstructionCalls(block, instr);
                s.assembleWhile(c, block);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String instr1 = s.disassembleCall();
                if (instr1.equals(instr)) {
                		count++;
                }
                s.assembleCall(instr1);

                break;
            }
            default: {
                	// this will never happen because this switch stmt 
            		// contains a case for each value defined 
            		// in the StatementKernel.Kind enum
                break;
            }
        }
        return count;
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
     * @ensures <pre>
     * s = [#s refactored so that every occurrence of oldName is
     *   replaced by newName]
     * </pre>
     */
    public static void renameInstruction(Statement s, String oldName,
            String newName) {
        switch (s.kind()) {
            case BLOCK: {
                /*
                 * Add up the number of calls to primitive instructions in each
                 * nested statement in the BLOCK.
                 */

            		for (int i = 0; i < s.lengthOfBlock(); i++) {
            			Statement stmt = s.removeFromBlock(i);
            			renameInstruction(stmt, oldName, newName);
            			s.addToBlock(i, stmt);
            		}

                break;
            }
            case IF: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the IF.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleIf(block);
                renameInstruction(block, oldName, newName);
                s.assembleIf(c, block);

                break;
            }
            case IF_ELSE: {
                /*
                 * Add up the number of calls to primitive instructions in the
                 * "then" and "else" bodies of the IF_ELSE.
                 */

                Statement block1 = s.newInstance();
                Statement block2 = s.newInstance();
                Condition c = s.disassembleIfElse(block1, block2);
                renameInstruction(block1, oldName, newName);
                renameInstruction(block2, oldName, newName);
                s.assembleIfElse(c, block1, block2);
                

                break;
            }
            case WHILE: {
                /*
                 * Find the number of calls to primitive instructions in the
                 * body of the WHILE.
                 */

                Statement block = s.newInstance();
                Condition c = s.disassembleWhile(block);
                renameInstruction(block, oldName, newName);
                s.assembleWhile(c, block);

                break;
            }
            case CALL: {
                /*
                 * This is a leaf: the count can only be 1 or 0. Determine
                 * whether this is a call to a primitive instruction or not.
                 */

                String instr1 = s.disassembleCall();
                if (instr1.equals(oldName)) {
                		instr1 = newName;
                }
                s.assembleCall(instr1);

                break;
            }
            default: {
                	// this will never happen because this switch stmt 
            		// contains a case for each value defined 
            		// in the StatementKernel.Kind enum
                break;
            }
        }
    }
    
    /**
     * Refactors the given {@code Statement} so that every IF_ELSE statement
     * with a negated condition (NEXT_IS_NOT_EMPTY, NEXT_IS_NOT_ENEMY,
     * NEXT_IS_NOT_FRIEND, NEXT_IS_NOT_WALL) is replaced by an equivalent
     * IF_ELSE with the opposite condition and the "then" and "else" BLOCKs
     * switched. Every other statement is left unmodified.
     * 
     * @param s
     *            the {@code Statement}
     * @updates s
     * @ensures <pre>
     * s = [#s refactored so that IF_ELSE statements with "not"
     *   conditions are simplified so the "not" is removed]
     * </pre>
     */
    public static void simplifyIfElse(Statement s) {
        switch (s.kind()) {
	        case BLOCK: {
	            /*
	             * Add up the number of calls to primitive instructions in each
	             * nested statement in the BLOCK.
	             */
	
	        		for (int i = 0; i < s.lengthOfBlock(); i++) {
	        			Statement stmt = s.removeFromBlock(i);
	        			simplifyIfElse(stmt);
	        			s.addToBlock(i, stmt);
	        		}
	
	            break;
	        }
	        case IF: {
	            /*
	             * Find the number of calls to primitive instructions in the
	             * body of the IF.
	             */
	
	            Statement block = s.newInstance();
	            Condition c = s.disassembleIf(block);
	            simplifyIfElse(block);
	            s.assembleIf(c, block);
	
	            break;
	        }
	        case IF_ELSE: {
	            /*
	             * Add up the number of calls to primitive instructions in the
	             * "then" and "else" bodies of the IF_ELSE.
	             */
	
	            Statement block1 = s.newInstance();
	            Statement block2 = s.newInstance();
	            Condition c = s.disassembleIfElse(block1, block2);
	            simplifyIfElse(block1);
	            simplifyIfElse(block2);
	            if (c.equals(Condition.NEXT_IS_NOT_FRIEND)) {
	            		s.assembleIfElse(Condition.NEXT_IS_FRIEND, block2, block1);
	            } else if (c.equals(Condition.NEXT_IS_NOT_EMPTY)) {
	            		s.assembleIfElse(Condition.NEXT_IS_EMPTY, block2, block1);
	            } else if (c.equals(Condition.NEXT_IS_NOT_ENEMY)) {
	            		s.assembleIfElse(Condition.NEXT_IS_ENEMY, block2, block1);
	            } else if (c.equals(Condition.NEXT_IS_NOT_WALL)) {
	            		s.assembleIfElse(Condition.NEXT_IS_WALL, block2, block1);
	            } else {
		            s.assembleIfElse(c, block1, block2);
	            }
	
	            break;
	        }
	        case WHILE: {
	            /*
	             * Find the number of calls to primitive instructions in the
	             * body of the WHILE.
	             */
	
	            Statement block = s.newInstance();
	            Condition c = s.disassembleIf(block);
	            simplifyIfElse(block);
	            s.assembleWhile(c, block);
	
	            break;
	        }
	        default: {
	            	// this will happen for CALL Kind's
	            break;
	        }
        }
    }
}
