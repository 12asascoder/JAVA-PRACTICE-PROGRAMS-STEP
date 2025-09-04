import java.util.*;

class Subject {
    String subjectCode;
    String subjectName;
    int credits;
    String instructor;

    public Subject(String code, String name, int credits, String instructor) {
        this.subjectCode = code;
        this.subjectName = name;
        this.credits = credits;
        this.instructor = instructor;
    }
}

class Student {
    String studentId;
    String studentName;
    String className;
    String[] subjects;
    double[][] marks; // [term][subject]
    double gpa;

    static int totalStudents = 0;
    static String schoolName = "Unset School";
    static String[] gradingScale = {"A","B","C","D","F"};
    static double passPercentage = 40.0;

    private static int sidCounter = 0;

    public Student(String name, String className, String[] subjects, int terms) {
        this.studentId = "S" + String.format("%03d", ++sidCounter);
        this.studentName = name;
        this.className = className;
        this.subjects = subjects;
        this.marks = new double[terms][subjects.length];
        this.gpa = 0;
        totalStudents++;
    }

    public void addMarks(String subject, double mark) {
        int sIdx = -1;
        for (int i=0; i<subjects.length; i++) if (subjects[i].equals(subject)) { sIdx = i; break; }
        if (sIdx == -1) { System.out.println("Subject not found for " + studentName); return; }
        // Put in term 0 for simplicity
        marks[0][sIdx] = mark;
    }

    public void calculateGPA() {
        double total = 0;
        int count = 0;
        for (int t=0; t<marks.length; t++)
            for (int s=0; s<marks[t].length; s++) {
                total += marks[t][s]; count++;
            }
        double avg = count==0?0: total / count;
        // Map percentage to 0-10 scale (simple)
        this.gpa = avg / 10.0;
    }

    public String gradeFromPercent(double p) {
        if (p >= 90) return "A";
        if (p >= 75) return "B";
        if (p >= 60) return "C";
        if (p >= 40) return "D";
        return "F";
    }

    public void generateReportCard() {
        System.out.println("=== Report: " + studentName + " (" + studentId + ") - " + schoolName + " ===");
        double sum = 0;
        for (int i=0; i<subjects.length; i++) {
            double m = marks[0][i];
            sum += m;
            System.out.println(subjects[i] + ": " + m + "% [" + gradeFromPercent(m) + "]");
        }
        double avg = subjects.length==0?0: sum/subjects.length;
        calculateGPA();
        System.out.println("Average: " + String.format("%.2f", avg) + "%  | GPA: " + String.format("%.2f", gpa));
        System.out.println("Promotion Eligible: " + (avg >= passPercentage));
        System.out.println("-----------------------------------------------");
    }

    public boolean checkPromotionEligibility() {
        double sum = 0;
        for (int i=0; i<subjects.length; i++) sum += marks[0][i];
        double avg = subjects.length==0?0: sum/subjects.length;
        return avg >= passPercentage;
    }

    // Static analytics
    public static void setGradingScale(String[] scale) { gradingScale = scale; }
    public static double calculateClassAverage(Student[] students) {
        double sum = 0; int count = 0;
        for (Student s : students) if (s != null) {
            for (int i=0; i<s.subjects.length; i++) { sum += s.marks[0][i]; count++; }
        }
        return count==0?0:sum/count;
    }
    public static Student[] getTopPerformers(Student[] students, int count) {
        // rank by average
        Student[] copy = Arrays.copyOf(students, students.length);
        Arrays.sort(copy, new Comparator<Student>() {
            public int compare(Student a, Student b) {
                if (a == null && b == null) return 0;
                if (a == null) return 1;
                if (b == null) return -1;
                double aa = 0, bb = 0;
                for (double m : a.marks[0]) aa+=m;
                for (double m : b.marks[0]) bb+=m;
                aa/=a.subjects.length; bb/=b.subjects.length;
                return Double.compare(bb, aa);
            }
        });
        int n = Math.min(count, copy.length);
        Student[] top = new Student[n];
        for (int i=0; i<n; i++) top[i] = copy[i];
        return top;
    }
    public static void generateSchoolReport(Student[] students) {
        System.out.println("=== " + schoolName + " - School Report ===");
        System.out.println("Total Students: " + totalStudents);
        System.out.println("Class Average: " + String.format("%.2f", calculateClassAverage(students)) + "%");
        Student[] top = getTopPerformers(students, Math.min(3, students.length));
        System.out.println("Top Performers:");
        for (int i=0; i<top.length; i++) if (top[i] != null)
            System.out.println((i+1)+". "+top[i].studentName);
        System.out.println("-----------------------------------------");
    }
}

public class StudentGradeManagement {
    public static void main(String[] args) {
        Student.schoolName = "Greenfield High";

        String[] subs = {"Math","Science","English","History"};
        Student s1 = new Student("Meera", "10A", subs, 1);
        Student s2 = new Student("Rohan", "10A", subs, 1);
        Student s3 = new Student("Aisha", "10A", subs, 1);

        s1.addMarks("Math", 95); s1.addMarks("Science", 88); s1.addMarks("English", 92); s1.addMarks("History", 85);
        s2.addMarks("Math", 75); s2.addMarks("Science", 68); s2.addMarks("English", 70); s2.addMarks("History", 72);
        s3.addMarks("Math", 58); s3.addMarks("Science", 62); s3.addMarks("English", 65); s3.addMarks("History", 55);

        s1.generateReportCard();
        s2.generateReportCard();
        s3.generateReportCard();

        Student[] students = {s1,s2,s3};
        Student.generateSchoolReport(students);
    }
}
