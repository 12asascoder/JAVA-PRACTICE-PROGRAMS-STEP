import java.util.Scanner;

public class TextCalculator{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String expression = scanner.nextLine();
        if (!validateExpression(expression)) {
            System.out.println("Invalid expression!");
            return;
        }
        int result = evaluateExpression(expression);
        System.out.println("Result: " + result);
    }

    private static boolean validateExpression(String expression) {
        return expression.matches("[0-9+\\-*/(). ]+");
    }

    private static int evaluateExpression(String expression) {
        expression = expression.replaceAll(" ", "");
        while (expression.contains("(")) {
            int open = expression.lastIndexOf("(");
            int close = expression.indexOf(")", open);
            String subExpr = expression.substring(open + 1, close);
            int result = evaluate(subExpr);
            expression = expression.substring(0, open) + result + expression.substring(close + 1);
        }
        return evaluate(expression);
    }

    private static int evaluate(String expression) {
        expression = expression.replaceAll("[+\\-*/]", " $0 ");
        String[] tokens = expression.split(" ");
        int result = Integer.parseInt(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            int num = Integer.parseInt(tokens[i + 1]);
            switch (op) {
                case "+": result += num; break;
                case "-": result -= num; break;
                case "*": result *= num; break;
                case "/": result /= num; break;
            }
        }
        return result;
    }
}