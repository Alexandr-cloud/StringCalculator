package TaskKata;
import java.util.Scanner;

public class StringCalculator {
    public static void main(String[] args) throws Exception {
        System.out.println("Введите выражение");

        Scanner scanner = new Scanner(System.in);
        String exp = scanner.nextLine();

        int actionIndex = findFirstNonQuotedOperatorIndex(exp);

        if (actionIndex != -1) {
            String data1 = exp.substring(0, actionIndex).trim();
            char action = exp.charAt(actionIndex);
            String data2 = exp.substring(actionIndex + 1).trim();

            if (action == '*' || action == '/') {
                if (data2.contains("\"")) {
                    throw new Exception("Строку можно делить или умножать только на число");
                }
            }

            if (data1.startsWith("\"") && data1.endsWith("\"")) {
                data1 = data1.substring(1, data1.length() - 1); // удаляем кавычки
            }
            if (data2.startsWith("\"") && data2.endsWith("\"")) {
                data2 = data2.substring(1, data2.length() - 1); // удаляем кавычки
            }

            if (data1.length() > 10 || data2.length() > 10) {
                throw new Exception("Введено больше 10 символов");
            }

            switch (action) {
                case '+':
                    printInQuotes(data1 + data2);
                    break;
                case '*':
                    int multiplier = Integer.parseInt(data2);
                    if (multiplier < 1 || multiplier > 10) {
                        throw new Exception("Число для умножения должно быть от 1 до 10");
                    }
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < multiplier; i++) {
                        result.append(data1);
                    }
                    printInQuotes(result.toString());
                    break;

                case '-':
                    int index = data1.indexOf(data2);
                    String resultStr = (index == -1) ? data1 : data1.substring(0, index) + data1.substring(index + data2.length());
                    printInQuotes(resultStr);
                    break;
                case '/':
                    int divisor = Integer.parseInt(data2);
                    if (divisor < 1 || divisor > 10) {
                        throw new Exception("Число для деления должно быть от 1 до 10");
                    }
                    int newLen = data1.length() / divisor;
                    printInQuotes(data1.substring(0, newLen));
                    break;
            }
        } else {
            throw new Exception("Некорректный формат выражения");
        }
    }

    static void printInQuotes(String text) {

        if (text.length() >= 40) {
            System.out.println(text.substring(0, 40) + "...");
        } else {
            System.out.print(text);
        }
    }
    static int findFirstNonQuotedOperatorIndex(String exp) {
        boolean inQuotes = false;
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (!inQuotes && (c == '+' || c == '-' || c == '*' || c == '/')) {
                return i;
            }
        }
        return -1;
    }
}
