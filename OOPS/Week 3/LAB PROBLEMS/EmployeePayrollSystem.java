public class EmployeePayrollSystem {
    private String empId;
    private String empName;
    private String department;
    private double baseSalary;
    private String empType;
    private static int totalEmployees = 0;
    private static int counter = 0;

    public EmployeePayrollSystem(String empName, String department, double baseSalary, String empType) {
        this.empId = generateEmpId();
        this.empName = empName;
        this.department = department;
        this.baseSalary = baseSalary;
        this.empType = empType;
        totalEmployees++;
    }

    private static String generateEmpId() {
        counter++;
        return "E" + String.format("%03d", counter);
    }

    public double calculateSalary(double bonus) { return baseSalary + bonus; }
    public double calculateSalary(int hours, double rate) { return hours * rate; }
    public double calculateSalary() { return baseSalary; }

    public double calculateTax(double salary) {
        if (empType.equals("Full-Time")) return salary * 0.2;
        else if (empType.equals("Part-Time")) return salary * 0.1;
        else return salary * 0.15;
    }

    public void generatePaySlip(double salary) {
        double tax = calculateTax(salary);
        System.out.println("PaySlip for " + empName + " | Salary: " + salary + " | Tax: " + tax + " | Net: " + (salary - tax));
    }

    public void displayEmployeeInfo() {
        System.out.println("EmpID: " + empId + ", Name: " + empName + ", Dept: " + department + ", Type: " + empType);
    }

    public static int getTotalEmployees() { return totalEmployees; }

    public static void main(String[] args) {
        EmployeePayrollSystem e1 = new EmployeePayrollSystem("Alice", "IT", 50000, "Full-Time");
        EmployeePayrollSystem e2 = new EmployeePayrollSystem("Bob", "HR", 200, "Part-Time");
        EmployeePayrollSystem e3 = new EmployeePayrollSystem("Charlie", "Finance", 30000, "Contract");

        e1.displayEmployeeInfo();
        double sal1 = e1.calculateSalary(5000);
        e1.generatePaySlip(sal1);

        e2.displayEmployeeInfo();
        double sal2 = e2.calculateSalary(20, 200);
        e2.generatePaySlip(sal2);

        e3.displayEmployeeInfo();
        double sal3 = e3.calculateSalary();
        e3.generatePaySlip(sal3);

        System.out.println("Total Employees: " + EmployeePayrollSystem.getTotalEmployees());
    }
}