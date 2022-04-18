package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import methods.Point;

public class FalsePosition implements Solver {
    public static ArrayList<Point> points = new ArrayList<>();

    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon) throws IOException {
        double[] xl = {initGuess[0]};
        double[] xu = {initGuess[1]};
        double[] xr = {0};
        double xrold;
        double ea = 100;
        int i = 0;
        if (Functions.substitute(xl, A, y, x, p) * Functions.substitute(xu, A, y, x, p) > 0) {
            return "<html>You have not assumed<br> the upper and lower guesses right</html>";
        } else if (Functions.substitute(xl, A, y, x, p) == 0) {
            return "The value of root is : " + xl[0];
        } else if (Functions.substitute(xu, A, y, x, p) == 0) {
            return "The value of root is : " + xu[0];
        }

        Functions.fileWriter.write("Iteration no. [" + i + "]\n xr = ( xl * f(xu) - xu * f(xl) ) / (f(xu)-f(xl) ) \n ea = ( (xr(new) - xr(old)) / xr(new) ) * 100 %");

        // Initialize result
        xrold = xl[0];
        while (ea >= epsilon && i < iterations) {
            points.add(new Point(xl[0],0));
            points.add(new Point(xu[0],0));
            double fxl = Functions.substitute(xl, A, y, x, p);
            double fxu = Functions.substitute(xu, A, y, x, p);
            xr[0] = (xl[0]*fxu - xu[0]*fxl) / (fxu-fxl);
            double fxr = Functions.substitute(xr, A, y, x, p);
            Functions.fileWriter.write("Iteration no. [" + (i + 1) + "]\n xr = ( "+xl[0]+" * "+fxu+" - "+xu[0]+" * "+fxl+" ) / ( "+fxu+" - "+fxl+" ) = "+ xr[0] +"\n");
            // Check if middle point is root
            if (fxr == 0.0) {
                break;
            } // Decide the side to repeat the steps
            else if (fxr < 0) {
                xu[0] = xr[0];
            } else {
                xl[0] = xr[0];
            }
            ea = Math.abs((xr[0] - xrold) / xr[0]) * 100;
            xrold = xr[0];
            i++;
            Functions.fileWriter.write(" ea = ((" + xr[0] + " - " + xrold + " )" + " / " + xr[0] + ") * 100% = " + ea + "%\n");

        }
        return (xr[0]==Double.MAX_VALUE || xr[0]==Double.MIN_VALUE)? "It Diverges":"<html>The value of root is : " + BigDecimal.valueOf(xr[0]).setScale(p, RoundingMode.HALF_UP).doubleValue();

    }
}
