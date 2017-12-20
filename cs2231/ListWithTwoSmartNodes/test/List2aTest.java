import components.list.List;
import components.list.List1L;

/**
 * Customized JUnit test fixture for {@code List2a}.
 */
public class List2aTest extends ListTest {

    @Override
    protected final List<String> constructorTest() {
        return new List2a<String>();
    }

    @Override
    protected final List<String> constructorRef() {
        return new List1L<String>();
    }

}
