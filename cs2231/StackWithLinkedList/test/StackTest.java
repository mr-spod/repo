import components.set.Set;
import components.stack.Stack;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * JUnit test fixture for {@code Stack<String>}'s constructor and kernel
 * methods.
 * 
 * @author Put your name here
 * 
 */
public abstract class StackTest {

    /**
     * Invokes the appropriate {@code Stack} constructor for the implementation
     * under test and returns the result.
     * 
     * @return the new stack
     * @ensures constructorTest = <>
     */
    protected abstract Stack<String> constructorTest();

    /**
     * Invokes the appropriate {@code Stack} constructor for the reference
     * implementation and returns the result.
     * 
     * @return the new stack
     * @ensures constructorRef = <>
     */
    protected abstract Stack<String> constructorRef();

    /**
     * 
     * Creates and returns a {@code Stack<String>} of the implementation under
     * test type with the given entries.
     * 
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsTest = [entries in args]
     */
    private Stack<String> createFromArgsTest(String... args) {
        Stack<String> stack = this.constructorTest();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    /**
     * 
     * Creates and returns a {@code Stack<String>} of the reference
     * implementation type with the given entries.
     * 
     * @param args
     *            the entries for the stack
     * @return the constructed stack
     * @ensures createFromArgsRef = [entries in args]
     */
    private Stack<String> createFromArgsRef(String... args) {
        Stack<String> stack = this.constructorRef();
        for (String s : args) {
            stack.push(s);
        }
        stack.flip();
        return stack;
    }

    // TODO - add test cases for constructor, push, pop, and length
    
    /*
     * Tests for constructors
     */
    
    @Test
    public void testNoArgumentConstructor() {
    		Stack<String> s = this.createFromArgsTest();
    		Stack<String> sExpected = this.createFromArgsRef();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testSingleArgumentConstructor() {
    		Stack<String> s = this.createFromArgsTest("Sean");
    		Stack<String> sExpected = this.createFromArgsRef();
    		sExpected.push("Sean");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testManyArgumentConstructor() {
    		Stack<String> s = this.createFromArgsTest("Sean", "JS", "Phil");
    		Stack<String> sExpected = this.createFromArgsRef();
    		sExpected.push("Phil");
    		sExpected.push("JS");
    		sExpected.push("Sean");
    		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Tests for push
     */
    
    @Test
    public void testPushOntoEmpty() {
		Stack<String> s = this.createFromArgsTest();
		Stack<String> sExpected = this.createFromArgsRef("Sean");
		s.push("Sean");
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testPushOntoOneElement() {
		Stack<String> s = this.createFromArgsTest("JS");
		Stack<String> sExpected = this.createFromArgsRef("Sean", "JS");
		s.push("Sean");
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testPushOntoManyElements() {
		Stack<String> s = this.createFromArgsTest("JS", "Phil", "Jon");
		Stack<String> sExpected = this.createFromArgsRef("Sean", "JS", "Phil", "Jon");
		s.push("Sean");
		
		assertEquals(s, sExpected);
    }
    
    /*
     * Tests for pop
     */
    
    @Test
    public void testPopToEmpty() {
		Stack<String> s = this.createFromArgsTest("Sean");
		Stack<String> sExpected = this.createFromArgsRef();
		s.pop();
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testPopToOneElement() {
		Stack<String> s = this.createFromArgsTest("Sean", "JS");
		Stack<String> sExpected = this.createFromArgsRef("JS");
		s.pop();
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testPopToManyElements() {
		Stack<String> s = this.createFromArgsTest("Sean", "JS", "Phil", "Jon");
		Stack<String> sExpected = this.createFromArgsRef("JS", "Phil", "Jon");
		s.pop();
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testPopValue() {
		Stack<String> s = this.createFromArgsTest("Sean", "sEAN");
		String popped = s.pop();
		
		assertEquals(popped, "Sean");
    }
    
    /*
     * Tests for length
     */
    
    @Test
    public void testLengthNoArgConstructor() {
		Stack<String> s = this.createFromArgsTest();
		
		assertEquals(s.length(), 0);
    }
    
    @Test
    public void testLengthZeroFromPop() {
		Stack<String> s = this.createFromArgsTest("Sean");
		s.pop();
		
		assertEquals(s.length(), 0);
    }
    
    @Test
    public void testLengthOneArgConstructor() {
		Stack<String> s = this.createFromArgsTest("Sean");
		
		assertEquals(s.length(), 1);
    }
    
    /* Don't need to test length 1 from pushing, because that's what createFromArgsTest(String.. args) does
     */
    
    @Test
    public void testLengthOneFromPop() {
		Stack<String> s = this.createFromArgsTest("Sean", "JS");
		s.pop();
		
		assertEquals(s.length(), 1);
    }
    
    @Test
    public void testLengthManyArgConstructor() {
    		Stack<String> s = this.createFromArgsTest("Sean", "JS", "Phil", "Jon");
    		
    		assertEquals(s.length(), 4);
    }
    
    @Test
    public void testLengthManyFromPop() {
		Stack<String> s = this.createFromArgsTest("Sean", "JS", "Phil", "Jon", "Jack Bauer");
		s.pop();
		
		assertEquals(s.length(), 4);
    }
    
    /*
     * Stress test
     */
    
    @Test
    public void stressTest() {
    		Stack<String> s = createFromArgsTest();
    		Stack<String> sExpected1 = createFromArgsRef("Sean");
    		
    		s.push("Sean");
    		assertEquals(s, sExpected1);
    		
    		Stack<String> sExpected2 = createFromArgsRef("JS", "Phil", "Jon");
    		
    		String sean = s.pop();
    		s.push("Jon");
    		s.push("Phil");
    		s.push("JS");
    		assertEquals(s, sExpected2);
    		
    		Stack<String> sExpected3 = createFromArgsRef("Sean", "Jon");
    		
    		s.pop();
    		s.pop();
    		s.push(sean);
    		assertEquals(s, sExpected3);
    		
    		Stack<String> sExpected4 = createFromArgsRef();
    		
    		s.pop();
    		s.pop();
    		assertEquals(s, sExpected4);
    }

}
