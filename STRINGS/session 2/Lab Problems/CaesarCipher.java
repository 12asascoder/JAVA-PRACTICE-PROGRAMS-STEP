import java.util.*;

public class CaesarCipher{
    
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                c = (char) ((c - base + shift) % 26 + base);
            }
            result.append(c);
        }
        
        return result.toString();
    }
    
    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26));
    }
    
    public static void displayASCIIValues(String text, String description) {
        System.out.println(description + ":");
        for (char c : text.toCharArray()) {
            System.out.printf("'%c' = %d | ", c, (int) c);
        }
        System.out.println("\n");
    }
    
    public static boolean validateDecryption(String original, String decrypted) {
        return original.equals(decrypted);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter text to encrypt: ");
        String text = scanner.nextLine();
        
        System.out.print("Enter shift value: ");
        int shift = scanner.nextInt();
        
        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);
        
        System.out.println("\n--- ASCII Values ---");
        displayASCIIValues(text, "Original text");
        displayASCIIValues(encrypted, "Encrypted text");
        displayASCIIValues(decrypted, "Decrypted text");
        
        System.out.println("Original text: " + text);
        System.out.println("Encrypted text: " + encrypted);
        System.out.println("Decrypted text: " + decrypted);
        System.out.println("Decryption successful: " + validateDecryption(text, decrypted));
        
        scanner.close();
    }
}