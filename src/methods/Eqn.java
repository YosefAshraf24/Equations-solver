package methods;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eqn {

    private final String variables = "xyzabcdefg";

    String match(String regexp, String text, int group) {
        Pattern p1 = Pattern.compile(regexp);
        Matcher m1 = p1.matcher(text);
        if (m1.find()) {
            return m1.group(group);
        } else {
            return null;
        }
    }

    /*
     * Class that represents a single member inside an equation
     */
    public class EquationMember {

        public float constant;
        public String variable;

        public String toString() {
            return constant + "" + variable;
        }
    }

    /*
     * Class that represents an entire equation
     */
    public ArrayList<EquationMember> leftMembers;
    public ArrayList<EquationMember> rightMembers;

    Eqn(String eq) {
        leftMembers = new ArrayList<>();
        rightMembers = new ArrayList<>();
        String equationString = eq.replace(" ", "");
        if (equationString.contains(",")) {
            String[] values = equationString.split(",");
            int i;
            for (i = 0; i < values.length - 1; i++) {
                EquationMember m = new EquationMember();
                m.constant = Float.parseFloat(values[i]);
                m.variable = this.variables.charAt(i) + "";
                this.leftMembers.add(m);
            }
            EquationMember m = new EquationMember();
            m.constant = Float.parseFloat(values[i]);
            m.variable = "";
            this.rightMembers.add(m);
        } else {
            if (equationString.charAt(0) == '-' && (equationString.charAt(1) < '0' || equationString.charAt(1) > '9')) {
                equationString = equationString.substring(0, 1) + "1" + equationString.substring(1, equationString.length());
            } else if ((equationString.charAt(0) < '0' || equationString.charAt(0) > '9') && equationString.charAt(0) != '-') {
                equationString = "1" + equationString;
            }

            for (int i = 0; i < equationString.length() - 1; i++) {//handle the case of x+-y  and x--y
                if ((equationString.charAt(i) == '+') && (equationString.charAt(i + 1) == '-')) {
                    equationString = equationString.substring(0, i) + equationString.substring(i + 1, equationString.length());
                }
                if ((equationString.charAt(i) == '-') && (equationString.charAt(i + 1) == '-')) {
                    equationString = equationString.substring(0, i) + "+" + equationString.substring(i + 2, equationString.length());
                }
            }
            for (int i = 0; i < equationString.length() - 1; i++) {//handle cases of -x to -1x
                if ((equationString.charAt(i) == '+' || equationString.charAt(i) == '-') && (equationString.charAt(i + 1) < '0' || equationString.charAt(i + 1) > '9')) {
                    equationString = equationString.substring(0, i + 1) + "1" + equationString.substring(i + 1, equationString.length());
                }
            }
            for (int i = 0; i < equationString.length() - 1; i++) {//handle cases of sin(x) to sin(1x)
                if ((equationString.charAt(i) == '(') && (equationString.charAt(i + 1) < '0' || equationString.charAt(i + 1) > '9') && equationString.charAt(i + 1) != '-' ) {
                    equationString = equationString.substring(0, i + 1) + "1" + equationString.substring(i + 1, equationString.length());
                }
            }
            char[] charArray = equationString.toCharArray();
            boolean isLeft = true;
            String lastMember = "";
            boolean repeated;
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '-' || charArray[i] == '+' || charArray[i] == '=') {
                    repeated = false;
                    if (lastMember.length() > 0) {
                        EquationMember m = new EquationMember();
                        m.constant = Float.parseFloat(match("([-+]?[\\d\\.]+)[A-Za-z]?", lastMember, 1));
                        m.variable = match("[\\d\\.]+([A-Za-z]?)", lastMember, 1);

                        if ((lastMember.indexOf(m.variable) != lastMember.length() - 1) && (lastMember.charAt(lastMember.indexOf(m.variable) + 1) + "").matches("-?\\d+(\\.\\d+)?") && !m.variable.equals("")) {
                            m.variable += lastMember.charAt(lastMember.indexOf(m.variable) + 1);//for x1 , x2 or y6
                        }

                        if (lastMember.contains("sin")) {
                            m.variable = lastMember.substring(lastMember.indexOf("s"));
                        } else if (lastMember.contains("cos")) {
                            m.variable = lastMember.substring(lastMember.indexOf("c"));
                        } else if (lastMember.contains("exp")) {
                            m.variable = lastMember.substring(lastMember.indexOf("e"));
                        } else if (lastMember.contains("^")) {
                            m.variable += lastMember.substring(lastMember.indexOf("^"));
                        }
                        if (lastMember.indexOf("^") == lastMember.length() - 1) {
                            m.variable += charArray[i];
                            i++;
                            while (charArray[i] != '-' && charArray[i] != '+' && charArray[i] != '=') {
                                m.variable += charArray[i];
                                i++;
                            }

                        }else if(lastMember.indexOf("(") == lastMember.length() - 1){
                            m.variable += charArray[i];
                            i++;
                            while (charArray[i] != ')') {
                                m.variable += charArray[i];
                                i++;
                            }
                            m.variable+=")";
                            i++;
                        }

                        for (EquationMember x : leftMembers) {
                            if (x.variable.equals(m.variable)) {
                                x.constant += m.constant;
                                repeated = true;
                            }
                        }
                        if (isLeft) {
                            if (!repeated) {
                                this.leftMembers.add(m);
                            }
                        } else {
                            this.rightMembers.add(m);
                        }
                    }
                    lastMember = charArray[i] == '=' ? "" : String.valueOf(charArray[i]);
                } else {
                    lastMember += charArray[i];
                }

                if (charArray[i] == '=') {
                    isLeft = false;
                }
            }

            EquationMember m = new EquationMember();
            m.constant = Float.parseFloat(match("([-+]?[\\d\\.]+)[A-Za-z]?", lastMember, 1));
            m.variable = match("[\\d\\.]+([A-Za-z]?)", lastMember, 1);
            this.rightMembers.add(m);
        }

    }

}
