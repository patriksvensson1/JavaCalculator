import java.util.*;

public class Compute {
    public static String evaluate(String expression) {
        //Stacks to hold numberButton and operators
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < expression.length()) {
            char ch = expression.charAt(i);
            //Add '*' between digit and '(' to indicate multiplication
            if (ch == '(') {
                if (i > 0 && Character.isDigit(expression.charAt(i - 1))) {
                    operators.push('*');
                }
                operators.push(ch);
            }

            // If ch == ')', evaluate operands within until corresponding '('
            else if (ch == ')') {
                while (operators.peek() != '(') {
                    char operator = operators.pop();
                    double b = operands.pop();
                    double a = operands.pop();
                    operands.push(calculate(a, b, operator));
                }
                operators.pop(); // Remove '('
            }

            else if (Character.isDigit(ch) || (ch == '-' && (i == 0 || expression.charAt(i - 1) == '(' || expression.charAt(i - 1) == '-'))) {
                StringBuilder num = new StringBuilder();
                //Handle negative numberButton
                if (ch == '-') {
                    num.append('-');
                    i++;
                }
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                operands.push(Double.parseDouble(num.toString()));
                //To ensure that the main loop continues from the correct index
                i--;
            }

            else {
                //Evaluates while stack is not empty & top of stack precedence >= current operator
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(ch)) {
                    char operator = operators.pop();
                    double b = operands.pop();
                    double a = operands.pop();
                    operands.push(calculate(a, b, operator));
                }
                operators.push(ch);
            }
            i++;
        }

        //Finalizing the evaluation
        while (!operators.isEmpty()) {
            char operator = operators.pop();
            double b = operands.pop();  //b before a, as .pop() pops the last element in stack
            double a = operands.pop();
            operands.push(calculate(a, b, operator));
        }

        double result = operands.pop();

        if (result % 1 == 0)
            return String.valueOf((int)result);
        else
            return String.valueOf(result);
    }
    private static double calculate(double a, double b, char operator) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '^' -> Math.pow(a, b);
            default -> 0;
        };
    }

    private static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }
}
