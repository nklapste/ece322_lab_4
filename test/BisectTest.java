import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUNIT4 tests for Bisect.
 *
 * @see Bisect
 */
public class BisectTest {

    private Bisect bisect;

    /**
     * Setup Biesect with its default {@code tol} and {@code maxIter} values (0.000001 and 50 respectfully) and
     * define the polynomial to solve to be f(x)=x-1.
     *
     * Thus, running {@code Biesect.run(x1, x2)} should solve the equation f(x)=0 which should result in x=1.
     */
    @Before
    public void setUp() throws Exception {
        // same definition as defined in main
        bisect = new Bisect(
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

    /* -- below is tests on {@code Bisect}'s constructors, getters, and setters -- */

    /**
     * Test Biesect's overloading constructor with a {@code tol} specified.
     */
    @Test
    public void test_constructor1() {
        bisect = new Bisect(
                0.5,
                new Bisect.polynomial() {
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(0.5, bisect.getTolerance(), 10);
        assertEquals(50, bisect.getMaxIterations(), 10);

    }

    /**
     * Test Biesect's overloading constructor with a {@code tol} and {@code maxIter} specified.
     */
    @Test
    public void test_constructor2() {
        bisect = new Bisect(
                100,
                100,
                new Bisect.polynomial() {
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(100, bisect.getTolerance(), 10);
        assertEquals(100, bisect.getMaxIterations(), 10);
    }

    /**
     * Test Biesect's overloading constructor with a {@code maxIter} specified.
     */
    @Test
    public void test_constructor3() {
        bisect = new Bisect(
                10,
                new Bisect.polynomial() {
                    public double eval(double value) {
                        return value - 1;
                    }
                }
        );
        assertEquals(0.000001, bisect.getTolerance(), 10);
        assertEquals(10, bisect.getMaxIterations(), 10);
    }

    @Test
    public void getTolerance() {
        assertEquals(0.000001, bisect.getTolerance(), 10);
    }

    @Test
    public void setTolerance() {
        bisect.setTolerance(100);
        assertEquals(100, bisect.getTolerance(), 10);
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

    /* -- constructors, getters, and setters tests are now done -- */

    /* -- below is tests actually testing the {@code Bisect.run(x1, x2)} functionality -- */

    @Test(expected = Bisect.RootNotFound.class)
    public void test_RootNotFoundDuringIter() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // no loops
        // throws RootNotFound immediately
        bisect.run(1, 2);
    }

    @Test(expected = Bisect.MaxIterationsPassed.class)
    public void test_MaxIterationsPassed1() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // too many loops
        // throws MaxIterationsPassed after do loop
        bisect.run(20, -1000000000);
    }

    @Test(expected = Bisect.MaxIterationsPassed.class)
    public void test_MaxIterationsPassed2() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // too many loops
        // throws MaxIterationsPassed after do loop
        // set max iterations to be 1 this "ensures" that a MaxIterationsPassed exception should be thrown
        bisect.setMaxIterations(1);
        bisect.run(-2, 2);
    }

    @Test
    public void test_IterX1X1Mid() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x1 = mid
        // 2. x1 = mid
        double result = bisect.run(-2, 2);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX2X1Mid() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x2 = mid
        // 2. x1 = mid
        double result = bisect.run(2, -2);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX2X1MidHighTol() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x2 = mid
        // 2. x1 = mid
        // setting the tolerance to be high
        bisect.setTolerance(0.1);
        double result = bisect.run(2, -2);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX2X1MidLowTol() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x2 = mid
        // 2. x1 = mid
        // setting the tolerance to be low
        bisect.setTolerance(0.000000000001);
        double result = bisect.run(2, -2);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX1Mid() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // one loop
        // 1. x1 = mid
        double result = bisect.run(-2, 4);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX1X2Mid() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x1 = mid
        // 2. x2 = mid
        double result = bisect.run(-2, 1);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX1X2MidHighTol() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x1 = mid
        // 2. x2 = mid
        bisect.setTolerance(0.1);
        double result = bisect.run(-2, 1);
        assertEquals(1, result, bisect.getTolerance());
    }

    @Test
    public void test_IterX1X2MidLowTol() throws Bisect.RootNotFound, Bisect.MaxIterationsPassed {
        // two loops
        // 1. x1 = mid
        // 2. x2 = mid
        bisect.setTolerance(0.000000000001);
        double result = bisect.run(-2, 1);
        assertEquals(1, result, bisect.getTolerance());
    }
}