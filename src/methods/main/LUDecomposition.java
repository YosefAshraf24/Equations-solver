package methods.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LUDecomposition implements Solver{
    String subMethode; // Doolittle, Crout or Chleskey.
    double[][] Lower;
    double[][] Upper;
    double[][] D; // diagonal matrix used by cholesky method.
    LUDecomposition(String subMethode){
        this.subMethode = subMethode;
    }
    @Override
    public String solve(double[][] A, double[] y, String[] x, int p, double[] initGuess, int iterations, double epsilon) throws IOException {
        switch(subMethode){
            case "DoolittleForm":
                return this.DoolittleForm(A, y, x, p);
            case "CroutForm":
                return this.CroutForm(A, y, x, p);
            case "CholeskyForm":
                return this.CholeskyForm(A, y, x, p);
        }
        return"";
    }

    public String DoolittleForm(double[][] A, double[] y, String[] x, int p) throws IOException {
        int length = y.length;
        String solution="<html>";
        double sum = 0;
        Lower = new double[length][length];
        Upper = new double[length][length];
        Functions.Pivoting(A,y,p);
        int l , u;
        l=u=0;
        // Checking existence of the LU decomposition.
        if(Functions.checkU(A,length)) {
            return "System has no LU decomposition";
        }

        for (int i = 0; i < length; i++){

            //Upper
            for (int k = i; k < length; k++){
                sum = 0;
                for (int j = 0; j < i; j++) 
                	sum += BigDecimal.valueOf(Lower[i][j] * Upper[j][k]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                Upper[i][k] = BigDecimal.valueOf(A[i][k] - sum).setScale(p, RoundingMode.HALF_UP).doubleValue();
                Functions.fileWriter.write("Upper ["+(u+1)+"]\n"+Functions.Print(Upper,length, p));
                u++;
            }

            //Lower
            for (int k = i; k < length; k++){
                if (i == k)
                    Lower[i][i] = 1;
                else{
                    sum = 0;
                    for (int j = 0; j < i; j++) 
                    	sum += BigDecimal.valueOf((Lower[k][j] * Upper[j][i])).setScale(p, RoundingMode.HALF_UP).doubleValue();
                    Lower[k][i] = BigDecimal.valueOf((A[k][i] - sum) / Upper[i][i]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                }

                Functions.fileWriter.write("Lower ["+(l+1)+"]\n"+Functions.Print(Lower,length, p));
                l++;
            }
        }

	
        solution+="U matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Upper, length, p);

        solution+="L matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Lower,length, p);
        double[] temp = new double[length];
        Functions.ForwardSubstitution(Lower,y,temp, p);
        double[] temp2 = new double[length];
        Functions.BackSubstitution(Upper,temp,temp2, p);
        solution+=Functions.PrintSolve(temp2,x, p);
        solution+="</html>";
        return solution;

    }

    public String CroutForm(double[][] A, double[] y, String[] x, int p) throws IOException {
        int length = y.length;
        String solution="<html>";
        double sum = 0;
        Lower = new double[length][length];
        Upper = new double[length][length];
        Functions.Pivoting(A,y,p);
        int l=0,u=0;

        // Checking existence of the LU decomposition.
        if(Functions.checkU(A,length)) {
            return "System has no LU decomposition";
        }

        for (int i = 0; i < length; i++){

            //Lower
            for (int j = 0; j < length; j++){
                sum = 0;
                if (j < i)  Lower[j][i] = 0;
                else{
                    for (int k = 0; k < i; k++) 
                    	sum += BigDecimal.valueOf(Lower[j][k] * Upper[k][i]).setScale(p, RoundingMode.HALF_UP).doubleValue();
                    Lower[j][i] = BigDecimal.valueOf(A[j][i] - sum).setScale(p, RoundingMode.HALF_UP).doubleValue();
                }
                Functions.fileWriter.write("Lower ["+(l+1)+"]\n"+Functions.Print(Lower,length, p));
                l++;
            }

            //Upper
            for (int j = 0; j < length; j++){
                sum=0;
                if (j < i)
                    Upper[i][j] = 0;
                else if (j == i)
                    Upper[i][j] = 1;
                else{
                    for (int k = 0; k < i; k++) 
                    	sum += BigDecimal.valueOf(((Lower[i][k] * Upper[k][j]) / Lower[i][i])).setScale(p, RoundingMode.HALF_UP).doubleValue();
                    Upper[i][j] = BigDecimal.valueOf((A[i][j] / Lower[i][i]) - sum).setScale(p, RoundingMode.HALF_UP).doubleValue();		
                }

                Functions.fileWriter.write("Upper ["+(u+1)+"]\n"+Functions.Print(Upper,length, p));
                u++;
            }
        }

        solution+="U matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Upper,length, p);

        solution+="L matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Lower,length, p);
        double[] temp = new double[length];
        Functions.ForwardSubstitution(Lower,y,temp, p);
        double[] temp2 = new double[length];
        Functions.BackSubstitution(Upper,temp,temp2, p);

        solution+=Functions.PrintSolve(temp2,x, p);
        solution+="</html>";
        return solution;
    }
    
    public String CholeskyForm(double[][] A, double[] y, String[] x, int p) throws IOException {
        int length = y.length;
        String solution="<html>";
        double sum = 0;
        Lower = new double[length][length];
        Upper = new double[length][length];
        D = new double[length][length];
        Functions.Pivoting(A,y,p);
        int l=0,u=0;
        // Checking existence of the LU decomposition.
        if(Functions.checkU(A,length)) {
            return "System has no LU decomposition";
        }

        for (int i = 0; i < length; i++){

            //Upper
            for (int k = i; k < length; k++){
                sum = 0;
                for (int j = 0; j < i; j++) 
                	sum +=  BigDecimal.valueOf((Lower[i][j] * Upper[j][k])).setScale(p, RoundingMode.HALF_UP).doubleValue();
                Upper[i][k] = BigDecimal.valueOf(A[i][k] - sum).setScale(p, RoundingMode.HALF_UP).doubleValue();
                Functions.fileWriter.write("Upper ["+(u+1)+"]\n"+Functions.Print(Upper,length, p));
                u++;
            }

            //Lower
            for (int k = i; k < length; k++){
                if (i == k)
                    Lower[i][i] = 1;
                else{
                    sum = 0;
                    for (int j = 0; j < i; j++) 
                    	sum += BigDecimal.valueOf((Lower[k][j] * Upper[j][i])).setScale(p, RoundingMode.HALF_UP).doubleValue();
                    Lower[k][i] = BigDecimal.valueOf((A[k][i] - sum) / Upper[i][i]).setScale(p, RoundingMode.HALF_UP).doubleValue();        		
                }
                Functions.fileWriter.write("Lower ["+(l+1)+"]\n"+Functions.Print(Lower,length, p));
                l++;
            }
        }

        //Diagonal
        for (int i = 0; i < length; i++) {
			double temp = Upper[i][i];
			D[i][i] = temp;
			for (int j = 0; j < length; j++) {
				if (i != j) 
					D[i][j] = 0;
				Upper[i][j] /= BigDecimal.valueOf(temp).setScale(p, RoundingMode.HALF_UP).doubleValue();
                Functions.fileWriter.write("Upper ["+(u+1)+"]\n"+Functions.Print(Upper,length, p));
                u++;
			}
            Functions.fileWriter.write("Diagonal ["+(i+1)+"]\n"+Functions.Print(D,length, p));
		}

        solution+="U matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Upper,length, p);
        
        solution+="D matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(D,length, p);

        solution+="L matrix : "+"<br>";
        solution+=Functions.PrintMatrixLU(Lower,length, p);
        
        double[] temp = new double[length];
        Functions.ForwardSubstitution(Lower,y,temp, p);
        double[] temp2 = new double[length];
        Functions.ForwardSubstitution(D,temp,temp2, p);
        double[] temp3 = new double[length];
        Functions.BackSubstitution(Upper,temp2,temp3, p);
        
        solution+=Functions.PrintSolve(temp3,x, p);
        solution+="</html>";
        return solution;

    }
}
