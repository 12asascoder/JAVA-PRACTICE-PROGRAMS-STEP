import java.util.Random;
import java.util.Scanner;

public class PasswordAnalyzer{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter passwords to analyze (comma separated): ");
        String[] passwords = scanner.nextLine().split(",");
        System.out.println("Password | Length | Upper | Lower | Digits | Special | Score | Strength");
        for (String password : passwords) {
            password = password.trim();
            int upper = countUpperCase(password);
            int lower = countLowerCase(password);
            int digits = countDigits(password);
            int special = password.length() - upper - lower - digits;
            int score = calculateScore(password, upper, lower, digits, special);
            String strength = getStrength(score);
            System.out.printf("%-8s | %-6d | %-5d | %-5d | %-6d | %-7d | %-5d | %s%n",
                    password, password.length(), upper, lower, digits, special, score, strength);
        }
        System.out.print("Enter length for generated password: ");
        int length = scanner.nextInt();
        String generatedPassword = generatePassword(length);
        System.out.println("Generated Password: " + generatedPassword);
    }

    private static int countUpperCase(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (c >= 'A' && c <= 'Z') count++;
        }
        return count;
    }

    private static int countLowerCase(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (c >= 'a' && c <= 'z') count++;
        }
        return count;
    }

    private static int countDigits(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9') count++;
        }
        return count;
    }

    private static int calculateScore(String password, int upper, int lower, int digits, int special) {
        int score = 0;
        score += Math.max(0, (password.length() - 8) * 2);
        if (upper > 0) score += 10;
        if (lower > 0) score += 10;
        if (digits > 0) score += 10;
        if (special > 0) score += 10;
        if (password.contains("123") || password.contains("abc") || password.contains("qwerty")) score -= 30;
        return score;
    }

    private static String getStrength(int score) {
        if (score <= 20) return "Weak";
        else if (score <= 50) return "Medium";
        else return "Strong";
    }

    private static String generatePassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()_+";
        String all = upper + lower + digits + special;
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        for (int i = 4; i < length; i++) {
            password.append(all.charAt(random.nextInt(all.length())));
        }
        return password.toString();
    }
}