import components.set.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 * 
 * @author Sean & JS
 * 
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     * 
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     * 
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    
    /*
     * Test cases for constructor
     */
    
    @Test
    public void testNoArgumentConstructor() {
    		Set<String> s = createFromArgsTest();
    		Set<String> sExpected = createFromArgsRef();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testOneArgumentConstructor() {
    		Set<String> s = createFromArgsTest("Sean");
    		Set<String> sExpected = createFromArgsRef("Sean");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testManyArgumentConstructor() {
    		Set<String> s = createFromArgsTest("Sean", "JS");
    		Set<String> sExpected = createFromArgsRef("JS", "Sean");
    		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for add
     */
    
    @Test
    public void testAddToEmptySet() {
    		Set<String> s = createFromArgsTest();
    		Set<String> sExpected = createFromArgsRef("JS");
    		
    		s.add("JS");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testAddToSetOfOne() {
    		Set<String> s = createFromArgsTest("Sean");
    		Set<String> sExpected = createFromArgsRef("Sean", "JS");
    		
    		s.add("JS");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testAddToSetOfMany() {
    		Set<String> s = createFromArgsTest("Sean", "JS", "Phil");
    		Set<String> sExpected = createFromArgsRef("Phil", "JS", "Sean", "Jon");
    		
    		s.add("Jon");
    		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for remove
     */
    
    @Test
    public void testRemoveToEmptySet() {
    		Set<String> s = createFromArgsTest("Sean");
    		Set<String> sExpected = createFromArgsRef();
    		
    		s.remove("Sean");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveToSetOfOne() {
		Set<String> s = createFromArgsTest("Sean", "JS");
		Set<String> sExpected = createFromArgsRef("JS");
		
		s.remove("Sean");
		
		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveToSetOfMany() {
		Set<String> s = createFromArgsTest("Sean", "JS", "Phil", "Jon");
		Set<String> sExpected = createFromArgsRef("JS", "Jon", "Phil");
		
		s.remove("Sean");
		
		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for removeAny
     */
    
    @Test
    public void testRemoveAnyToEmptySet() {
    		Set<String> s = createFromArgsTest("Sean");
    		Set<String> sExpected = createFromArgsRef();
    		
    		s.removeAny();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveAnyToSetOfOne() {
		Set<String> s = createFromArgsTest("Sean", "JS");
		Set<String> sExpected = createFromArgsRef("JS");
		
		s.removeAny();
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testRemoveAnyToSetOfMany() {
		Set<String> s = createFromArgsTest("Sean", "JS", "Phil", "Jon");
		Set<String> sExpected = createFromArgsRef("JS", "Jon", "Phil");
		
		s.removeAny();
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testRemoveAnyWasInSet() {
    		Set<String> s = createFromArgsTest("Sean", "JS", "Jon");
    		Set<String> sCopy = createFromArgsRef("Sean", "JS", "Jon");
    		
    		String removed = s.removeAny();
    		
    		assertTrue(sCopy.contains(removed));
    		
    }
    
    /*
     * Test cases for contains
     */
    
    @Test
    public void testContainsEmptySet() {
		Set<String> s = createFromArgsTest();
		
		assertFalse(s.contains("JS"));
    }
    
    @Test
    public void testContainsDoesSetOfOne() {
		Set<String> s = createFromArgsTest("JS");
		
		assertTrue(s.contains("JS"));
    }
    
    @Test
    public void testContainsDoesNotSetOfOne() {
		Set<String> s = createFromArgsTest("Sean");
		
		assertFalse(s.contains("JS"));
    }
    
    @Test
    public void testContainsDoesSetOfMany() {
		Set<String> s = createFromArgsTest("Sean", "JS", "Phil", "Jon", "Bill Nye", "CC Sabathia");
		
		assertTrue(s.contains("JS"));
    }
    
    @Test
    public void testContainsDoesNotSetOfMany() {
    		Set<String> s = createFromArgsTest("Sean", "JS", "Phil", "Jon", "Bill Nye", "CC Sabathia");
		
		assertFalse(s.contains("Santa Clause"));
    }
    
    /*
     * Test cases for size
     */
    
    @Test
    public void testSizeEmptySet() {
    		Set<String> s = createFromArgsTest();
    		
    		assertEquals(s.size(), 0);
    }
    
    @Test
    public void testSizeSetOfOne() {
    		Set<String> s = createFromArgsTest("JS");
    		
    		assertEquals(s.size(), 1);
    }
    
    @Test
    public void testSizeSetOfMany() {
    		Set<String> s = createFromArgsTest("Sean", "JS", "Phil", "Jon", "Bill Nye", "CC Sabathia");
    		
    		assertEquals(s.size(), 6);
    }

}
