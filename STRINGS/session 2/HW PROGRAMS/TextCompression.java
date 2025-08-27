import java.util.Scanner;

public class TextCompression{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text to compress: ");
        String text = scanner.nextLine();
        char[] chars = getUniqueChars(text);
        int[] freqs = getFrequencies(text, chars);
        String[] codes = generateCodes(chars, freqs);
        String compressed = compressText(text, codes, chars);
        String decompressed = decompressText(compressed, codes, chars);
        double ratio = (double) compressed.length() / text.length() * 100;
        System.out.println("Original Text: " + text);
        System.out.println("Compressed Text: " + compressed);
        System.out.println("Decompressed Text: " + decompressed);
        System.out.printf("Compression Ratio: %.2f%%%n", ratio);
        System.out.println("Char | Frequency | Code");
        for (int i = 0; i < chars.length; i++) {
            System.out.printf("%-4c | %-9d | %s%n", chars[i], freqs[i], codes[i]);
        }
    }

    private static char[] getUniqueChars(String text) {
        StringBuilder unique = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (unique.toString().indexOf(c) == -1) {
                unique.append(c);
            }
        }
        return unique.toString().toCharArray();
    }

    private static int[] getFrequencies(String text, char[] chars) {
        int[] freqs = new int[chars.length];
        for (char c : text.toCharArray()) {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == c) freqs[i]++;
            }
        }
        return freqs;
    }

    private static String[] generateCodes(char[] chars, int[] freqs) {
        String[] codes = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            codes[i] = Integer.toBinaryString(i);
        }
        return codes;
    }

    private static String compressText(String text, String[] codes, char[] chars) {
        StringBuilder compressed = new StringBuilder();
        for (char c : text.toCharArray()) {
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == c) {
                    compressed.append(codes[i]);
                    break;
                }
            }
        }
        return compressed.toString();
    }

    private static String decompressText(String compressed, String[] codes, char[] chars) {
        StringBuilder decompressed = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        for (char bit : compressed.toCharArray()) {
            currentCode.append(bit);
            for (int i = 0; i < codes.length; i++) {
                if (codes[i].equals(currentCode.toString())) {
                    decompressed.append(chars[i]);
                    currentCode.setLength(0);
                    break;
                }
            }
        }
        return decompressed.toString();
    }
}