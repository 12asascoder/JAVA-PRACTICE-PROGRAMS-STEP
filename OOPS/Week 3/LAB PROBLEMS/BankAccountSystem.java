public class BankAccountSystem {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private static int totalAccounts = 0;
    private static int counter = 0;

    public BankAccountSystem(String accountHolderName, double initialDeposit) {
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.accountNumber = generateAccountNumber();
        totalAccounts++;
    }

    private static String generateAccountNumber() {
        counter++;
        return "ACC" + String.format("%03d", counter);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + " to " + accountHolderName);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " from " + accountHolderName);
        } else {
            System.out.println("Invalid or insufficient balance!");
        }
    }

    public void checkBalance() {
        System.out.println(accountHolderName + "'s Balance: " + balance);
    }

    public void displayAccountInfo() {
        System.out.println("Account No: " + accountNumber + ", Holder: " + accountHolderName + ", Balance: " + balance);
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    public static void main(String[] args) {
        BankAccountSystem[] accounts = new BankAccountSystem[3];
        accounts[0] = new BankAccountSystem("Alice", 1000);
        accounts[1] = new BankAccountSystem("Bob", 2000);
        accounts[2] = new BankAccountSystem("Charlie", 500);

        accounts[0].deposit(200);
        accounts[1].withdraw(300);
        accounts[2].checkBalance();

        for (BankAccountSystem acc : accounts) {
            acc.displayAccountInfo();
        }

        System.out.println("Total Accounts Created: " + BankAccountSystem.getTotalAccounts());
    }
}