package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussSeidel extends Jacobi{
	@Override
	public String solve(double[][] A, double[] y, String[] x, int p,double[] initGuess, int iterations, double epsilon) {
		int n = initGuess.length;
		// Check if there is 0 exists in the diagonal so swap it with other rows to prevent division by 0 i.e:
		// 0x+2y=5 (1)
		// 2x+y=3  (2)
		//(1) and (2) will be swapped.
                for(int i =0;i<A.length-1;i++)
                    for(int j = 0; j<A[0].length;j++)
                        if(i==j && A[i][j] == 0){
                            Functions.SwapRows(A, j, j+1);
                            Functions.SwapValues(y, j, j+1);
                        }
                if(A[A.length-1][A[0].length-1]==0) {Functions.SwapRows(A, A.length-1, A.length-2);Functions.SwapValues(y, A.length-1, A.length-2);}
                
            try {
                Functions.fileWriter.write("Iteration Rules\n" + Functions.PrintSolveIterativeLaw(A, x, y));
            } catch (IOException ex) {}


		double[] result = initGuess.clone();
		double[] prev = result.clone();
		for (int i = 0; i < iterations; i++) {
			prev = result.clone();
			for (int row = 0; row < n; row++) {
				double sum = y[row];
				for (int column = 0; column < n; column++) {
					if (column != row)
						sum -= BigDecimal.valueOf(A[row][column] * result[column]).setScale(p, RoundingMode.HALF_UP).doubleValue();
				}
				// updating guess each iteration.
				result[row] = BigDecimal.valueOf(sum / A[row][row]).setScale(p, RoundingMode.HALF_UP).doubleValue();
			}
                        try {//write this iteration to steps file
                                Functions.fileWriter.write("Iteration no. ["+(i+1)+"]\n"+Functions.PrintSolveIterative(result,x,p));
                            } catch (IOException ex) {}
			if (calcE(prev, result) < epsilon) // relative error check.
				break;
		}
		return Functions.PrintSolve(result,x,p);
	}
}
