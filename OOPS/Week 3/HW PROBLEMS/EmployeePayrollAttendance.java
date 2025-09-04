import java.util.*;

class EmployeeE {
    String empId, empName, department, designation, joinDate, type;
    double baseSalary;
    boolean[] attendanceRecord; // 30 days
    static int totalEmployees = 0;
    static String companyName = "Unset Corp";
    static double totalSalaryExpense = 0;
    static int workingDaysPerMonth = 22;
    private static int eid=0;

    public EmployeeE(String name, String dept, String desig, double baseSalary, String joinDate, String type) {
        this.empId = "EMP" + String.format("%03d", ++eid);
        this.empName = name; this.department = dept; this.designation = desig;
        this.baseSalary = baseSalary; this.joinDate = joinDate; this.type = type;
        this.attendanceRecord = new boolean[30];
        totalEmployees++;
    }

    void markAttendance(int day, boolean present) {
        if (day<1 || day>30) return;
        attendanceRecord[day-1]=present;
    }

    double calculateSalary() {
        int presentDays=0; for (boolean p: attendanceRecord) if (p) presentDays++;
        double salary=0;
        if ("Full-time".equals(type)) salary = baseSalary;
        else if ("Part-time".equals(type)) salary = presentDays * (baseSalary/workingDaysPerMonth);
        else /* Contract */ salary = baseSalary; // fixed
        return salary + calculateBonus();
    }

    double calculateBonus() {
        int presentDays=0; for (boolean p: attendanceRecord) if (p) presentDays++;
        double perf = (presentDays/(double)EmployeeE.workingDaysPerMonth);
        if (perf >= 0.95) return 0.10*baseSalary;
        if (perf >= 0.85) return 0.05*baseSalary;
        return 0;
    }

    void generatePaySlip() {
        double sal = calculateSalary();
        System.out.println("Payslip: " + empName + " | " + type + " | Gross: ₹" + String.format("%.2f", sal));
        EmployeeE.totalSalaryExpense += sal;
    }

    void requestLeave() { System.out.println(empName + " requested leave."); }
}

class DepartmentD {
    String deptId, deptName;
    EmployeeE manager;
    EmployeeE[] employees;
    double budget;
    private static int did=0;

    public DepartmentD(String name, EmployeeE manager, int capacity, double budget) {
        this.deptId="D"+String.format("%03d", ++did);
        this.deptName=name;
        this.manager=manager;
        this.employees=new EmployeeE[capacity];
        this.budget=budget;
    }
}

public class EmployeePayrollAttendance {
    static double calculateCompanyPayroll(EmployeeE[] emps) {
        double sum=0; for (EmployeeE e: emps) if (e!=null) sum+=e.calculateSalary();
        return sum;
    }
    static double[] getDepartmentWiseExpenses(DepartmentD[] depts) {
        double[] out = new double[depts.length];
        for (int i=0; i<depts.length; i++) {
            double sum=0;
            if (depts[i]!=null && depts[i].employees!=null)
                for (EmployeeE e: depts[i].employees) if (e!=null) sum+=e.calculateSalary();
            out[i]=sum;
        }
        return out;
    }
    static void getAttendanceReport(EmployeeE[] emps) {
        for (EmployeeE e: emps) if (e!=null) {
            int present=0; for (boolean p: e.attendanceRecord) if (p) present++;
            System.out.println(e.empName + ": " + present + "/30 present");
        }
    }

    public static void main(String[] args) {
        EmployeeE.companyName = "Orion Systems";
        EmployeeE.workingDaysPerMonth = 22;

        EmployeeE e1 = new EmployeeE("Tara","IT","Engineer",60000,"2023-02-01","Full-time");
        EmployeeE e2 = new EmployeeE("Vikram","IT","Analyst",40000,"2024-06-01","Part-time");
        EmployeeE e3 = new EmployeeE("Sana","HR","Consultant",50000,"2025-01-15","Contract");

        // Mark attendance
        for (int d=1; d<=22; d++) { e1.markAttendance(d,true); e2.markAttendance(d, d%2==0); e3.markAttendance(d,true); }

        DepartmentD it = new DepartmentD("IT", e1, 5, 500000);
        it.employees[0]=e1; it.employees[1]=e2;
        DepartmentD hr = new DepartmentD("HR", e3, 3, 300000);
        hr.employees[0]=e3;

        EmployeeE[] all = {e1,e2,e3};
        for (EmployeeE e: all) e.generatePaySlip();

        System.out.println("Company Payroll: ₹" + String.format("%.2f", calculateCompanyPayroll(all)));
        double[] deptExp = getDepartmentWiseExpenses(new DepartmentD[]{it,hr});
        System.out.println("Dept Expenses IT/HR: ₹" + String.format("%.2f", deptExp[0]) + " / ₹" + String.format("%.2f", deptExp[1]));
        getAttendanceReport(all);
    }
}
