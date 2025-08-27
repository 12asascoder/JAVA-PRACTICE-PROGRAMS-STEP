import java.util.*;

public class FindReplaceSubstring{
    
    public static int[] findOccurrences(String text, String substring) {
        List<Integer> positions = new ArrayList<>();
        int index = text.indexOf(substring);
        while (index != -1) {
            positions.add(index);
            index = text.indexOf(substring, index + 1);
        }
        
        int[] result = new int[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            result[i] = positions.get(i);
        }
        return result;
    }
    
    public static String manualReplace(String text, String find, String replace) {
        StringBuilder result = new StringBuilder();
        int[] occurrences = findOccurrences(text, find);
        int lastIndex = 0;
        
        for (int pos : occurrences) {
            result.append(text, lastIndex, pos);
            result.append(replace);
            lastIndex = pos + find.length();
        }
        result.append(text.substring(lastIndex));
        
        return result.toString();
    }
    
    public static boolean compareWithBuiltIn(String customResult, String builtInResult) {
        return customResult.equals(builtInResult);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the main text: ");
        String text = scanner.nextLine();
        
        System.out.print("Enter the substring to find: ");
        String find = scanner.nextLine();
        
        System.out.print("Enter the replacement string: ");
        String replace = scanner.nextLine();
        
        String customResult = manualReplace(text, find, replace);
        String builtInResult = text.replace(find, replace);
        
        System.out.println("\nCustom replace result: " + customResult);
        System.out.println("Built-in replace result: " + builtInResult);
        System.out.println("Results match: " + compareWithBuiltIn(customResult, builtInResult));
        
        scanner.close();
    }
}