import java.util.*;

public class CaseConversion{
    
    public static char toUpperCase(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c - 32);
        }
        return c;
    }
    
    public static char toLowerCase(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) (c + 32);
        }
        return c;
    }
    
    public static String toTitleCase(String text) {
        if (text == null || text.isEmpty()) return text;
        
        StringBuilder result = new StringBuilder();
        boolean newWord = true;
        
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                newWord = true;
                result.append(c);
            } else if (newWord) {
                result.append(toUpperCase(c));
                newWord = false;
            } else {
                result.append(toLowerCase(c));
            }
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter text to convert: ");
        String text = scanner.nextLine();
        
        // Convert to uppercase
        StringBuilder upper = new StringBuilder();
        for (char c : text.toCharArray()) {
            upper.append(toUpperCase(c));
        }
        
        // Convert to lowercase
        StringBuilder lower = new StringBuilder();
        for (char c : text.toCharArray()) {
            lower.append(toLowerCase(c));
        }
        
        // Convert to title case
        String title = toTitleCase(text);
        
        System.out.println("\n+----------------------+----------------------+----------------------+");
        System.out.println("| Conversion Type      | Custom Implementation| Built-in Method      |");
        System.out.println("+----------------------+----------------------+----------------------+");
        System.out.printf("| Uppercase           | %-20s | %-20s |\n", upper, text.toUpperCase());
        System.out.printf("| Lowercase           | %-20s | %-20s |\n", lower, text.toLowerCase());
        System.out.printf("| Title Case          | %-20s | %-20s |\n", title, toTitleCaseBuiltIn(text));
        System.out.println("+----------------------+----------------------+----------------------+");
        
        scanner.close();
    }
    
    // Helper method for built-in title case conversion
    public static String toTitleCaseBuiltIn(String text) {
        if (text == null || text.isEmpty()) return text;
        
        StringBuilder result = new StringBuilder();
        boolean newWord = true;
        
        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                newWord = true;
                result.append(c);
            } else if (newWord) {
                result.append(Character.toUpperCase(c));
                newWord = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        
        return result.toString();
    }
}