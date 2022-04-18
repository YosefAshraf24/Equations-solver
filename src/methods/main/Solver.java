package methods.main;

import java.io.IOException;

public interface Solver {
     public String solve(double[][] A, double[] y, String[] x, int p,double[] initGuess, int iterations, double epsilon) throws IOException;
}
