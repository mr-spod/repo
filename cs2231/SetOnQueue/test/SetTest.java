import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
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
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    @Test
    public final void testNoArgumentConstructor() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        assertEquals(s, sExpected);
    }

    @Test
    public final void testEmptySize() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        assertEquals(s.size(), sExpected.size());
    }

    @Test
    public final void testNonEmptySize() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3");

        assertEquals(s.size(), sExpected.size());
    }

    @Test
    public final void testAddSetToEmpty() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3");
        Set<String> toAdd = this.createFromArgsRef("1", "2", "3");

        s.add(toAdd);

        assertEquals(s, sExpected);
    }

    @Test
    public final void testAddSingleToEmpty() {
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("1");

        s.add("1");

        assertEquals(s, sExpected);
    }

    @Test
    public final void testAddSet() {
        Set<String> s = this.createFromArgsTest("1");
        Set<String> sExpected = this.createFromArgsRef("1", "2", "3");
        Set<String> toAdd = this.createFromArgsRef("2", "3");

        s.add(toAdd);

        assertEquals(s, sExpected);
    }

    @Test
    public final void testRemoveSingleFromExisting() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        Set<String> sExpected = this.createFromArgsRef("1", "2");

        s.remove("3");

        assertEquals(s, sExpected);

    }

    @Test
    public final void testRemoveAny() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        int length = s.size();
        s.removeAny();

        assertEquals(s.size(), length - 1);
    }

    @Test
    public final void testContainsTrue() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        boolean sExpected = s.contains("2");

        assertEquals(true, sExpected);
    }

    @Test
    public final void testContainsFalse() {
        Set<String> s = this.createFromArgsTest("1", "2", "3");
        boolean sExpected = s.contains("4");

        assertFalse(sExpected);
    }

    @Test
    public final void testContainsEmpty() {
        Set<String> s = this.createFromArgsTest();
        boolean sExpected = s.contains("1");

        assertFalse(sExpected);
    }
    
    /*
     * Test cases for constructor
     */
    
    @Test
    public final void testConstructorZero() {
		Set<String> s = createFromArgsTest();
		Set<String> sExpected = createFromArgsRef();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testConstructorOne() {
		Set<String> s = createFromArgsTest("one");
		Set<String> sExpected = createFromArgsRef();
    		sExpected.add("one");
		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testConstructorMany() {
		Set<String> s = createFromArgsTest("one", "two", "three");
		Set<String> sExpected = createFromArgsRef();
    		sExpected.add("one");
    		sExpected.add("two");
    		sExpected.add("three");
		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for add
     */
    
    @Test
    public final void testAddEmpty() {
		Set<String> s = createFromArgsTest();
		Set<String> sExpected = createFromArgsRef("one");
    		s.add("one");
		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testAddSize1() {
		Set<String> s = createFromArgsTest("one");
		Set<String> sExpected = createFromArgsRef("two", "one");
    		s.add("two");
		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testAddSizeMoreThan1() {
		Set<String> s = createFromArgsTest("one", "three", "two");
		Set<String> sExpected = createFromArgsRef("one", "two", "three", "four");
    		s.add("four");
		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Test cases for remove
     */
    
    @Test
    public final void testRemoveSize1() {
		Set<String> s = createFromArgsTest("one");
		Set<String> sExpected = createFromArgsRef();
    		s.remove("one");
		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testRemoveSizeMoreThan1FirstInQ() {
		Set<String> s = createFromArgsTest("one", "two", "three");
		Set<String> sExpected = createFromArgsRef("three", "two");
		s.remove("one");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public final void testRemoveSeveralNonFirst() {
		Set<String> s = createFromArgsTest("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
		Set<String> sExpected = createFromArgsRef("two", "one", "three", "four", "five", "seven", "eight", "nine");
    		s.remove("six");
		
    		assertEquals(s, sExpected);
    		assertFalse(s.contains("six"));
    		assertTrue(s.contains("nine"));
    		assertTrue(s.contains("one"));
    		assertTrue(s.contains("five"));
    		assertTrue(s.contains("seven"));
    		assertTrue(s.contains("three"));
    		assertTrue(s.contains("two"));
    		assertTrue(s.contains("nine"));
    		assertTrue(s.contains("four"));
    		s.remove("seven");
    		s.remove("three");
    		s.remove("two");
    		assertTrue(s.contains("five"));
    		assertTrue(s.contains("one"));
    		assertTrue(s.contains("eight"));
    		assertFalse(s.contains("six"));
    		assertTrue(s.contains("nine"));
    		assertTrue(s.contains("four"));
    		assertFalse(s.contains("seven"));
    		assertFalse(s.contains("three"));
    		assertFalse(s.contains("two"));
    }
    
    /*
     * Test cases for removeAny
     */
    
    @Test
    public final void testRemoveAnySize1() {
    		Set<String> s = createFromArgsTest("one");
    		s.removeAny();
    		
    		assertEquals(s.size(), 0);
    }
    
    @Test
    public final void testRemoveAnySizeMoreThan1() {
    		Set<String> s = createFromArgsTest("one", "two", "three");
    		int size = s.size();
    		s.removeAny();
    		assertEquals(s.size(), size - 1);
    }
    
    /*
     * Test cases for contains
     */
    
    @Test
    public final void testContainsDoesSize1() {
    		Set<String> s = createFromArgsTest("one");
    		assertTrue(s.contains("one"));
    }
    
    @Test
    public final void testContainsDoesSizeMoreThan1() {
    		Set<String> s = createFromArgsTest("one", "two", "three");
    		assertTrue(s.contains("two"));
    }
    
    @Test
    public final void testContainsEmptySean() {
    		Set<String> s = createFromArgsTest();
    		assertFalse(s.contains("one"));
    }
    
    public final void testContainsDoesntSize1() {
    		Set<String> s = createFromArgsTest("one");
    		assertFalse(s.contains("three"));
    }
    
    public final void testContainsDoesntSizeMoreThan1() {
		Set<String> s = createFromArgsTest("one", "two", "three");
		assertFalse(s.contains("zero"));
    }
    
    /*
     * Test cases for size
     */
    
    public final void testSizeEmpty() {
    		Set<String> s = createFromArgsTest();
    		assertEquals(s.size(), 0);
    }
    
	public final void testSize1() {
		Set<String> s = createFromArgsTest("one");
		assertEquals(s.size(), 1);
	}

	public final void testSizeMoreThan1() {
		Set<String> s = createFromArgsTest("one", "two", "three");
		assertEquals(s.size(), 3);
	}

}
