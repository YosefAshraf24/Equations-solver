package methods.main;

import java.io.IOException;
import java.math.RoundingMode;
import java.math.BigDecimal;

// Class for performing either forward elimination or backward substitution.
public class Forward {
	
// Applying forward elimination and applyong partial pivoting and scaling.
    public static void forward(double[][] A, double[] B,int p) throws IOException {
        int length = B.length;
        // scaling and check for partial pivoting.
        for (int k = 0; k < length; k++) {
            int max = k;
            double[][] scaledMatrix = Functions.scale(A, length, p);
            for (int i = k + 1; i < length; i++)
                if (Math.abs(scaledMatrix[i][k]) > Math.abs(scaledMatrix[max][k]))
                    max = i;
        // check for free variable.
            if(A[max][k] == 0)
            	return;
	    // apply partial pivoting.
            Functions.SwapRows(A, max, k);
            Functions.SwapValues(B, max, k);
        // perform elimination.
            for (int i = k + 1; i < length; i++) {
                double factor = BigDecimal.valueOf(A[i][k] / A[k][k]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                B[i] -= BigDecimal.valueOf(factor * B[k]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                for (int j = k; j < length; j++)
                    A[i][j] -= BigDecimal.valueOf(factor * A[k][j]).setScale(p, RoundingMode.HALF_UP).doubleValue();
            }
            Functions.fileWriter.write("Forward Elimination ["+(k+1)+"]\n"+Functions.PrintMatrix(A, B,length, p));
        }
    }

// Applying backward Elimination.
    public static void backward(double[][] A, double[] B, int p) throws IOException {
        int length = B.length;
        
        for (int k = length - 1; k >= 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                double factor = A[i][k] / A[k][k];
                B[i] -= BigDecimal.valueOf(factor*B[k]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                for (int j=k;j>0;j--)
                    A[i][j] -= BigDecimal.valueOf(factor * A[k][j]).setScale(p, RoundingMode.HALF_UP).doubleValue();
            }
            Functions.fileWriter.write( "Back Elimination ["+(length-k)+"]\n"+Functions.PrintMatrix(A, B,length, p));
        }
    }
    
}
