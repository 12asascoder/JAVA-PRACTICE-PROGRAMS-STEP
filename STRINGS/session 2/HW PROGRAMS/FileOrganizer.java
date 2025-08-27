import java.util.Scanner;

public class FileOrganizer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file names (comma separated): ");
        String[] files = scanner.nextLine().split(",");
        System.out.println("Original Name | Category | Suggested Name");
        for (String file : files) {
            file = file.trim();
            String ext = getExtension(file);
            String category = getCategory(ext);
            String newName = generateNewName(file, category);
            System.out.printf("%-13s | %-8s | %s%n", file, category, newName);
        }
    }

    private static String getExtension(String file) {
        int dotIndex = file.lastIndexOf(".");
        return dotIndex == -1 ? "" : file.substring(dotIndex + 1);
    }

    private static String getCategory(String ext) {
        switch (ext) {
            case "txt": case "doc": return "Document";
            case "jpg": case "png": return "Image";
            case "java": case "cpp": return "Code";
            default: return "Unknown";
        }
    }

    private static String generateNewName(String file, String category) {
        String base = file.substring(0, file.lastIndexOf("."));
        return category + "_" + base + "_20231010." + getExtension(file);
    }
}