import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value, hasKey, and size

    @Test
    public final void testNoArgumentConstructor() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();

        assertEquals(s, sExpected);
    }

    @Test
    public final void testEmptySize() {
        Map<String, String> s = this.createFromArgsTest();
        Map<String, String> sExpected = this.createFromArgsRef();

        assertEquals(s.size(), sExpected.size());
    }

    @Test
    public final void testNonEmptySize() {
        Map<String, String> s = this.createFromArgsTest("JS", "Sean");
        Map<String, String> sExpected = this.createFromArgsRef("JS", "Sean");

        assertEquals(s.size(), sExpected.size());
    }

    @Test
    public final void testAddPairToEmpty() {
        Map<String, String> s = this.createFromArgsTest();
        s.add("JS", "1");
        Map<String, String> sExpected = this.createFromArgsRef("JS", "1");

        assertEquals(s, sExpected);

    }

    @Test
    public final void testAddPairToExisting() {
        Map<String, String> s = this.createFromArgsTest("JS", "1");
        s.add("Sean", "2");
        Map<String, String> sExpected = this.createFromArgsRef("JS", "1",
                "Sean", "2");

        assertEquals(s, sExpected);
    }

    @Test
    public final void removeFromExisting() {
        Map<String, String> s = this.createFromArgsTest("JS", "1", "Sean", "2");
        s.remove("Sean");
        Map<String, String> sExpected = this.createFromArgsRef("JS", "1");

        assertEquals(s, sExpected);
    }
    
    @Test
    public final void removeFromExistingToEmpty() {
        Map<String, String> s = this.createFromArgsTest("JS", "1");
        s.remove("JS");
        Map<String, String> sExpected = this.createFromArgsRef();

        assertEquals(s, sExpected);
    }
    
    @Test
    public final void removeFromExistingPair() {
        Map<String, String> s = this.createFromArgsTest("JS", "1", "Sean", "2");
        Map.Pair<String, String> pair = s.remove("Sean");

        assertEquals(pair.value(), "2");
    }

    @Test
    public final void removeAnyFromExisting() {
        Map<String, String> s = this.createFromArgsTest("JS", "1", "Sean", "2");
        s.removeAny();
        Map<String, String> sExpected = this.createFromArgsRef("JS", "1");

        assertEquals(s.size(), sExpected.size());
    }

    @Test
    public final void valueTest() {
        Map<String, String> s = this.createFromArgsTest("JS", "1");
        String sExpected = s.value("JS");

        assertEquals(sExpected, "1");
    }

    @Test
    public final void hasKeyTest() {
        Map<String, String> s = this.createFromArgsTest("JS", "1", "Sean", "2");
        boolean sExpected = s.hasKey("Sean");

        assertTrue(sExpected);
    }
    
    @Test
    public final void doesntHaveKeyTest() {
        Map<String, String> s = this.createFromArgsTest("JS", "1", "Sean", "2");
        boolean sExpected = s.hasKey("Paul");

        assertFalse(sExpected);
    }
    
    @Test
    public final void doesntHaveKeyEmptyTest() {
        Map<String, String> s = this.createFromArgsTest();
        boolean sExpected = s.hasKey("Paul");

        assertFalse(sExpected);
    }

}
