package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Secant implements Solver {

    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon) throws IOException {
        double[] xp = {initGuess[0]}; //x(i-1)  previous
        double[] xc = {initGuess[1]};//x(i)  current
        double[] xn = {0};//x(i+1)  next
        double ea = 100;
        int i = 0;


        Functions.fileWriter.write("Iteration no. [" + i + "]\n x(i+1) = x(i) - ( f(x(i)) ( x(i-1) - x(i) ) ) / (f(x(i-1))-f(x(i)) ) \n ea = ( (x(i+1) - x(i)) / x(i+1) ) * 100 %");

        while (ea >= epsilon && i < iterations) {
            double fxp = Functions.substitute(xp, A, y, x, p);
            double fxc = Functions.substitute(xc, A, y, x, p);
            xn[0] = xc[0] - (fxc * (xp[0] - xc[0])) / (fxp - fxc);
            double fxn = Functions.substitute(xn, A, y, x, p);
            Functions.fileWriter.write("Iteration no. [" + (i + 1) + "]\n x(i+1) = "+xc[0] +" -  ( " + fxc + " ( " + xp[0] + " - " + xc[0] +" ) / ( " + fxp + " - " + fxc + " ) = " + xn[0] + "\n");
            if (fxn == 0.0) {
                break;
            }
            ea = Math.abs((xn[0] - xc[0]) / xn[0]) * 100;
            xp[0] = xc[0];
            xc[0] = xn[0];
            i++;
            Functions.fileWriter.write(" ea = ((" + xn[0] + " - " + xc[0] + " )" + " / " + xn[0] + ") * 100% = " + ea + "%\n");

        }
        return (xn[0]==Double.MAX_VALUE || xn[0]==Double.MIN_VALUE)? "It Diverges":"<html>The value of root is : " + BigDecimal.valueOf(xn[0]).setScale(p, RoundingMode.HALF_UP).doubleValue();

    }
}
