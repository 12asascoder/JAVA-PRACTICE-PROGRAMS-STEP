public class Student {
    private String studentId;
    private String name;
    private double grade;
    private String course;

    public Student() {}

    public Student(String studentId, String name, double grade, String course) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.course = course;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String calculateLetterGrade() {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public void displayStudent() {
        System.out.println("Student ID: " + studentId + ", Name: " + name + ", Grade: " + grade +
                " (" + calculateLetterGrade() + "), Course: " + course);
    }

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setStudentId("S001");
        s1.setName("Alice");
        s1.setGrade(85);
        s1.setCourse("Mathematics");

        Student s2 = new Student("S002", "Bob", 92, "Physics");

        s1.displayStudent();
        s2.displayStudent();
    }
}