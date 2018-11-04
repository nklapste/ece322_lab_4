import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUNIT4 tests for Bisect.
 *
 * @see Bisect
 */
public class BisectTest {

    private Bisect bisect;

    @Before
    public void setUp() throws Exception {
        // same definition as defined in main
       bisect =  new Bisect(
               new Bisect.polynomial() {
                   //Define your function here:
                   public double eval(double value) {
                       return value - 1;
                   }
               }
       );
    }

    @After
    public void tearDown() throws Exception {
        bisect = null;
    }

    @Test
    public void test_constructor1(){
        bisect =  new Bisect(
                0.5,
                new Bisect.polynomial() {
                    //Define your function here:
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(0.5, bisect.getTolerance(), 10);
        assertEquals(50 , bisect.getMaxIterations(), 10);

    }

    @Test
    public void test_constructor2(){
        bisect =  new Bisect(
                100,
                100,
                new Bisect.polynomial() {
                    //Define your function here:
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(100 , bisect.getTolerance(), 10);
        assertEquals(100 , bisect.getMaxIterations(), 10);
    }

    @Test
    public void test_constructor3(){
        bisect =  new Bisect(
                10,
                new Bisect.polynomial() {
                    //Define your function here:
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(0.000001 , bisect.getTolerance(), 10);
        assertEquals(10 , bisect.getMaxIterations(), 10);
    }

    @Test
    public void getTolerance() {
        assertEquals(0.000001, bisect.getTolerance(), 10);
    }

    @Test
    public void setTolerance() {
        bisect.setTolerance(100);
        assertEquals(100, bisect.getTolerance(),10);
    }

    @Test
    public void getMaxIterations() {
        assertEquals(50, bisect.getMaxIterations(), 10);
    }

    @Test
    public void setMaxIterations() {
        bisect.setMaxIterations(100);
        assertEquals(100, bisect.getMaxIterations(), 10);
    }

    @Test(expected = Bisect.RootNotFound.class)
    public void test_RootNotFoundDuringIter() throws Bisect.RootNotFound {
        // no loops
        // throws immediately
        bisect.run(1, 2);
    }

    @Test(expected = Bisect.RootNotFound.class)
    public void test_RootNotFoundAfterIter() throws Bisect.RootNotFound {
        // too many loops
        // throws after do loop
        bisect.run(20, -1000000000);
    }

    @Test
    public void test_IterX1X1Mid() throws Bisect.RootNotFound {
        // two loops
        // x1 = mid
        // x1 = mid
        bisect.run(-2, 2);
    }

    @Test
    public void test_IterX2X1Mid() throws Bisect.RootNotFound {
        // two loop
        // x2 = mid
        // x1 = mid
        bisect.run(2, -2);
    }

    @Test
    public void test_IterX1Mid() throws Bisect.RootNotFound {
        // one loop
        // x1 = mid
        bisect.run(-2, 4);
    }

    @Test
    public void test_IterX1X2Mid() throws Bisect.RootNotFound {
        // two loop
        // x1 = mid
        // x2 = mid
        bisect.run(-2, 1);
    }
}