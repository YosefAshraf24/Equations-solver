package methods.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Supplying intermediate operations like swapping rows or value, Substitutions, ...etc.
public class Functions {

    public static File file;
    public static FileWriter fileWriter;

    public static int findIndex(String arr[], String t) {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i].equals(t)) {
                return i;
            } else {
                i = i + 1;
            }
        }
        return -1;
    }

    public static void SwapRows(double[][] a, int max, int n) {
        double[] temp = a[n];
        a[n] = a[max];
        a[max] = temp;
    }

    public static void SwapValues(double[] a, int max, int n) {
        double temp = a[n];
        a[n] = a[max];
        a[max] = temp;
    }

    public static double subderivative(double[] values, double[][] A, double[] y, String[] x, int p) {
        String variables = "xyz";
        double sum = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (x[j].contains("exp")) {//exponintial
                    if (x[j].contains("^")) {//exp(-3x^-2)  ///     exp(a * x^b)

                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].lastIndexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.pow(Math.E, a * Math.pow(v, b)) * b * a * Math.pow(v, b - 1);
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].lastIndexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.pow(Math.E, a * v) * a;
                    }

                } else if (x[j].contains("sin")) {//sin
                    if (x[j].contains("^")) {//    sin(a * x^b )
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.cos(a * Math.pow(v, b)) * a * b * Math.pow(v, b - 1);
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.cos(a * v) * a;
                    }
                } else if (x[j].contains("cos")) {//cos
                    if (x[j].contains("^")) {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.sin(a * Math.pow(v, b)) * a * b * Math.pow(v, b - 1) * -1;
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.sin(a * v) * a * -1;
                    }
                } else if (x[j].contains("^")) {//polynomial with high degree
                    sum += A[i][j] * Math.pow(values[variables.indexOf(x[j].charAt(0))], Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1)) - 1) * Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1));
                } else if (x[j].equals("")) {//absolute
                    sum += 0;
                } else {//polynomial
                    sum += A[i][j];
                }
            }
        }
                if (Double.isInfinite(sum)) {
            if (values[0] > 0) {
                sum = Double.MAX_VALUE;
            } else {
                sum = Double.MIN_VALUE;
            }
        } else if (Double.isNaN(sum)) {
            if (values[0] > 0) {
                sum = Double.MAX_VALUE;
            } else {
                sum = Double.MIN_VALUE;
            }
        }
        return BigDecimal.valueOf(sum).setScale(p, RoundingMode.HALF_UP).doubleValue();
    }

    public static double substitute(double[] values, double[][] A, double[] y, String[] x, int p) {
        String variables = "xyz";
        double sum = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (x[j].contains("exp")) {//exponintial
                    if (x[j].contains("^")) {//exp(-3x^-2)

                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].lastIndexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.pow(Math.E, a * Math.pow(v, b));
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].lastIndexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.pow(Math.E, a * v);
                    }

                } else if (x[j].contains("sin")) {//sin
                    if (x[j].contains("^")) {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.sin(a * Math.pow(v, b));
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.sin(a * v);
                    }
                } else if (x[j].contains("cos")) {//cos
                    if (x[j].contains("^")) {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        double b = Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1, x[j].indexOf(")")));
                        sum += A[i][j] * Math.cos(a * Math.pow(v, b));
                    } else {
                        double a = Double.parseDouble(x[j].substring(x[j].indexOf("(") + 1, x[j].indexOf("x")));
                        double v = values[0];
                        sum += A[i][j] * Math.cos(a * v);
                    }
                } else if (x[j].contains("^")) {//polynomial with high degree
                    sum += A[i][j] * Math.pow(values[variables.indexOf(x[j].charAt(0))], Double.parseDouble(x[j].substring(x[j].indexOf("^") + 1)));
                } else if (x[j].equals("")) {//absolute
                    sum += A[i][j];
                } else {//polynomial
                    sum += A[i][j] * values[variables.indexOf(x[j].charAt(0))];
                }
            }
        }
        if (Double.isInfinite(sum)) {
            if (values[0] > 0) {
                sum = Double.MAX_VALUE;
            } else {
                sum = Double.MIN_VALUE;
            }
        } else if (Double.isNaN(sum)) {
            if (values[0] > 0) {
                sum = Double.MAX_VALUE;
            } else {
                sum = Double.MIN_VALUE;
            }
        }
        System.out.println(values[0] + "  " + sum);
        System.out.println((sum + "").equals("NaN"));

        return BigDecimal.valueOf(sum).setScale(p, RoundingMode.HALF_UP).doubleValue();
    }
// Used in LU decomposition to check if there is such composition or not.

    public static Boolean checkU(double[][] a, int n) {

        int num;
        for (int k = 0; k < n - 1; k++) {
            for (int i = k + 1; i < n; i++) {
                num = 0;
                double ratio = 0.0;
                /*
                * Befor we calculate ratio ,We check the first element in the row equal zero
                * or the first element in the another row -compare with it- equal zero
                **/
                if (a[k][0] == 0 || a[i][0] == 0) {
                    int y = 0;
                    // Search to element in the row not equal zero
                    for (int m = 0; m < n; m++) {
                        if (a[k][y] == 0) {
                            y++;
                        }
                    }
                    ratio = a[k][y] / a[i][y];
                } else {
                    ratio = a[k][0] / a[i][0];
                }
                int numZero = 0;
                //Check if there is relation between two rows
                for (int j = 0; j < n; j++) {
                    if (a[k][j] == 0 && a[i][j] == 0) {
                        numZero++;
                    } else if (Math.abs(a[k][j]) >= Math.abs(a[i][j])) {
                        if ((a[k][j] % a[i][j] == 0) && (a[k][j] / a[i][j] == ratio)) {
                            num++;
                        }
                    } else if (Math.abs(a[k][j]) < Math.abs(a[i][j])) {
                        if (a[i][j] % a[k][j] == 0 && (a[k][j] / a[i][j] == ratio)) {
                            num++;
                        }
                    }
                }
                if ((num + numZero) == n) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void ForwardSubstitution(double[][] A, double[] B, double[] solution, int p) {
        int N = B.length;
        for (int i = 0; i < N; i++) {
            double sum = 0.0;
            for (int j = 0; j < i; j++) {
                sum += BigDecimal.valueOf(A[i][j] * solution[j]).setScale(p, RoundingMode.HALF_UP).doubleValue();
            }
            solution[i] = BigDecimal.valueOf((B[i] - sum) / A[i][i]).setScale(p, RoundingMode.HALF_UP).doubleValue();
        }

    }

    public static void BackSubstitution(double[][] A, double[] B, double[] solution, int p) {
        int N = B.length;
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += BigDecimal.valueOf(A[i][j] * solution[j]).setScale(p, RoundingMode.HALF_UP).doubleValue();
            }
            solution[i] = BigDecimal.valueOf((B[i] - sum) / A[i][i]).setScale(p, RoundingMode.HALF_UP).doubleValue();
        }

    }

    // Printing matrix in intermediate steps.
    public static String PrintMatrix(double[][] A, double[] B, int length, int p) {
        String solution = "***********************\n";
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                solution += BigDecimal.valueOf(A[i][j]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "  ";
            }
            solution += " | " + BigDecimal.valueOf(B[i]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "  ";
            solution += "\n\n";
        }
        solution += "\n";
        return solution;
    }

    public static String PrintMatrixLU(double[][] A, int length, int p) {
        String solution = "";
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                solution += BigDecimal.valueOf(A[i][j]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "  ";
            }
            solution += "<br><br>";
        }
        solution += "<br>";
        return solution;
    }
// Printing final results.

    public static String PrintSolve(double[] b, String[] c, int p) {
        int length = b.length;
        String solution = "<html>";
        for (int i = 0; i < length; i++) {
            solution += c[i] + " = " + BigDecimal.valueOf(b[i]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "<br>";
        }
        solution += "</html>";
        return solution;
    }

    public static String PrintSolveIterative(double[] b, String[] c, int p) {
        int length = b.length;
        String solution = "***********************\n";
        for (int i = 0; i < length; i++) {
            solution += c[i] + " = " + BigDecimal.valueOf(b[i]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "\n";
        }
        solution += "\n";
        return solution;
    }

    public static String PrintSolveIterativeLaw(double[][] A, String[] x, double[] y) {
        String solution = "***********************\n";
        for (int row = 0; row < x.length; row++) {
            solution += x[row] + " = (" + y[row];
            for (int column = 0; column < x.length; column++) {
                if (column != row) {
                    solution += " + " + (-1 * A[row][column]) + " * " + x[column];
                }
            }
            // updating guess each iteration.
            solution += ") /" + A[row][row] + "\n";
        }
        solution += "\n";
        return solution;
    }
// Using for applying scaling before partial pivoting.
// Given a matrix it returns a scaled form of this matrix.

    public static double[][] scale(double[][] a, int n, int p) {
        double max = 0;
        double[][] scaledMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                scaledMatrix[i][j] = a[i][j];
            }
        }
        // Find max element in each row and divide all elements by it.
        for (int i = 0; i < n; i++) {
            max = 0;
            for (int j = 0; j < n; j++) {
                if (Math.abs(a[i][j]) > Math.abs(max)) {
                    max = scaledMatrix[i][j];
                }
            }
            for (int k = 0; k < n; k++) {
                scaledMatrix[i][k] /= BigDecimal.valueOf(max).setScale(p, RoundingMode.HALF_UP).doubleValue();
            }
        }
        return scaledMatrix;
    }

    public static void createFile() throws IOException {
        file = new File("Steps Of Solution.txt");
        file.createNewFile();
        fileWriter = new FileWriter("Steps Of Solution.txt");

    }

    public static void Pivoting(double[][] A, double[] B, int p) {
        int length = B.length;
        // scaling and check for partial pivoting.
        for (int k = 0; k < length; k++) {
            int max = k;
            double[][] scaledMatrix = Functions.scale(A, length, p);
            for (int i = k + 1; i < length; i++) {
                if (Math.abs(scaledMatrix[i][k]) > Math.abs(scaledMatrix[max][k])) {
                    max = i;
                }
            }
            // check for free variable.
            if (A[max][k] == 0) {
                return;
            }
            // apply partial pivoting.
            Functions.SwapRows(A, max, k);
            Functions.SwapValues(B, max, k);

        }
    }

    public static String Print(double[][] A, int n, int p) {
        String solution = "";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                solution += BigDecimal.valueOf(A[i][j]).setScale(p, RoundingMode.HALF_UP).doubleValue() + "  ";
            }
            solution += "\n";
        }
        solution += "\n";
        return solution;
    }

}
