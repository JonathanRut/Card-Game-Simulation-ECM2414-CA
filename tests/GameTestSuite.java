import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite for testing the entire system
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CardDeckTest.class, CardGameTest.class, CardTest.class, PlayerTest.class})
public class GameTestSuite {

}
