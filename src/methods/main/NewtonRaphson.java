package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class NewtonRaphson implements Solver {

    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon) throws IOException {
        double[] xnew = {0};
        double[] xold = {initGuess[0]};
        double ea = 100;
        int i = 0;
        if (Functions.subderivative(xold, A, y, x, p) == 0) {
            return "<html>You have not assumed the intial guesse right <br>ps: f'(x0) = 0 </html>";
        }

        Functions.fileWriter.write("Iteration no. [" + i + "]\n xnew = xold - f(xold)/f'(xold) \n ea = ( xnew - xold) / xnew ) * 100 %");
        

        while (ea >= epsilon && i < iterations) {
            double fxold = Functions.substitute(xold, A, y, x, p);
            double fdxold = Functions.subderivative(xold, A, y, x, p);
            xnew[0] = xold[0] - (fxold / fdxold);
            Functions.fileWriter.write("Iteration no. [" + (i + 1) + "]\n xnew = " + xold[0] + " - " + fxold + " / " + fdxold + " = " + xnew[0] + "\n");
            if (fxold == 0.0) {
                break;
            }

            ea = Math.abs((xnew[0] - xold[0]) / xnew[0]) * 100;
            xold[0] = xnew[0];
            i++;
            Functions.fileWriter.write(" ea = ((" + xnew[0] + " - " + xold[0] + " )" + " / " + xnew[0] + ") * 100% = " + ea + "%\n");

        }
        return (xnew[0]==Double.MAX_VALUE || xnew[0]==Double.MIN_VALUE)? "It Diverges":"<html>The value of root is : " + BigDecimal.valueOf(xnew[0]).setScale(p, RoundingMode.HALF_UP).doubleValue();
    }
}
