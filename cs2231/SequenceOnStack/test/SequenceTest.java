import components.sequence.Sequence;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * JUnit test fixture for {@code Sequence<String>}'s constructor and kernel
 * methods.
 * 
 * @author Put your name here
 * 
 */
public abstract class SequenceTest {

    /**
     * Invokes the appropriate {@code Sequence} constructor for the
     * implementation under test and returns the result.
     * 
     * @return the new sequence
     * @ensures constructorTest = <>
     */
    protected abstract Sequence<String> constructorTest();

    /**
     * Invokes the appropriate {@code Sequence} constructor for the reference
     * implementation and returns the result.
     * 
     * @return the new sequence
     * @ensures constructorRef = <>
     */
    protected abstract Sequence<String> constructorRef();

    /**
     * 
     * Creates and returns a {@code Sequence<String>} of the implementation
     * under test type with the given entries.
     * 
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsTest = [entries in args]
     */
    private Sequence<String> createFromArgsTest(String... args) {
        Sequence<String> sequence = this.constructorTest();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    /**
     * 
     * Creates and returns a {@code Sequence<String>} of the reference
     * implementation type with the given entries.
     * 
     * @param args
     *            the entries for the sequence
     * @return the constructed sequence
     * @ensures createFromArgsRef = [entries in args]
     */
    private Sequence<String> createFromArgsRef(String... args) {
        Sequence<String> sequence = this.constructorRef();
        for (String s : args) {
            sequence.add(sequence.length(), s);
        }
        return sequence;
    }

    
    /*
     * Test cases for constructors
     */
    
    @Test
    public final void testNoArgumentConstructor() {
    		Sequence<String> s = this.createFromArgsTest();
    		Sequence<String> sExpected = this.createFromArgsRef();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testConstructorOne() {
    		Sequence<String> s = this.createFromArgsTest("one");
    		Sequence<String> sExpected = this.createFromArgsRef();
    		
    		sExpected.add(0, "one");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testConstructorMoreThanOne() {
    		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
    		Sequence<String> sExpected = this.createFromArgsRef();
    		
    		sExpected.add(0, "one");
    		sExpected.add(1, "two");
    		sExpected.add(2, "three");
    		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for add
     */
    
    @Test
    public final void testAddFirstPosition() {

		Sequence<String> s = this.createFromArgsTest("two", "three");
		Sequence<String> sExpected = this.createFromArgsRef("one", "two", "three");
		
		s.add(0, "one");
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testAddNonFirstNonLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three", "five");
		Sequence<String> sExpected = this.createFromArgsRef("one", "two", "three", "four", "five");
		
		s.add(3, "four");
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testAddLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two");
		Sequence<String> sExpected = this.createFromArgsRef("one", "two", "three");
		
		s.add(s.length(), "three");
		
		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for remove
     */
    
    @Test
    public final void testRemoveFirstPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		Sequence<String> sExpected = this.createFromArgsRef("two", "three");
		
		s.remove(0);
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testRemoveNonFirstNonLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three", "four");
		Sequence<String> sExpected = this.createFromArgsRef("one", "three", "four");
		
		s.remove(1);
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testRemoveLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		Sequence<String> sExpected = this.createFromArgsRef("one", "two");
		
		s.remove(s.length() - 1);
		
		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for length
     */
    
    @Test
    public final void testLengthZero() {

		Sequence<String> s = this.createFromArgsTest();
		
		assertEquals(s.length(), 0);
    }
    
    @Test
    public final void testLengthOne() {

		Sequence<String> s = this.createFromArgsTest("one");
		
		assertEquals(s.length(), 1);
    }
    
    @Test
    public final void testLengthMoreThanOne() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		
		assertEquals(s.length(), 3);
    }
    
    @Test
    public final void testLengthAfterAdd() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		s.add(0, "zero");
		
		assertEquals(s.length(), 4);
    }
    
    @Test
    public final void testLengthAfterRemove() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three", "four");
		s.remove(0);
		
		assertEquals(s.length(), 3);
    }
    
    /*
     * Test cases for entry
     */
    
    @Test
    public final void testEntryFirstPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		
		assertEquals(s.entry(0), "one");
    }
    
    @Test
    public final void testEntryLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three");
		
		assertEquals(s.entry(s.length() - 1), "three");
    }
    
    @Test
    public final void testEntryNonFirstNonLastPosition() {

		Sequence<String> s = this.createFromArgsTest("one", "two", "three", "four");
		
		assertEquals(s.entry(2), "three");
    }
}
