import java.util.*;

class PersonalAccount {
    private String accountHolderName;
    private String accountNumber;
    private double currentBalance;
    private double totalIncome;
    private double totalExpenses;

    private static int totalAccounts = 0;
    private static String bankName = "Unset Bank";
    private static int acctCounter = 0;

    public PersonalAccount(String holderName, double openingDeposit) {
        this.accountHolderName = holderName;
        this.accountNumber = generateAccountNumber();
        this.currentBalance = 0;
        this.totalIncome = 0;
        this.totalExpenses = 0;
        totalAccounts++;
        if (openingDeposit > 0) addIncome(openingDeposit, "Opening Deposit");
    }

    public static void setBankName(String name) { bankName = name; }
    public static int getTotalAccounts() { return totalAccounts; }
    public static String generateAccountNumber() {
        acctCounter++;
        return "PA" + String.format("%04d", acctCounter);
    }

    public void addIncome(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Income must be positive.");
            return;
        }
        currentBalance += amount;
        totalIncome += amount;
        System.out.println("[" + accountNumber + "] +₹" + amount + " income: " + description);
    }

    public void addExpense(double amount, String description) {
        if (amount <= 0) {
            System.out.println("Expense must be positive.");
            return;
        }
        if (amount > currentBalance) {
            System.out.println("Insufficient funds for expense: " + description);
            return;
        }
        currentBalance -= amount;
        totalExpenses += amount;
        System.out.println("[" + accountNumber + "] -₹" + amount + " expense: " + description);
    }

    public double calculateSavings() {
        return totalIncome - totalExpenses;
    }

    public void displayAccountSummary() {
        System.out.println("----- Account Summary (" + bankName + ") -----");
        System.out.println("Holder: " + accountHolderName + "  Account#: " + accountNumber);
        System.out.println("Income: ₹" + String.format("%.2f", totalIncome));
        System.out.println("Expenses: ₹" + String.format("%.2f", totalExpenses));
        System.out.println("Savings: ₹" + String.format("%.2f", calculateSavings()));
        System.out.println("Current Balance (unique per account): ₹" + String.format("%.2f", currentBalance));
        System.out.println("Shared bankName (static): " + bankName);
        System.out.println("----------------------------------------------");
    }
}

public class PersonalFinanceManager {
    public static void main(String[] args) {
        PersonalAccount.setBankName("Prism National Bank");
        PersonalAccount a1 = new PersonalAccount("Aarav", 10000);
        PersonalAccount a2 = new PersonalAccount("Zara", 5000);
        PersonalAccount a3 = new PersonalAccount("Ishan", 0);

        a1.addIncome(2500, "Salary credit");
        a1.addExpense(1200, "Groceries");
        a2.addIncome(8000, "Freelance payment");
        a2.addExpense(4500, "Laptop EMI");
        a3.addIncome(3000, "Gift");
        a3.addExpense(3500, "Attempted rent (should fail due to insufficient funds)");
        a3.addExpense(2000, "Rent");

        a1.displayAccountSummary();
        a2.displayAccountSummary();
        a3.displayAccountSummary();

        System.out.println("Total personal accounts: " + PersonalAccount.getTotalAccounts());
    }
}
