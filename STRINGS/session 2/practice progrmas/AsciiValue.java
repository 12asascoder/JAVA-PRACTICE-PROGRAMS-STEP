package session2;

import java.util.Scanner;
import java.util.logging.Logger;

public class AsciiValue{
    private static final Logger logger = Logger.getLogger(AsciiValue.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        logger.info("Enter a character: ");
        char ch = scanner.next().charAt(0);
        int ascii = ch;
        logger.info(String.format("ASCII value of '%c' is: %d", ch, ascii));
        scanner.close();
    }
}