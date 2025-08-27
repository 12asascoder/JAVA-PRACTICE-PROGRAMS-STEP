public class StringManipulation {
    public static void main(String[] args) {
        // Create strings using 3 different methods
        String str1 = "Java Programming"; // String literal
        String str2 = new String("Java Programming"); // new String() constructor
        char[] charArray = {'J', 'a', 'v', 'a', ' ', 'P', 'r', 'o', 'g', 'r', 'a', 'm', 'm', 'i', 'n', 'g'};
        String str3 = new String(charArray); // Character array

        // Compare strings
        System.out.println("str1 == str2: " + (str1 == str2)); // false (different memory references)
        System.out.println("str1.equals(str2): " + str1.equals(str2)); // true (same content)
        System.out.println("str1 == str3: " + (str1 == str3)); // false
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true

        // String with escape sequences
        String quote = "Programming Quote:\n\"Code is poetry\" - Unknown\nPath: C:\\Java\\Projects";
        System.out.println("\n" + quote);
    }
}