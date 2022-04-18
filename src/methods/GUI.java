package methods;


import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import methods.Eqn.EquationMember;
import methods.main.Bisection;
import methods.main.Context;
import methods.main.FalsePosition;
import methods.main.Functions;

public class GUI extends javax.swing.JFrame {

    private ArrayList<Eqn> equations;
    private ArrayList<String> variables;
    int N;
    int precision;
    double[][] A; // Ax = y
    String[] x;
    double[] y;
    double[] initGuess;
    int iterations = 20;
    double epsilon = 0.00001;
    String methode = "Gauss Elimination";
    String subMethode = "DoolittleForm";
    Context context;
    long startTime;
    long endTime;
    boolean linear = true;
    public static ArrayList<Point> points;

    public GUI() {
        setTitle("System of Linear Equations Solver");
        initComponents();
        this.setLocation(0,0);//center the window in middle of any screen
        this.subMethodeComboBox.setVisible(false);
        Label8.setVisible(false);
        Label9.setVisible(false);
        Label10.setVisible(false);
        this.intialGuessTextField.setVisible(false);
        this.iterationsTextField.setVisible(false);
        this.epsilonTextField.setVisible(false);
        this.comboBox.addItemListener((ItemEvent ie) -> {//hide unnessesary fields
            methode = comboBox.getSelectedItem() + "";
            if (methode.equals("Gauss Seidel") || methode.equals("Jacobi Iteration") || methode.equals("Bisection") || methode.equals("False Position") || methode.equals("Newton Raphson") || methode.equals("Secant") || methode.equals("Fixed Point")) {
                Label8.setVisible(true);
                Label9.setVisible(true);
                Label10.setVisible(true);
                this.intialGuessTextField.setVisible(true);
                this.iterationsTextField.setVisible(true);
                this.epsilonTextField.setVisible(true);
            } else {
                Label8.setVisible(false);
                Label9.setVisible(false);
                Label10.setVisible(false);
                this.intialGuessTextField.setVisible(false);
                this.iterationsTextField.setVisible(false);
                this.epsilonTextField.setVisible(false);
            }
            if (methode.equals("Gauss Elimination") || methode.equals("Gauss Jordan") || methode.equals("Gauss Seidel") || methode.equals("Jacobi Iteration") || methode.equals("LU Decomposition")) {
                this.linear = true;
            } else {
                this.linear = false;
            }

            if (methode.equals("LU Decomposition")) {
                subMethodeComboBox.setVisible(true);
            } else {
                subMethodeComboBox.setVisible(false);
            }
        });
        this.solvebtn1.addActionListener((e) -> {
            try {
                this.start();
            } catch (IOException ex) {
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        equationsTextArea = new javax.swing.JTextArea();
        comboBox = new javax.swing.JComboBox<>();
        resultLabel = new javax.swing.JLabel();
        precisionTextField = new javax.swing.JTextField();
        subMethodeComboBox = new javax.swing.JComboBox<>();
        iterationsTextField = new javax.swing.JTextField();
        intialGuessTextField = new javax.swing.JTextField();
        timeLabel = new javax.swing.JLabel();
        Label1 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        Label3 = new javax.swing.JLabel();
        Label4 = new javax.swing.JLabel();
        Label5 = new javax.swing.JLabel();
        Label6 = new javax.swing.JLabel();
        Label7 = new javax.swing.JLabel();
        Label8 = new javax.swing.JLabel();
        Label9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Label10 = new javax.swing.JLabel();
        epsilonTextField = new javax.swing.JTextField();
        solvebtn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        equationsTextArea.setColumns(20);
        equationsTextArea.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        equationsTextArea.setRows(5);
        jScrollPane1.setViewportView(equationsTextArea);

        comboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gauss Elimination", "Gauss Jordan", "LU Decomposition", "Gauss Seidel", "Jacobi Iteration", "Bisection", "False Position", "Fixed Point", "Newton Raphson", "Secant" }));
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        resultLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        precisionTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        precisionTextField.setText("6");
        precisionTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precisionTextFieldActionPerformed(evt);
            }
        });

        subMethodeComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        subMethodeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DoolittleForm", "CroutForm", "CholeskyForm" }));
        subMethodeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subMethodeComboBoxActionPerformed(evt);
            }
        });

        iterationsTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        iterationsTextField.setText("50");
        iterationsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                iterationsTextFieldActionPerformed(evt);
            }
        });

        intialGuessTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        intialGuessTextField.setText("0,1");
        intialGuessTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intialGuessTextFieldActionPerformed(evt);
            }
        });

        timeLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        Label1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Label1.setText(" Enter Equations line by line like:");

        Label2.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label2.setText(" 3x+y=11");

        Label3.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label3.setText("2x+5y=16");

        Label4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Label4.setText("OR");

        Label5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label5.setText("2, 5, 16");

        Label6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label6.setText("3, 1, 11");

        Label7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label7.setText("Precision:");

        Label8.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label8.setText("# Iterations:");

        Label9.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        Label9.setText("Intial:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel1.setText("Time:");

        Label10.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        Label10.setText("ε:");

        epsilonTextField.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        epsilonTextField.setText("0.00001");
        epsilonTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                epsilonTextFieldActionPerformed(evt);
            }
        });

        solvebtn1.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        solvebtn1.setForeground(new java.awt.Color(0, 153, 51));
        solvebtn1.setText("Solve");
        solvebtn1.setActionCommand("solve");
        solvebtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solvebtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(130, 130, 130)
                            .addComponent(Label1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(151, 151, 151)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Label3)
                                .addComponent(Label2))
                            .addGap(18, 18, 18)
                            .addComponent(Label4)
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Label5)
                                .addComponent(Label6)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(67, 67, 67)
                                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(subMethodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(Label8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(Label7)
                                            .addGap(28, 28, 28))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(Label10)
                                            .addGap(53, 53, 53)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(intialGuessTextField)
                                        .addComponent(iterationsTextField)
                                        .addComponent(epsilonTextField)
                                        .addComponent(precisionTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(Label9)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(107, 107, 107)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(123, 123, 123)
                .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(343, 343, 343)
                    .addComponent(solvebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(492, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Label2)
                                    .addComponent(Label6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Label3)
                                    .addComponent(Label5)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(Label4)))
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subMethodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(precisionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(iterationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(intialGuessTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label9))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(epsilonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Label10))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(resultLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(457, Short.MAX_VALUE)
                    .addComponent(solvebtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(368, 368, 368)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void prepareEquations() {
        this.equations = new ArrayList<>();
        variables = new ArrayList<>();
        String[] stringEquations = equationsTextArea.getText().split("\n");
        precision = Integer.parseInt(precisionTextField.getText());
        if (stringEquations[0].equals("")) {
            return; /// show alert
        }
        for (int i = 0; i < stringEquations.length; i++) {
            Eqn e = new Eqn(stringEquations[i]);
            this.equations.add(e);
        }

        N = equations.size();
        y = new double[N];

        for (Eqn i : equations) {
            for (EquationMember j : i.leftMembers) {
                if (!variables.contains(j.variable)) {
                    variables.add(j.variable);
                }
            }
        }
        A = new double[N][this.variables.size()];
        x = new String[this.variables.size()];
        int l = 0;
        for (Eqn i : equations) {
            for (EquationMember j : i.leftMembers) {
                A[l][variables.indexOf(j.variable)] = j.constant;
                x[variables.indexOf(j.variable)] = variables.get(variables.indexOf(j.variable));
            }
            y[l] = i.rightMembers.get(0).constant;
            l++;
        }
    }

    public String solve() throws IOException {
        context = new Context(methode, subMethode);
        return context.solve(A, y, x, precision, initGuess, iterations, epsilon);
    }

    public void start() throws IOException {
        prepareEquations();
        //read fields
        this.subMethode = this.subMethodeComboBox.getSelectedItem() + "";
        this.precision = Integer.parseInt(precisionTextField.getText());
        this.iterations = Integer.parseInt(this.iterationsTextField.getText());
        this.epsilon = Double.parseDouble(this.epsilonTextField.getText());
        String[] s = this.intialGuessTextField.getText().replaceAll(" ", "").split(",");
        this.initGuess = new double[10];
        for (int i = 0; i < s.length; i++) {
            this.initGuess[i] = Double.parseDouble(s[i]);
        }
        try {
            Functions.createFile();
        } catch (IOException ex) {
        }

        //check if equations are nonlinear and linear methode is choosen
        String eqns = this.equationsTextArea.getText();
        if ((eqns.contains("^") || eqns.contains("e") || eqns.contains("sin") || eqns.contains("cos")) && this.linear == true) {
            this.resultLabel.setText("<html> Can't Solve Non-Linear Equations <br> With Linear Methode like:<br><br> " + this.methode + "</html>");
            return;
        }
        if (this.methode.equals("Bisection") || this.methode.equals("False Position")) {
            points = new ArrayList<>();
            double[] values = new double[1];
            for (double i = -100; i <= 100; i += 0.1) {
                values[0] = i;
                double fx=Functions.substitute(values, A, y, x, precision);
                if(fx>100)
                    points.add(new Point(i,6000 ));
                else if(fx<-100)
                    points.add(new Point(i,-6000 ));
                else
                    points.add(new Point(i,fx ));
            }
        } else if (this.methode.equals("Fixed Point")) {
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
            points = new ArrayList<>();
            double[] values = new double[1];
            for (double i = -100; i <= 100; i += 0.1) {
                values[0] = i;
                double fx=Functions.substitute(values, Ag, y, xg, precision);
                if(fx>100)
                    points.add(new Point(i,6000 ));
                else if(fx<-100)
                    points.add(new Point(i,-6000 ));
                else
                    points.add(new Point(i,fx ));
            }
        } else if(methode.equals("Newton Raphson") || methode.equals("Secant")){
            points = new ArrayList<>();
            double[] values = new double[1];
            for (double i = -100; i <= 100; i += 0.1) {
                values[0] = i;
                double fx=Functions.subderivative(values, A, y, x, precision);
                if(fx>100)
                    points.add(new Point(i,6000 ));
                else if(fx<-100)
                    points.add(new Point(i,-6000 ));
                else
                    points.add(new Point(i,fx ));
            }

        }
        startTime = System.nanoTime();

        String solution = solve();
        try {
            Functions.fileWriter.close();
        } catch (IOException ex) {
        }

        if (this.methode.equals("Bisection")) {
            DrawingStuff ex = new DrawingStuff(points, Bisection.points, "f(x) and Boundary functions");
            ex.setVisible(true);
        } else if (this.methode.equals("False Position")) {
            DrawingStuff ex = new DrawingStuff(points, FalsePosition.points,"f(x) and Boundary functions");
            ex.setVisible(true);
        } else if (this.methode.equals("Fixed Point")) {
            ArrayList<Point> point = new ArrayList<>();
            point.add(new Point(0, 0));
            DrawingStuff ex = new DrawingStuff(points, point, "g(x) and  y = x");
            ex.setVisible(true);
        } else if(methode.equals("Newton Raphson") || methode.equals("Secant")){
            ArrayList<Point> point = new ArrayList<>();
            DrawingStuff ex = new DrawingStuff(points, point, "f'(x)");
            ex.setVisible(true);
        }
        this.resultLabel.setText(solution);
        endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        this.timeLabel.setText(totalTime / 1000 + " micro sec");
    }
    private void precisionTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precisionTextFieldActionPerformed

    }//GEN-LAST:event_precisionTextFieldActionPerformed

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed

    }//GEN-LAST:event_comboBoxActionPerformed

    private void subMethodeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subMethodeComboBoxActionPerformed

    }//GEN-LAST:event_subMethodeComboBoxActionPerformed

    private void iterationsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_iterationsTextFieldActionPerformed

    }//GEN-LAST:event_iterationsTextFieldActionPerformed

    private void intialGuessTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intialGuessTextFieldActionPerformed

    }//GEN-LAST:event_intialGuessTextFieldActionPerformed

    private void epsilonTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_epsilonTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_epsilonTextFieldActionPerformed

    private void solvebtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solvebtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_solvebtn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label10;
    private javax.swing.JLabel Label2;
    private javax.swing.JLabel Label3;
    private javax.swing.JLabel Label4;
    private javax.swing.JLabel Label5;
    private javax.swing.JLabel Label6;
    private javax.swing.JLabel Label7;
    private javax.swing.JLabel Label8;
    private javax.swing.JLabel Label9;
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JTextField epsilonTextField;
    private javax.swing.JTextArea equationsTextArea;
    private javax.swing.JTextField intialGuessTextField;
    private javax.swing.JTextField iterationsTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField precisionTextField;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JButton solvebtn1;
    private javax.swing.JComboBox<String> subMethodeComboBox;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
