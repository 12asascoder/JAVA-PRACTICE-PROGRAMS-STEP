import java.util.*;

class Patient {
    String patientId, patientName, gender, contactInfo;
    int age;
    String[] medicalHistory;
    String[] currentTreatments;
    private static int pid=0;
    static int totalPatients = 0;

    public Patient(String name, int age, String gender, String contact, int histCap, int treatCap) {
        this.patientId = "PT" + String.format("%03d", ++pid);
        this.patientName=name; this.age=age; this.gender=gender; this.contactInfo=contact;
        this.medicalHistory = new String[histCap];
        this.currentTreatments = new String[treatCap];
        totalPatients++;
    }

    void updateTreatment(String treatment) {
        for (int i=0; i<currentTreatments.length; i++) if (currentTreatments[i]==null){ currentTreatments[i]=treatment; return; }
    }
    void dischargePatient() { Arrays.fill(currentTreatments, null); }
}

class Doctor {
    String doctorId, doctorName, specialization;
    String[] availableSlots;
    int patientsHandled;
    double consultationFee;
    private static int did=0;

    public Doctor(String name, String specialization, double fee, String[] slots) {
        this.doctorId="DC"+String.format("%03d",++did);
        this.doctorName=name; this.specialization=specialization; this.consultationFee=fee;
        this.availableSlots=slots; this.patientsHandled=0;
    }
}

class Appointment {
    String appointmentId;
    Patient patient;
    Doctor doctor;
    String appointmentDate;
    String appointmentTime;
    String status; // Scheduled, Cancelled, Completed
    private static int aid=0;

    static int totalAppointments = 0;
    static String hospitalName = "Unset Hospital";
    static double totalRevenue = 0;

    public Appointment(Patient p, Doctor d, String date, String time) {
        this.appointmentId="AP"+String.format("%04d",++aid);
        this.patient=p; this.doctor=d; this.appointmentDate=date; this.appointmentTime=time;
        this.status="Scheduled";
        totalAppointments++;
    }
}

public class HospitalManagementSystem {
    static Appointment scheduleAppointment(Patient p, Doctor d, String date, String time) {
        // Simple slot check
        boolean ok=false;
        for (String s: d.availableSlots) if (s!=null && s.equals(time)) { ok=true; break; }
        if (!ok) { System.out.println("Slot unavailable."); return null; }
        Appointment ap = new Appointment(p,d,date,time);
        d.patientsHandled++;
        System.out.println("Scheduled " + ap.appointmentId + " with Dr. " + d.doctorName + " for " + p.patientName);
        return ap;
    }
    static void cancelAppointment(Appointment ap) {
        if (ap==null) return;
        ap.status="Cancelled";
        System.out.println("Cancelled " + ap.appointmentId);
    }
    static double generateBill(Appointment ap, String type) {
        double factor = "Emergency".equals(type)?1.5 : ("Follow-up".equals(type)?0.5:1.0);
        double amt = ap.doctor.consultationFee * factor;
        Appointment.totalRevenue += amt;
        ap.status="Completed";
        return amt;
    }
    static void generateHospitalReport(Doctor[] docs) {
        System.out.println("=== " + Appointment.hospitalName + " Report ===");
        System.out.println("Patients: " + Patient.totalPatients + " | Appointments: " + Appointment.totalAppointments);
        System.out.println("Revenue: ₹" + String.format("%.2f", Appointment.totalRevenue));
        for (Doctor d: docs) if (d!=null) System.out.println("Dr. " + d.doctorName + " handled: " + d.patientsHandled);
    }
    static double getDoctorUtilization(Doctor d) {
        int slots = 0; for (String s: d.availableSlots) if (s!=null) slots++;
        return slots==0?0:(d.patientsHandled*100.0/slots);
    }
    static void getPatientStatistics(Patient[] patients) {
        System.out.println("Total Patients: " + patients.length);
        int seniors=0; for (Patient p: patients) if (p.age>=60) seniors++;
        System.out.println("Seniors (60+): " + seniors);
    }

    public static void main(String[] args) {
        Appointment.hospitalName = "Sunrise Hospital";
        String[] slots = {"10:00","10:30","11:00","11:30"};
        Doctor d1 = new Doctor("Seema","Cardiology",800, slots);
        Doctor d2 = new Doctor("Ajay","Dermatology",600, new String[]{"12:00","12:30","13:00"});

        Patient p1 = new Patient("Rahul", 45, "M", "999-111", 10, 5);
        Patient p2 = new Patient("Priya", 29, "F", "999-222", 10, 5);

        Appointment a1 = scheduleAppointment(p1,d1,"2025-09-04","10:00");
        Appointment a2 = scheduleAppointment(p2,d2,"2025-09-04","12:00");

        p1.updateTreatment("Statins");
        p2.updateTreatment("Ointment");

        double bill1 = generateBill(a1,"Consultation");
        double bill2 = generateBill(a2,"Emergency");
        System.out.println("Bills: ₹"+bill1+", ₹"+bill2);

        generateHospitalReport(new Doctor[]{d1,d2});
        System.out.println("Dr. Utilization (d1): " + String.format("%.1f", getDoctorUtilization(d1)) + "%");
        getPatientStatistics(new Patient[]{p1,p2});
    }
}
