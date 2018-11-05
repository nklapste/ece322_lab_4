public class Bisect {

    private double tolerance;
    private int maxIterations;
    private polynomial func;

    public Bisect(polynomial f) {
        func = f;
        tolerance = 0.000001;
        maxIterations = 50;
    }

    public Bisect(double tol, polynomial f) {
        func = f;
        tolerance = tol;
        maxIterations = 50;
    }

    public Bisect(int maxIter, polynomial f) {
        func = f;
        tolerance = 0.000001;
        maxIterations = maxIter;
    }

    public Bisect(double tol, int maxIter, polynomial f) {
        func = f;
        tolerance = tol;
        maxIterations = maxIter;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tol) {
        if (tol > 0)
            tolerance = tol;
    }

    public double getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIter) {
        if (maxIter > 0)
            maxIterations = maxIter;
    }


    public double run(double x1, double x2) throws RootNotFound, MaxIterationsPassed {

        int iterNum = 1;
        double f1, f2, fmid;
        double mid = 0;

        // alteration of sourcecode for easier coverage testing validation
        while (true) {

            f1 = func.eval(x1);
            f2 = func.eval(x2);

            if (f1 * f2 > 0) { // L1
                throw new RootNotFound(); // T1
            }

            mid = (x1 + x2) / 2;
            fmid = func.eval(mid);
            if (fmid * f1 < 0) // L2
                x2 = mid; // x2 = med
            else
                x1 = mid; // x1 = med
            iterNum++;

            if (Math.abs(x1 - x2) / 2 >= tolerance) { //L3.1
                if (Math.abs(fmid) > tolerance) { // L3.2
                    if (iterNum <= maxIterations) { // L3.3
                        continue;
                    }
                    break;
                }
                break;
            }
            break;
        }

        // original do loop
//      do {
//         f1 = func.eval(x1);
//         f2 = func.eval(x2);
//
//         if (f1 * f2 > 0) { // L1
//            throw new RootNotFound(); // T1
//         }
//
//         mid = (x1 + x2) / 2;
//         fmid = func.eval(mid);
//         if (fmid * f1 < 0) // L2
//            x2 = mid; // x2 = mid
//         else
//            x1 = mid; // x1 = mid
//         iterNum++;
//      } while (Math.abs(x1 - x2) / 2 >= tolerance && Math.abs(fmid) > tolerance && iterNum <= maxIterations); // L3.1 L3.2 L3.3
//
        if (iterNum >= maxIterations) { // L4
            throw new MaxIterationsPassed(); // T2
        }

        return mid;
    }

    public interface polynomial {
        public double eval(double value);
    }

    public class RootNotFound extends Exception {

    }

    // adding additional exception for easier validation of branch coverage
    public class MaxIterationsPassed extends Exception {

    }
}