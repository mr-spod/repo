import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 * 
 * @author Sean & JS
 * 
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /*
     * Tests for add
     */
    
    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }
    
    @Test
    public void testAddToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "b");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b");
    		
    		s.add("a");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testAddToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "b", "a", "c");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b", "c", "d");
    		
    		s.add("d");
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testAddToExisting() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "b", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b", "b");
    		
    		s.add("b");
    		
    		assertEquals(s, sExpected);
    }
    
    /*
     * Tests for changeToExtractionMode
     */
    
    @Test
    public void testChangeToExtractionModeEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false);
    		
    		s.changeToExtractionMode();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testChangeToExtractionModeOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "b");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "b");
    		
    		s.changeToExtractionMode();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testChangeToExtractionModeMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "b", "a", "b");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a", "b", "b");
    		
    		s.changeToExtractionMode();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testChangeToExtractionModeNotEqualsInsertionEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true);
    		
    		s.changeToExtractionMode();
    		
    		assertNotEquals(s, sExpected);
    }
    
    @Test
    public void testChangeToExtractionModeNotEqualsInsertionOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a");
    		
    		s.changeToExtractionMode();
    		
    		assertNotEquals(s, sExpected);
    }
    
    @Test
    public void testChangeToExtractionModeNotEqualsInsertionMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a", "a", "b");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b", "a");
    		
    		s.changeToExtractionMode();
    		
    		assertNotEquals(s, sExpected);
    }
    
    /*
     * Tests for removeFirst
     */
    
    @Test
    public void testRemoveFirstToEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "s");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false);
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "s", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "s");
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "s", "b", "x", "o");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "o", "s", "x");
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstToAllSame() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "s", "s", "s", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "s", "s", "s");
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstMultipleLeast() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "c", "a", "a", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a", "a", "c");
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstAllLeast() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "a", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a", "a");
    		
    		s.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstFromEqualMachines() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "l", "s", "v", "a");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "l", "s", "v", "a");
    		
    		s.removeFirst();
    		sExpected.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstFromMachinesEqualAfterRemoval() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "b", "l", "s", "v");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "l", "s", "v", "a");
    		
    		s.removeFirst();
    		sExpected.removeFirst();
    		
    		assertEquals(s, sExpected);
    }
    
    @Test
    public void testRemoveFirstEqualMachinesReturnSame() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "v", "s", "v", "m");
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "v", "s", "v", "m");
    		
    		String sFirst = s.removeFirst();
    		String sFirstExpected = sExpected.removeFirst();
    		
    		assertEquals(sFirst, sFirstExpected);
    }
    
    /*
     * Tests for isInInsertionMode
     */
    
    // Insertion mode after constructor
    
    @Test
    public void testInsertionModeAfterConstructor() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		
    		assertTrue(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterConstructorRef() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b");
    		
    		assertEquals(s.isInInsertionMode(), sExpected.isInInsertionMode());
    }
    
    // Insertion mode after adding
    
    @Test
    public void testInsertionModeAfterAddingToZero() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		
    		s.add("a");
    		
    		assertTrue(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterAddingToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a");
    		
    		s.add("a");
    		
    		assertTrue(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterAddingToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a", "s", "m");
    		
    		s.add("a");
    		
    		assertTrue(s.isInInsertionMode());
    }
    
    // Insertion mode after change to extraction mode
    
    @Test
    public void testInsertionModeAfterChangeToExtractionModeRef() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false);
    		
    		s.changeToExtractionMode();
    		
    		assertEquals(s.isInInsertionMode(), sExpected.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterChangeToExtractionModeEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		
    		s.changeToExtractionMode();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterChangeToExtractionModeOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a");
    		
    		s.changeToExtractionMode();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterChangeToExtractionModeMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a", "b", "c", "d");
    		
    		s.changeToExtractionMode();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    // Insertion mode after removing
    
    @Test
    public void testInsertionModeAfterRemovingToEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a");
    		
    		s.removeFirst();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterRemovingToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "b");
    		
    		s.removeFirst();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    @Test
    public void testInsertionModeAfterRemovingToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "b", "c", "d");
    		
    		s.removeFirst();
    		
    		assertFalse(s.isInInsertionMode());
    }
    
    /*
     * Tests for order
     */
    
    @Test
    public void testOrder() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		
    		assertEquals(s.order(), ORDER);
    	}
    
    @Test
    public void testOrderRef() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true);
    		
    		assertEquals(s.order(), sExpected.order());
    	}
    
    /*
     * Tests for size
     */

    @Test
    public void testSizeAfterConstructor() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true);
    		
    		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterRemovingToEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false);
		
		s.removeFirst();
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterRemovingToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "b");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a");
		
		s.removeFirst();
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterRemovingToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "b", "c", "d");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a", "b", "c");
		
		s.removeFirst();
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterAddingToEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a");
		
		s.add("a");
		
		assertEquals(s.size(), sExpected.size());
    }

    @Test
    public void testSizeAfterAddingToOne() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b");
		
		s.add("b");
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterAddingToMany() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a", "b", "c");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, true, "a", "b", "c", "d");
		
		s.add("z");
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterChangeEmptyToExtractionMode() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false);
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false);
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterChangeOneToExtractionMode() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a");
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeAfterChangeManyToExtractionMode() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, false, "a", "b", "c");
		SortingMachine<String> sExpected = this.createFromArgsRef(ORDER, false, "a", "b", "c");
		
		assertEquals(s.size(), sExpected.size());
    }
    
    @Test
    public void testSizeUnchangedAfterChangeToExtractionModeEmpty() {
    		SortingMachine<String> s = this.createFromArgsTest(ORDER, true);
    		
    		int insertionSize = s.size(); 
    		
    		s.changeToExtractionMode();
    		
    		int extractionSize = s.size();
		
		assertEquals(insertionSize, extractionSize);
    }
    
    @Test
    public void testSizeUnchangedAfterChangeToExtractionModeOne() {
		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a");
		
    		int insertionSize = s.size(); 
		
		s.changeToExtractionMode();
		
		int extractionSize = s.size();
	
		assertEquals(insertionSize, extractionSize);
    }
    
    @Test
    public void testSizeUnchangedAfterChangeToExtractionModeMany() {
		SortingMachine<String> s = this.createFromArgsTest(ORDER, true, "a", "b", "c");
		
    		int insertionSize = s.size(); 
		
		s.changeToExtractionMode();
		
		int extractionSize = s.size();
	
		assertEquals(insertionSize, extractionSize);
    }

}
