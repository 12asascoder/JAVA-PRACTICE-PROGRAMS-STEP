import java.util.Scanner;

public class SpellChecker{
    public static void main(String[] args) {
        String[] dictionary = {"hello", "world", "java", "programming", "computer", "science"};
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String sentence = scanner.nextLine();
        String[] words = splitSentence(sentence);
        System.out.println("Spell Check Results:");
        System.out.println("Original Word | Suggested Correction | Distance | Status");
        for (String word : words) {
            if (word.isEmpty()) continue;
            String suggestion = findClosestMatch(word, dictionary);
            int distance = calculateDistance(word, suggestion);
            String status = distance == 0 ? "Correct" : "Misspelled";
            System.out.printf("%-13s | %-19s | %-8d | %s%n", word, suggestion, distance, status);
        }
    }

    private static String[] splitSentence(String sentence) {
        int wordCount = 0;
        for (int i = 0; i < sentence.length(); i++) {
            if (Character.isWhitespace(sentence.charAt(i))) wordCount++;
        }
        String[] words = new String[wordCount + 1];
        int start = 0, index = 0;
        for (int i = 0; i < sentence.length(); i++) {
            if (Character.isWhitespace(sentence.charAt(i))) {
                words[index++] = sentence.substring(start, i);
                start = i + 1;
            }
        }
        words[index] = sentence.substring(start);
        return words;
    }

    private static int calculateDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int commonLength = Math.min(len1, len2);
        int distance = Math.abs(len1 - len2);
        for (int i = 0; i < commonLength; i++) {
            if (word1.charAt(i) != word2.charAt(i)) distance++;
        }
        return distance;
    }

    private static String findClosestMatch(String word, String[] dictionary) {
        int minDistance = Integer.MAX_VALUE;
        String closestMatch = word;
        for (String dictWord : dictionary) {
            int distance = calculateDistance(word, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                closestMatch = dictWord;
            }
        }
        return minDistance <= 2 ? closestMatch : word;
    }
}