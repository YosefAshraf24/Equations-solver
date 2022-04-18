package methods.main;

import java.io.IOException;

public class GaussJordanElimination implements Solver{

    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon){
        int length = y.length;
        try {
            Forward.forward(A, y, p); // forward elimination 
        } catch (IOException ex) {}
        
        double[] solution = new double[length];
        double a = A[length-1][length-1];
        double b = y[length-1];
        // Checking if there is infinite number of solutions or no solution.
        // Else perform the backward elimination and substitution.
        if(a == 0){
            if(b == 0) return "Infinity Solution";
            else return "No Solution";
        }
        else {
            try {
                Forward.backward(A, y, p); // backward elimination
            } catch (IOException ex) {}
            Functions.BackSubstitution(A, y, solution, p);
            return Functions.PrintSolve(solution, x, p);
        }

    }
}
