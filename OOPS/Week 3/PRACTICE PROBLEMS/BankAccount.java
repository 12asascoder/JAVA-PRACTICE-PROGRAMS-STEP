public class BankAccount {
    static String bankName;
    static int totalAccounts;
    static double interestRate;

    String accountNumber;
    String accountHolder;
    double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        totalAccounts++;
    }

    public static void setBankName(String name) { bankName = name; }
    public static void setInterestRate(double rate) { interestRate = rate; }
    public static int getTotalAccounts() { return totalAccounts; }
    public static void displayBankInfo() {
        System.out.println("Bank: " + bankName + ", Total Accounts: " + totalAccounts + ", Interest Rate: " + interestRate + "%");
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println(accountHolder + " deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(accountHolder + " withdrew: " + amount);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.println(accountHolder + " will earn interest: " + interest);
    }

    public void displayAccountInfo() {
        System.out.println("Account No: " + accountNumber + ", Holder: " + accountHolder + ", Balance: " + balance);
    }

    public static void main(String[] args) {
        BankAccount.setBankName("Global Bank");
        BankAccount.setInterestRate(5.0);

        BankAccount acc1 = new BankAccount("001", "Alice", 1000);
        BankAccount acc2 = new BankAccount("002", "Bob", 2000);

        acc1.deposit(500);
        acc1.calculateInterest();
        acc1.displayAccountInfo();

        acc2.withdraw(300);
        acc2.calculateInterest();
        acc2.displayAccountInfo();

        BankAccount.displayBankInfo();
    }
}