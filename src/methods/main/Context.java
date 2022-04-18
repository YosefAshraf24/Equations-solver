package methods.main;

import java.io.IOException;

public class Context {
   private Solver solver;

   public Context(String methode, String subMethode){
         switch(methode){
            case "Gauss Elimination":
                this.solver = new GaussianElimination();
                break;
            case "Gauss Jordan":
                this.solver = new GaussJordanElimination();
                break;
            case "LU Decomposition":
                this.solver = new LUDecomposition(subMethode);
                break;
            case "Gauss Seidel":
                this.solver = new GaussSeidel();
                break;            
            case "Jacobi Iteration":
                this.solver = new Jacobi();
                break;
            case "Bisection":
                this.solver = new Bisection();
                break;
            case "False Position":
                this.solver = new FalsePosition();
                break;
            case "Newton Raphson":
                this.solver = new NewtonRaphson();
                break;
            case "Secant":
                this.solver = new Secant();
                break;
            case "Fixed Point":
                this.solver = new FixedPoint();
                break;

        }
   }

   public String solve(double[][] A, double[] y, String[] x, int p,double[] initGuess, int iterations, double epsilon) throws IOException {
      return solver.solve(A, y, x, p, initGuess, iterations, epsilon);
   }
}