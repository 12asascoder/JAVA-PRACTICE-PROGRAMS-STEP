public class StringPerformanceComparison{
    public static void main(String[] args) {
        System.out.println("=== PERFORMANCE COMPARISON ===");
        int iterations = 1000;
        
        // Test String concatenation
        long startTime = System.nanoTime();
        String result1 = concatenateWithString(iterations);
        long endTime = System.nanoTime();
        System.out.println("String concatenation time: " + (endTime - startTime) + " ns");
        
        // Test StringBuilder concatenation
        startTime = System.nanoTime();
        String result2 = concatenateWithStringBuilder(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuilder concatenation time: " + (endTime - startTime) + " ns");
        
        // Test StringBuffer concatenation
        startTime = System.nanoTime();
        String result3 = concatenateWithStringBuffer(iterations);
        endTime = System.nanoTime();
        System.out.println("StringBuffer concatenation time: " + (endTime - startTime) + " ns");
        
        // Demonstrate StringBuilder methods
        demonstrateStringBuilderMethods();
        
        // Compare string comparison methods
        compareStringComparisonMethods();
    }
    
    public static String concatenateWithString(int iterations) {
        String result = "";
        for (int i = 0; i < iterations; i++) {
            result += "Java " + i + " ";
        }
        return result;
    }
    
    public static String concatenateWithStringBuilder(int iterations) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }
    
    public static String concatenateWithStringBuffer(int iterations) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sb.append("Java ").append(i).append(" ");
        }
        return sb.toString();
    }
    
    public static void demonstrateStringBuilderMethods() {
        System.out.println("\n=== StringBuilder Methods ===");
        StringBuilder sb = new StringBuilder("Hello World");
        System.out.println("Original: " + sb);
        
        sb.append("!");
        System.out.println("After append: " + sb);
        
        sb.insert(5, " Java");
        System.out.println("After insert: " + sb);
        
        sb.delete(5, 10);
        System.out.println("After delete: " + sb);
        
        sb.deleteCharAt(5);
        System.out.println("After deleteCharAt: " + sb);
        
        sb.reverse();
        System.out.println("After reverse: " + sb);
        sb.reverse(); // reverse back
        
        sb.replace(0, 5, "Hi");
        System.out.println("After replace: " + sb);
        
        sb.setCharAt(1, 'a');
        System.out.println("After setCharAt: " + sb);
        
        System.out.println("Capacity: " + sb.capacity());
        sb.ensureCapacity(50);
        System.out.println("Capacity after ensureCapacity: " + sb.capacity());
        sb.trimToSize();
        System.out.println("Capacity after trimToSize: " + sb.capacity());
    }
    
    public static void compareStringComparisonMethods() {
        System.out.println("\n=== String Comparison Methods ===");
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");
        String str4 = "hello";
        
        System.out.println("str1 == str2: " + (str1 == str2)); // true, same reference
        System.out.println("str1 == str3: " + (str1 == str3)); // false, different reference
        System.out.println("str1.equals(str2): " + str1.equals(str2)); // true, same content
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true, same content
        System.out.println("str1.equals(str4): " + str1.equals(str4)); // false, case different
        System.out.println("str1.equalsIgnoreCase(str4): " + str1.equalsIgnoreCase(str4)); // true
        System.out.println("str1.compareTo(str2): " + str1.compareTo(str2)); // 0
        System.out.println("str1.compareTo(str4): " + str1.compareTo(str4)); // negative
        System.out.println("str1.compareToIgnoreCase(str4): " + str1.compareToIgnoreCase(str4)); // 0
    }
}