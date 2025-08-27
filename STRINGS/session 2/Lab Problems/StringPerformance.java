import java.util.*;

public class StringPerformance{
    
    public static long[] testStringConcatenation(int iterations) {
        long startTime = System.currentTimeMillis();
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "test";
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, result.length()};
    }
    
    public static long[] testStringBuilder(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            result.append("test");
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, result.length()};
    }
    
    public static long[] testStringBuffer(int iterations) {
        long startTime = System.currentTimeMillis();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            result.append("test");
        }
        long endTime = System.currentTimeMillis();
        return new long[]{endTime - startTime, result.length()};
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter number of iterations (e.g., 1000, 10000, 100000): ");
        int iterations = scanner.nextInt();
        
        long[] stringResults = testStringConcatenation(iterations);
        long[] builderResults = testStringBuilder(iterations);
        long[] bufferResults = testStringBuffer(iterations);
        
        System.out.println("\n+----------------------+----------------+-----------------+");
        System.out.println("| Method               | Time (ms)      | Final Length    |");
        System.out.println("+----------------------+----------------+-----------------+");
        System.out.printf("| String Concatenation | %-14d | %-15d |\n", stringResults[0], stringResults[1]);
        System.out.printf("| StringBuilder        | %-14d | %-15d |\n", builderResults[0], builderResults[1]);
        System.out.printf("| StringBuffer         | %-14d | %-15d |\n", bufferResults[0], bufferResults[1]);
        System.out.println("+----------------------+----------------+-----------------+");
        
        scanner.close();
    }
}