import java.util.*;

public class TextFormatter{
    
    public static String[] splitTextIntoWords(String text) {
        List<String> words = new ArrayList<>();
        int start = 0;
        boolean inWord = false;
        
        for (int i = 0; i < text.length(); i++) {
            if (Character.isWhitespace(text.charAt(i))) {
                if (inWord) {
                    words.add(text.substring(start, i));
                    inWord = false;
                }
            } else {
                if (!inWord) {
                    start = i;
                    inWord = true;
                }
            }
        }
        
        if (inWord) {
            words.add(text.substring(start));
        }
        
        return words.toArray(new String[0]);
    }
    
    public static String justifyText(String text, int width) {
        String[] words = splitTextIntoWords(text);
        StringBuilder result = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            if (currentLine.length() + word.length() > width) {
                result.append(justifyLine(currentLine.toString(), width)).append("\n");
                currentLine = new StringBuilder();
            }
            
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
        
        if (currentLine.length() > 0) {
            result.append(currentLine.toString());
        }
        
        return result.toString();
    }
    
    public static String justifyLine(String line, int width) {
        String[] words = line.split(" ");
        int totalSpaces = width - line.length() + (words.length - 1);
        int gaps = words.length - 1;
        
        if (gaps == 0) {
            return line;
        }
        
        int spacesPerGap = totalSpaces / gaps;
        int extraSpaces = totalSpaces % gaps;
        
        StringBuilder justified = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            justified.append(words[i]);
            if (i < gaps) {
                for (int j = 0; j < spacesPerGap; j++) {
                    justified.append(" ");
                }
                if (i < extraSpaces) {
                    justified.append(" ");
                }
            }
        }
        
        return justified.toString();
    }
    
    public static String centerAlignText(String text, int width) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        
        for (String line : lines) {
            int padding = (width - line.length()) / 2;
            for (int i = 0; i < padding; i++) {
                result.append(" ");
            }
            result.append(line).append("\n");
        }
        
        return result.toString();
    }
    
    public static long measurePerformance(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter text to format: ");
        String text = scanner.nextLine();
        
        System.out.print("Enter line width: ");
        int width = scanner.nextInt();
        
        // Justify text
        String justifiedText = justifyText(text, width);
        
        // Center align text
        String centeredText = centerAlignText(justifiedText, width);
        
        // Performance comparison
        long stringBuilderTime = measurePerformance(() -> {
            justifyText(text, width);
        });
        
        long stringConcatTime = measurePerformance(() -> {
            // Simulate string concatenation approach
            String[] words = splitTextIntoWords(text);
            String result = "";
            String currentLine = "";
            
            for (String word : words) {
                if (currentLine.length() + word.length() > width) {
                    result += currentLine + "\n";
                    currentLine = "";
                }
                
                if (currentLine.length() > 0) {
                    currentLine += " ";
                }
                currentLine += word;
            }
            
            if (currentLine.length() > 0) {
                result += currentLine;
            }
        });
        
        System.out.println("\n--- Original Text ---");
        System.out.println(text);
        
        System.out.println("\n--- Justified Text ---");
        String[] justifiedLines = justifiedText.split("\n");
        for (int i = 0; i < justifiedLines.length; i++) {
            System.out.printf("%2d: %s (%d characters)\n", i + 1, justifiedLines[i], justifiedLines[i].length());
        }
        
        System.out.println("\n--- Center Aligned Text ---");
        System.out.println(centeredText);
        
        System.out.println("\n--- Performance Comparison ---");
        System.out.println("StringBuilder approach: " + stringBuilderTime + " ns");
        System.out.println("String concatenation approach: " + stringConcatTime + " ns");
        System.out.printf("StringBuilder is %.2f times faster\n", (double) stringConcatTime / stringBuilderTime);
        
        scanner.close();
    }
}