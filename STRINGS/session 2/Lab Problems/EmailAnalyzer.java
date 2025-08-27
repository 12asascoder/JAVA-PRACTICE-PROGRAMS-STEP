import java.util.*;

public class EmailAnalyzer{
    
    public static boolean isValidEmail(String email) {
        int atIndex = email.indexOf('@');
        int dotIndex = email.lastIndexOf('.');
        
        return atIndex > 0 && 
               dotIndex > atIndex + 1 && 
               dotIndex < email.length() - 1;
    }
    
    public static String extractUsername(String email) {
        int atIndex = email.indexOf('@');
        return atIndex != -1 ? email.substring(0, atIndex) : "";
    }
    
    public static String extractDomain(String email) {
        int atIndex = email.indexOf('@');
        return atIndex != -1 ? email.substring(atIndex + 1) : "";
    }
    
    public static String extractDomainName(String domain) {
        int dotIndex = domain.lastIndexOf('.');
        return dotIndex != -1 ? domain.substring(0, dotIndex) : domain;
    }
    
    public static String extractExtension(String domain) {
        int dotIndex = domain.lastIndexOf('.');
        return dotIndex != -1 ? domain.substring(dotIndex + 1) : "";
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter email addresses (comma separated): ");
        String input = scanner.nextLine();
        String[] emails = input.split(",");
        
        int validCount = 0;
        int invalidCount = 0;
        Map<String, Integer> domainCount = new HashMap<>();
        int totalUsernameLength = 0;
        
        System.out.println("\n+----------------------+----------------------+----------------------+----------------------+----------------------+");
        System.out.println("| Email                | Username             | Domain               | Domain Name          | Extension            | Valid |");
        System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+-------+");
        
        for (String email : emails) {
            email = email.trim();
            boolean valid = isValidEmail(email);
            if (valid) validCount++;
            else invalidCount++;
            
            String username = extractUsername(email);
            String domain = extractDomain(email);
            String domainName = extractDomainName(domain);
            String extension = extractExtension(domain);
            
            // Update domain count
            domainCount.put(domain, domainCount.getOrDefault(domain, 0) + 1);
            
            // Update username length total
            totalUsernameLength += username.length();
            
            System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-5s |\n", 
                             email, username, domain, domainName, extension, valid ? "Yes" : "No");
        }
        
        System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+-------+");
        
        // Find most common domain
        String mostCommonDomain = "";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : domainCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonDomain = entry.getKey();
            }
        }
        
        // Calculate average username length
        double avgUsernameLength = validCount > 0 ? (double) totalUsernameLength / validCount : 0;
        
        System.out.println("\n--- Email Statistics ---");
        System.out.println("Total emails: " + emails.length);
        System.out.println("Valid emails: " + validCount);
        System.out.println("Invalid emails: " + invalidCount);
        System.out.println("Most common domain: " + mostCommonDomain + " (" + maxCount + " occurrences)");
        System.out.printf("Average username length: %.2f\n", avgUsernameLength);
        
        scanner.close();
    }
}