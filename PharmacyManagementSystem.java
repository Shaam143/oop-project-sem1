import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//tests
class Medicine {
    private final int medicineId;
    private final String name;
    private final String category;
    private int quantity;
    private double price;

    public Medicine(int medicineId, String name, String category, int quantity, double price) {
        this.medicineId = medicineId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public int getMedicineId() {
        return medicineId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Medicine ID: %d, Name: %s, Category: %s, Quantity: %d, Price: RM%.2f",
                medicineId, name, category, quantity, price);
    }
}

class Prescription {
    private int prescriptionId;
    private int doctorId;
    private int patientId;
    private LocalDate date;
    private ArrayList<Medicine> medicines;
    private String status; // PENDING, FILLED, CANCELLED

    public Prescription(int prescriptionId, int doctorId, int patientId, String dateStr) {
        this.prescriptionId = prescriptionId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        try {
            this.date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use DD/MM/YYYY");
        }
        this.medicines = new ArrayList<>();
        this.status = "PENDING";
    }

    // Getters
    public int getPrescriptionId() {
        return prescriptionId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Methods
    public void addMedicine(Medicine medicine) {
        medicines.add(medicine);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder details = new StringBuilder(String.format(
                "Prescription ID: %d, Doctor ID: %d, Patient ID: %d, Date: %s, Status: %s\nMedicines:\n",
                prescriptionId, doctorId, patientId, getDate(), status));
        for (Medicine medicine : medicines) {
            details.append(medicine).append("\n");
        }
        return details.toString();
    }
}

class Doctor {
    private int doctorId;
    private String name;
    private String specialty;
    private String contactNumber;

    public Doctor(int doctorId, String name, String specialty, String contactNumber) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
    }

    public int getDoctorId() {
        return doctorId;
    }
    
    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return String.format("Doctor ID: %d, Name: %s, Specialty: %s, Contact: %s",
                doctorId, name, specialty, contactNumber);
    }
}

class Patient {
    private int patientId;
    private String name;
    private int age;
    private String contactNumber;
    private String address;

    public Patient(int patientId, String name, int age, String contactNumber, String address) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Patient ID: %d, Name: %s, Age: %d, Contact: %s, Address: %s",
                patientId, name, age, contactNumber, address);
    }
}

class InventoryManager {
    private ArrayList<Medicine> medicines;

    public InventoryManager(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    public void checkLowStock(int threshold) {
        System.out.println("\n=== Low Stock Alert ===");
        boolean hasLowStock = false;
        for (Medicine medicine : medicines) {
            if (medicine.getQuantity() <= threshold) {
                System.out
                        .println(medicine.getName() + " is low on stock. Current quantity: " + medicine.getQuantity());
                hasLowStock = true;
            }
        }
        if (!hasLowStock) {
            System.out.println("No medicines are below the threshold quantity.");
        }
    }

    public void updateStock(int medicineId, int quantity) {
        for (Medicine medicine : medicines) {
            if (medicine.getMedicineId() == medicineId) {
                medicine.setQuantity(medicine.getQuantity() + quantity);
                System.out.println("Stock updated successfully for " + medicine.getName());
                return;
            }
        }
        System.out.println("Medicine not found!");
    }
}

class Pharmacist {
    private int pharmacistId;
    private String name;
    private String shift;

    public Pharmacist(int pharmacistId, String name, String shift) {
        this.pharmacistId = pharmacistId;
        this.name = name;
        this.shift = shift;
    }

    public int getPharmacistId() {
        return pharmacistId;
    }

    public String getName() {
        return name;
    }

    public String getShift() {
        return shift;
    }

    @Override
    public String toString() {
        return String.format("Pharmacist ID: %d, Name: %s, Shift: %s",
                pharmacistId, name, shift);
    }
}

class ReportGenerator {
    private ArrayList<Prescription> prescriptions;
    private ArrayList<Medicine> medicines;

    public ReportGenerator(ArrayList<Prescription> prescriptions, ArrayList<Medicine> medicines) {
        this.prescriptions = prescriptions;
        this.medicines = medicines;
    }

    public void generateDailySalesReport(String date) {
        System.out.println("\n=== Daily Sales Report for " + date + " ===");
        double totalSales = 0.0;
        int totalPrescriptions = 0;

        for (Prescription prescription : prescriptions) {
            if (prescription.getDate().equals(date)) {
                totalPrescriptions++;
                for (Medicine medicine : prescription.getMedicines()) {
                    totalSales += medicine.getPrice() * medicine.getQuantity();
                }
            }
        }

        System.out.println("Total Prescriptions: " + totalPrescriptions);
        System.out.printf("Total Sales: RM%.2f%n", totalSales);
    }

    public void generateMedicineUsageReport() {
        System.out.println("\n=== Medicine Usage Report ===");
        for (Medicine medicine : medicines) {
            int totalPrescribed = 0;
            for (Prescription prescription : prescriptions) {
                for (Medicine prescribedMed : prescription.getMedicines()) {
                    if (prescribedMed.getMedicineId() == medicine.getMedicineId()) {
                        totalPrescribed += prescribedMed.getQuantity();
                    }
                }
            }
            System.out.println(medicine.getName() + " - Total Prescribed: " + totalPrescribed +
                    ", Remaining Stock: " + medicine.getQuantity());
        }
    }
}

class PharmacyManager {
    private ArrayList<Doctor> doctors;
    private ArrayList<Patient> patients;
    private ArrayList<Medicine> medicines;
    private ArrayList<Prescription> prescriptions;
    private ArrayList<Pharmacist> pharmacists;
    private Scanner scanner;
    public PharmacyManager() {
        doctors = new ArrayList<>();
        patients = new ArrayList<>();
        medicines = new ArrayList<>();
        prescriptions = new ArrayList<>();
        pharmacists = new ArrayList<>();
        scanner = new Scanner(System.in);
        scanner = new Scanner(System.in);
    }

    // Direct object addition methods
    public void addDoctor(Doctor doctor) {
        if (findDoctor(doctor.getDoctorId()) != null) {
            throw new IllegalArgumentException("Doctor ID already exists");
        }
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        if (findPatient(patient.getPatientId()) != null) {
            throw new IllegalArgumentException("Patient ID already exists");
        }
        patients.add(patient);
    }

    public void addMedicine(Medicine medicine) {
        if (findMedicine(medicine.getMedicineId()) != null) {
            throw new IllegalArgumentException("Medicine ID already exists");
        }
        medicines.add(medicine);
    }

    public void addPrescription(Prescription prescription) {
        if (findPrescription(prescription.getPrescriptionId()) != null) {
            throw new IllegalArgumentException("Prescription ID already exists");
        }
        prescriptions.add(prescription);
    }

    // Interactive addition methods
    public void addPharmacistInteractive() {
        try {
            System.out.println("\nEnter Pharmacist Details:");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (findPharmacist(id) != null) {
                System.out.println("Pharmacist ID already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Shift: ");
            String shift = scanner.nextLine();

            pharmacists.add(new Pharmacist(id, name, shift));
            System.out.println("Pharmacist added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data types.");
            scanner.nextLine(); // Clear the scanner
        }
    }
    public void addDoctorInteractive() {
        try {
            System.out.println("\nEnter Doctor Details:");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (findDoctor(id) != null) {
                System.out.println("Doctor ID already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Specialty: ");
            String specialty = scanner.nextLine();
            System.out.print("Contact Number: ");
            String contact = scanner.nextLine();

            doctors.add(new Doctor(id, name, specialty, contact));
            System.out.println("Doctor added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data types.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    public void addPatientInteractive() {
        try {
            System.out.println("\nEnter Patient Details:");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (findPatient(id) != null) {
                System.out.println("Patient ID already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Contact Number: ");
            String contact = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();

            patients.add(new Patient(id, name, age, contact, address));
            System.out.println("Patient added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data types.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    public void addMedicineInteractive() {
        try {
            System.out.println("\nEnter Medicine Details:");
            System.out.print("ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (findMedicine(id) != null) {
                System.out.println("Medicine ID already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Category: ");
            String category = scanner.nextLine();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Price (RM): ");
            double price = scanner.nextDouble();

            medicines.add(new Medicine(id, name, category, quantity, price));
            System.out.println("Medicine added successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data types.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    public void createPrescriptionInteractive() {
        try {
            System.out.println("\nEnter Prescription Details:");
            System.out.print("Prescription ID: ");
            int presId = scanner.nextInt();

            if (findPrescription(presId) != null) {
                System.out.println("Prescription ID already exists!");
                return;
            }

            System.out.print("Doctor ID: ");
            int docId = scanner.nextInt();
            Doctor doctor = findDoctor(docId);
            if (doctor == null) {
                System.out.println("Doctor not found!");
                return;
            }

            System.out.print("Patient ID: ");
            int patId = scanner.nextInt();
            Patient patient = findPatient(patId);
            if (patient == null) {
                System.out.println("Patient not found!");
                return;
            }

            scanner.nextLine(); // Consume newline
            System.out.print("Date (DD/MM/YYYY): ");
            String date = scanner.nextLine();

            Prescription prescription = new Prescription(presId, docId, patId, date);

            while (true) {
                System.out.print("Add medicine? (y/n): ");
                String choice = scanner.nextLine().toLowerCase();
                if (!choice.equals("y"))
                    break;

                System.out.print("Medicine ID: ");
                int medId = scanner.nextInt();
                Medicine medicine = findMedicine(medId);
                if (medicine == null) {
                    System.out.println("Medicine not found!");
                    scanner.nextLine(); // Consume newline
                    continue;
                }

                System.out.print("Quantity prescribed: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (quantity > medicine.getQuantity()) {
                    System.out.println("Insufficient stock!");
                    continue;
                }

                Medicine prescribedMed = new Medicine(
                        medicine.getMedicineId(),
                        medicine.getName(),
                        medicine.getCategory(),
                        quantity,
                        medicine.getPrice());
                prescription.addMedicine(prescribedMed);

                // Update stock
                medicine.setQuantity(medicine.getQuantity() - quantity);
            }

            prescriptions.add(prescription);
            System.out.println("Prescription created successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter correct data types.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    // Find methods
    public Pharmacist findPharmacist(int id) {
        return pharmacists.stream()
                .filter(p -> p.getPharmacistId() == id)
                .findFirst()
                .orElse(null);
    }
    public Doctor findDoctor(int id) {
        return doctors.stream()
                .filter(d -> d.getDoctorId() == id)
                .findFirst()
                .orElse(null);
    }

    public Patient findPatient(int id) {
        return patients.stream()
                .filter(p -> p.getPatientId() == id)
                .findFirst()
                .orElse(null);
    }

    public Medicine findMedicine(int id) {
        return medicines.stream()
                .filter(m -> m.getMedicineId() == id)
                .findFirst()
                .orElse(null);
    }

    public Prescription findPrescription(int id) {
        return prescriptions.stream()
                .filter(p -> p.getPrescriptionId() == id)
                .findFirst()
                .orElse(null);
    }

    // Display methods (continued)
    public void displayDoctors() {
        System.out.println("\n=== Doctors ===");
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }
        doctors.forEach(System.out::println);
    }

    public void displayPatients() {
        System.out.println("\n=== Patients ===");
        if (patients.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        patients.forEach(System.out::println);
    }

    public void displayMedicines() {
        System.out.println("\n=== Medicines ===");
        if (medicines.isEmpty()) {
            System.out.println("No medicines in inventory.");
            return;
        }
        medicines.forEach(System.out::println);
    }

    public void displayPharmacists() {
        System.out.println("\n=== Pharmacists ===");
        if (pharmacists.isEmpty()) {
            System.out.println("No pharmacists registered.");
            return;
        }
        pharmacists.forEach(System.out::println);
    }

    public void displayPrescriptions() {
        System.out.println("\n=== Prescriptions ===");
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions recorded.");
            return;
        }
        prescriptions.forEach(System.out::println);
    }

    // Search methods
    public void searchPrescriptionsByDoctor() {
        System.out.print("Enter Doctor ID: ");
        int docId = scanner.nextInt();
        System.out.println("\n=== Prescriptions for Doctor ID: " + docId + " ===");
        prescriptions.stream()
                .filter(p -> p.getDoctorId() == docId)
                .forEach(System.out::println);
    }

    public void searchPrescriptionsByPatient() {
        System.out.print("Enter Patient ID: ");
        int patId = scanner.nextInt();
        System.out.println("\n=== Prescriptions for Patient ID: " + patId + " ===");
        prescriptions.stream()
                .filter(p -> p.getPatientId() == patId)
                .forEach(System.out::println);
    }

    // Inventory management methods
    public void checkLowStock() {
        try {
            System.out.print("Enter stock threshold: ");
            int threshold = scanner.nextInt();
            InventoryManager inventoryManager = new InventoryManager(medicines);
            inventoryManager.checkLowStock(threshold);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    public void updateMedicineStock() {
        try {
            System.out.print("Enter Medicine ID: ");
            int id = scanner.nextInt();
            System.out.print("Enter quantity to add: ");
            int quantity = scanner.nextInt();

            InventoryManager inventoryManager = new InventoryManager(medicines);
            inventoryManager.updateStock(id, quantity);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numbers only.");
            scanner.nextLine(); // Clear the scanner
        }
    }

    // Report generation methods
    public void generateReports() {
        ReportGenerator reporter = new ReportGenerator(prescriptions, medicines);

        System.out.println("\n=== Report Generation ===");
        System.out.println("1. Daily Sales Report");
        System.out.println("2. Medicine Usage Report");
        System.out.print("Enter your choice: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter date (DD/MM/YYYY): ");
                    String date = scanner.nextLine();
                    reporter.generateDailySalesReport(date);
                }
                case 2 -> reporter.generateMedicineUsageReport();
                default -> System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine(); // Clear the scanner
        }
    }

    public void close() {
        scanner.close();
    }

    // Add these methods to the PharmacyManager class:

    public void deleteDoctorInteractive() {
        try {
            System.out.println("\n=== Delete Doctor ===");
            System.out.print("Enter Doctor ID to delete: ");
            int id = scanner.nextInt();

            // Check if doctor has any prescriptions
            boolean hasPresciptions = prescriptions.stream()
                    .anyMatch(p -> p.getDoctorId() == id);

            if (hasPresciptions) {
                System.out.println("Cannot delete doctor with existing prescriptions!");
                return;
            }

            Doctor doctor = findDoctor(id);
            if (doctor == null) {
                System.out.println("Doctor not found!");
                return;
            }

            doctors.remove(doctor);
            System.out.println("Doctor deleted successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid ID.");
            scanner.nextLine(); // Clear scanner
        }
    }

    public void deletePatientInteractive() {
        try {
            System.out.println("\n=== Delete Patient ===");
            System.out.print("Enter Patient ID to delete: ");
            int id = scanner.nextInt();

            // Check if patient has any prescriptions
            boolean hasPresciptions = prescriptions.stream()
                    .anyMatch(p -> p.getPatientId() == id);

            if (hasPresciptions) {
                System.out.println("Cannot delete patient with existing prescriptions!");
                return;
            }

            Patient patient = findPatient(id);
            if (patient == null) {
                System.out.println("Patient not found!");
                return;
            }

            patients.remove(patient);
            System.out.println("Patient deleted successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid ID.");
            scanner.nextLine(); // Clear scanner
        }
    }

    public void deleteMedicineInteractive() {
        try {
            System.out.println("\n=== Delete Medicine ===");
            System.out.print("Enter Medicine ID to delete: ");
            int id = scanner.nextInt();

            // Check if medicine is used in any prescriptions
            boolean isUsedInPrescriptions = prescriptions.stream()
                    .flatMap(p -> p.getMedicines().stream())
                    .anyMatch(m -> m.getMedicineId() == id);

            if (isUsedInPrescriptions) {
                System.out.println("Cannot delete medicine that is used in prescriptions!");
                return;
            }

            Medicine medicine = findMedicine(id);
            if (medicine == null) {
                System.out.println("Medicine not found!");
                return;
            }

            medicines.remove(medicine);
            System.out.println("Medicine deleted successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid ID.");
            scanner.nextLine(); // Clear scanner
        }
    }

    public void deletePrescriptionInteractive() {
        try {
            System.out.println("\n=== Delete Prescription ===");
            System.out.print("Enter Prescription ID to delete: ");
            int id = scanner.nextInt();

            Prescription prescription = findPrescription(id);
            if (prescription == null) {
                System.out.println("Prescription not found!");
                return;
            }

            // Return medicines to inventory
            for (Medicine prescribedMed : prescription.getMedicines()) {
                Medicine inventoryMed = findMedicine(prescribedMed.getMedicineId());
                if (inventoryMed != null) {
                    inventoryMed.setQuantity(inventoryMed.getQuantity() + prescribedMed.getQuantity());
                }
            }

            prescriptions.remove(prescription);
            System.out.println("Prescription deleted successfully and medicine quantities returned to inventory!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid ID.");
            scanner.nextLine(); // Clear scanner
        }
    }

    public void deletePharmacistInteractive() {
        try {
            System.out.println("\n=== Delete Pharmacist ===");
            System.out.print("Enter Pharmacist ID to delete: ");
            int id = scanner.nextInt();

            Pharmacist pharmacist = findPharmacist(id);
            if (pharmacist == null) {
                System.out.println("Pharmacist not found!");
                return;
            }

            pharmacists.remove(pharmacist);
            System.out.println("Pharmacist deleted successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid ID.");
            scanner.nextLine(); // Clear scanner
        }
    }

    // Direct deletion methods for programmatic use
    public boolean deleteDoctor(int id) {
        Doctor doctor = findDoctor(id);
        if (doctor == null)
            return false;

        boolean hasPresciptions = prescriptions.stream()
                .anyMatch(p -> p.getDoctorId() == id);

        if (hasPresciptions)
            return false;

        return doctors.remove(doctor);
    }

    public boolean deletePatient(int id) {
        Patient patient = findPatient(id);
        if (patient == null)
            return false;

        boolean hasPresciptions = prescriptions.stream()
                .anyMatch(p -> p.getPatientId() == id);

        if (hasPresciptions)
            return false;

        return patients.remove(patient);
    }

    public boolean deleteMedicine(int id) {
        Medicine medicine = findMedicine(id);
        if (medicine == null)
            return false;

        boolean isUsedInPrescriptions = prescriptions.stream()
                .flatMap(p -> p.getMedicines().stream())
                .anyMatch(m -> m.getMedicineId() == id);

        if (isUsedInPrescriptions)
            return false;

        return medicines.remove(medicine);
    }

    public boolean deletePrescription(int id) {
        Prescription prescription = findPrescription(id);
        if (prescription == null)
            return false;

        // Return medicines to inventory
        for (Medicine prescribedMed : prescription.getMedicines()) {
            Medicine inventoryMed = findMedicine(prescribedMed.getMedicineId());
            if (inventoryMed != null) {
                inventoryMed.setQuantity(inventoryMed.getQuantity() + prescribedMed.getQuantity());
            }
        }

        return prescriptions.remove(prescription);
    }
}

public class PharmacyManagementSystem {
    private static void displayMenu() {
        System.out.println("*******************************************");
        System.out.println("*                                         *");
        System.out.println("*       Welcome to the Pharmacy           *");
        System.out.println("*         Management System!              *");
        System.out.println("*                                         *");
        System.out.println("*******************************************");
        System.out.println("\n=== Pharmacy Management System ===");
        System.out.println("+----+-----------------------------------+");
        System.out.printf("| %-3s | %-33s |%n", "No.", "Option");
        System.out.println("+----+-----------------------------------+");
        System.out.printf("| %-3s | %-33s |%n", "1.", "Add Doctor");
        System.out.printf("| %-3s | %-33s |%n", "2.", "Add Patient");
        System.out.printf("| %-3s | %-33s |%n", "3.", "Add Medicine");
        System.out.printf("| %-3s | %-33s |%n", "4.", "Add Pharmacist");
        System.out.printf("| %-3s | %-33s |%n", "5.", "Create Prescription");
        System.out.printf("| %-3s | %-33s |%n", "6.", "Display Doctors");
        System.out.printf("| %-3s | %-33s |%n", "7.", "Display Patients");
        System.out.printf("| %-3s | %-33s |%n", "8.", "Display Medicines");
        System.out.printf("| %-3s | %-33s |%n", "9.", "Display Prescriptions");
        System.out.printf("| %-3s | %-33s |%n", "10.", "Display Pharmacists");
        System.out.printf("| %-3s | %-33s |%n", "11.", "Search Prescriptions by Doctor");
        System.out.printf("| %-3s | %-33s |%n", "12.", "Search Prescriptions by Patient");
        System.out.printf("| %-3s | %-33s |%n", "13.", "Check Low Stock");
        System.out.printf("| %-3s | %-33s |%n", "14.", "Update Medicine Stock");
        System.out.printf("| %-3s | %-33s |%n", "15.", "Generate Reports");
        System.out.printf("| %-3s | %-33s |%n", "16.", "Delete Doctor");
        System.out.printf("| %-3s | %-33s |%n", "17.", "Delete Patient");
        System.out.printf("| %-3s | %-33s |%n", "18.", "Delete Medicine");
        System.out.printf("| %-3s | %-33s |%n", "19.", "Delete Prescription");
        System.out.printf("| %-3s | %-33s |%n", "20.", "Delete Pharmacist");
        System.out.printf("| %-3s | %-33s |%n", "0.", "Exit");
        System.out.println("+----+-----------------------------------+");
        System.out.print("Enter your choice: ");
        }

        public static void main(String[] args) {
        PharmacyManager manager = new PharmacyManager();
        Scanner scanner = new Scanner(System.in);

        // Add sample data
        try {
            manager.addDoctor(new Doctor(1, "Dr. Smith", "Cardiology", "123-456-7890"));
            manager.addPatient(new Patient(101, "John Doe", 30, "098-765-4321", "123 Main St"));
            manager.addMedicine(new Medicine(301, "Paracetamol", "Painkiller", 100, 5.0));
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding sample data: " + e.getMessage());
        }

        while (true) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> manager.addDoctorInteractive();
                    case 2 -> manager.addPatientInteractive();
                    case 3 -> manager.addMedicineInteractive();
                    case 4 -> manager.addPharmacistInteractive();
                    case 5 -> manager.createPrescriptionInteractive();
                    case 6 -> manager.displayDoctors();
                    case 7 -> manager.displayPatients();
                    case 8 -> manager.displayMedicines();
                    case 9 -> manager.displayPrescriptions();
                    case 10 -> manager.displayPharmacists();
                    case 11 -> manager.searchPrescriptionsByDoctor();
                    case 12 -> manager.searchPrescriptionsByPatient();
                    case 13 -> manager.checkLowStock();
                    case 14 -> manager.updateMedicineStock();
                    case 15 -> manager.generateReports();
                    case 16 -> manager.deleteDoctorInteractive();
                    case 17 -> manager.deletePatientInteractive();
                    case 18 -> manager.deleteMedicineInteractive();
                    case 19 -> manager.deletePrescriptionInteractive();
                    case 20 -> manager.deletePharmacistInteractive();
                    case 0 -> {
                        System.out.println("Thank you for using Pharmacy Management System!");
                        manager.close();
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}
