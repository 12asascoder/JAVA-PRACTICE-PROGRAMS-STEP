import java.util.Scanner;

public class ASCIIProcessor{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();
        
        System.out.println("Character Analysis:");
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int ascii = (int) ch;
            String type = classifyCharacter(ch);
            System.out.println("Character: '" + ch + "' ASCII: " + ascii + " Type: " + type);
            
            if (Character.isLetter(ch)) {
                char upper = Character.toUpperCase(ch);
                char lower = Character.toLowerCase(ch);
                int upperAscii = (int) upper;
                int lowerAscii = (int) lower;
                System.out.println("  Upper case: '" + upper + "' ASCII: " + upperAscii);
                System.out.println("  Lower case: '" + lower + "' ASCII: " + lowerAscii);
                System.out.println("  Difference between upper and lower: " + Math.abs(upperAscii - lowerAscii));
            }
            System.out.println();
        }
        
        // ASCII art
        System.out.println("ASCII Art:");
        displayASCIITable(65, 70);
        
        // Caesar cipher
        String cipherText = caesarCipher(input, 3);
        System.out.println("Caesar cipher (shift 3): " + cipherText);
        
        scanner.close();
    }
    
    public static String classifyCharacter(char ch) {
        if (Character.isUpperCase(ch)) return "Uppercase Letter";
        if (Character.isLowerCase(ch)) return "Lowercase Letter";
        if (Character.isDigit(ch)) return "Digit";
        return "Special Character";
    }
    
    public static char toggleCase(char ch) {
        if (Character.isUpperCase(ch)) return Character.toLowerCase(ch);
        if (Character.isLowerCase(ch)) return Character.toUpperCase(ch);
        return ch;
    }
    
    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + shift) % 26 + base);
            }
            result.append(ch);
        }
        return result.toString();
    }
    
    public static void displayASCIITable(int start, int end) {
        for (int i = start; i <= end; i++) {
            System.out.println("ASCII " + i + ": " + (char) i);
        }
    }
    
    public static int[] stringToASCII(String text) {
        int[] asciiValues = new int[text.length()];
        for (int i = 0; i < text.length(); i++) {
            asciiValues[i] = (int) text.charAt(i);
        }
        return asciiValues;
    }
    
    public static String asciiToString(int[] asciiValues) {
        StringBuilder result = new StringBuilder();
        for (int value : asciiValues) {
            result.append((char) value);
        }
        return result.toString();
    }
}