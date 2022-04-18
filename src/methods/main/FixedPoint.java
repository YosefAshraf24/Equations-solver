package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class FixedPoint implements Solver {

    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon) throws IOException {
        double[][] Ag = new double[A.length][A[0].length + 1];
        String[] xg = new String[x.length + 1];
        System.arraycopy(x, 0, xg, 0, x.length);
        xg[x.length] = "x";
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                Ag[i][j] = A[i][j];
            }
        }
        Ag[0][A[0].length] = 1;

        double[] xc = {initGuess[0]};
        double xold = 0;
        
        double ea = 100;
        int i = 0;
        
        
        Functions.fileWriter.write("Iteration no. [" + i + "]\n x(i+1) = g(x(i)) \n ea = ( (x(i+1) - x(i)) / x(i+1) ) * 100 %");

        // Initialize result
        while (ea >= epsilon && i < iterations) {
            xold = xc[0];
            xc[0] = Functions.substitute(xc, Ag, y, xg, p);
            double fxc = Functions.substitute(xc, A, y, x, p);
            Functions.fileWriter.write("Iteration no. [" + (i + 1) + "]\n x(i+1) = g("+xold +") = "+xc[0] +"\n");
            
            if (fxc == 0.0) {
                break;
            }
            ea = Math.abs((xc[0] - xold) / xc[0]) * 100;
            i++;
            Functions.fileWriter.write(" ea = ((" + xc[0] + " - " + xold + " )" + " / " + xc[0] + ") * 100% = " + ea + "%\n");

        }
        return (xc[0]==Double.MAX_VALUE || xc[0]==Double.MIN_VALUE)? "<html>Wrong intial assumstion leads to <br> divergence</html>":"<html>The value of root is : " + BigDecimal.valueOf(xc[0]).setScale(p, RoundingMode.HALF_UP).doubleValue();
        
    }

}
