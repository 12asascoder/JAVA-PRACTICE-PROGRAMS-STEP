import java.util.Scanner;

public class CSVAnalyzer{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CSV data (end with empty line):");
        StringBuilder csvData = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            csvData.append(line).append("\n");
        }
        String[][] data = parseCSV(csvData.toString());
        printTable(data);
    }

    private static String[][] parseCSV(String csvData) {
        int rows = csvData.split("\n").length;
        int cols = csvData.split("\n")[0].split(",").length;
        String[][] data = new String[rows][cols];
        int row = 0;
        int start = 0;
        for (int i = 0; i < csvData.length(); i++) {
            if (csvData.charAt(i) == '\n') {
                String line = csvData.substring(start, i);
                String[] fields = splitLine(line);
                System.arraycopy(fields, 0, data[row], 0, fields.length);
                start = i + 1;
                row++;
            }
        }
        return data;
    }

    private static String[] splitLine(String line) {
        int fields = 1;
        for (char c : line.toCharArray()) {
            if (c == ',') fields++;
        }
        String[] result = new String[fields];
        int field = 0;
        int start = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ',') {
                result[field++] = line.substring(start, i);
                start = i + 1;
            }
        }
        result[field] = line.substring(start);
        return result;
    }

    private static void printTable(String[][] data) {
        for (String[] row : data) {
            for (String field : row) {
                System.out.printf("%-15s", field);
            }
            System.out.println();
        }
    }
}