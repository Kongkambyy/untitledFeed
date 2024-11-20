import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    String kontonummer;
    double saldo;
    double deposit;
    double withdrawal;
    private static String authenticatedUser = null;


    public static void main(String[] args) {

        boolean isAuthenticated = false;

        while (!isAuthenticated) {
            System.out.println("\n--- Velkommen til banksystemet ---");
            System.out.println("1. Log ind");
            System.out.println("2. Registrer en ny bruger");
            System.out.println("3. Afslut programmet");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> isAuthenticated = login();
                case 2 -> {
                    opretBruger();
                    System.out.println("Du har nu registreret dig til banksystemet.");
                }
                case 3 -> {
                    System.out.println("Programmet bliver nu afsluttet");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }

        while (true) {
            System.out.println("1. Se dit kontonummer og saldo");
            System.out.println("2. Indskyd penge til din konto");
            System.out.println("3. Hæv penge fra din konto");
            System.out.println("4. Afslut");
            System.out.println("Vælg en af ovenstående muligheder: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                        forblivLoggetInd();
                        getBalance();
                }

                case 2 -> {
                        forblivLoggetInd();
                        deposit();
                }

                case 3 -> {
                        forblivLoggetInd();
                        withdraw();
                }

                case 4 -> {
                    System.out.println("Afslutter programmet...");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Ugyldigt valg, prøv igen.");

            }
        }
    }

    public static boolean login() {

        System.out.println("Indtast dit brugernavn: ");
        String loginUsername = scanner.nextLine();
        System.out.println("Indtast din adgangskode: ");
        String loginPassword = scanner.nextLine();

        boolean isAuthenticated = EventManager.authenticate(loginUsername, loginPassword);

        if (isAuthenticated) {
            authenticatedUser = loginUsername;
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String currentDateTime = now.format(formatter);

            System.out.println("Login er succesfuldt, velkommen til banksystemet.");
            System.out.println("Du loggede ind klokken: " + now);
            return true;
        } else {
            System.out.println("Du har indtastet et forkert brugernavn eller adgangskode. Prøv igen");
            return false;
        }
    }

    public static void deposit() {

        EventManager.BankInfo userBankInfo = EventManager.getBankInfo(authenticatedUser);

        if (userBankInfo == null) {
            System.out.println("Bankoplysningerne kunne ikke findes");
            return;
        }

        try {
            System.out.println("Indtast det beløb du vil indsætte: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0) {
                double newBalance = userBankInfo.getBalance() + amount;
                userBankInfo.setBalance(newBalance);
                System.out.println("Dine penge er nu blevet sat ind. Dine nye saldo er: " + newBalance + "DKK");
                afslutEllerTilbage();
            } else {
                System.out.println("Ugyldigt beløb");
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Ugyldigt valg");
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Der opstod en fejl");
        }
    }

    public static void withdraw(){

        EventManager.BankInfo userBankInfo = EventManager.getBankInfo(authenticatedUser);

        if (userBankInfo == null) {
            System.out.println("Bankoplysningerne kunne ikke findes");
            return;
        }

        try {
            System.out.println("Indtast beløbet du vil hæve fra din konto");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0) {
                double newBalance = userBankInfo.getBalance() - amount;
                userBankInfo.setBalance(newBalance);
                System.out.println("Dine nye saldo er: " + newBalance + "DKK");
                afslutEllerTilbage();
            } else {
                System.out.println("Ugyldigt beløb, du har ikke nok penge på din konto");
            }
        }

        catch (InputMismatchException e) {
            System.out.println("Ugyldigt valg");
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Der opstod en fejl");
        }

    }

    public static void getBalance(){

        EventManager.BankInfo userBankInfo = EventManager.getBankInfo(authenticatedUser);

        if (userBankInfo == null) {
            System.out.println("Bankoplysningerne kunne ikke findes");
            return;
        }

        System.out.println("Kontoinformationer for bruger: " + authenticatedUser);
        System.out.println("Saldo: " + userBankInfo.getBalance() + " DKK");
        afslutEllerTilbage();

    }

    public static void afslutEllerTilbage(){
        System.out.println("\n--- Valgmenu ---");
        System.out.println("1. Tilbage til hovedmenuen");
        System.out.println("2. Afslut programmet");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.println("Du vil nu blive taget tilbage til hovedmenuen");
            }

            case 2 -> {
                System.out.println("Programmet bliver nu afsluttet, tak for at benytte dig af banksystemet");
                scanner.close();
            }
            default -> System.out.println("Ugyldigt valg");
        }
    }
    public static void opretBruger(){

        System.out.println("\n--- Registrer en ny konto ---");
        System.out.println("Indtast dit ønskede brugernavn");
        String username = scanner.nextLine();
        System.out.println("Indtast din ønskede adgangskode: ");
        String password = scanner.nextLine();

        String accountNumber = generateAccountNumber();

        EventManager.addNewUser(username, password, accountNumber);


        System.out.println("Din konto er nu blevet registreret succesfuldt. Velkommen til banksystemet.");
        System.out.println("Dit kontonummer er: " + accountNumber);
        System.out.println("Din startsaldo er 0 DKK");

    }

    public static String generateAccountNumber(){
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = String.format("%08d", random.nextInt(100000));
        } while (EventManager.checkIfAccountNumberExists(accountNumber));

        return accountNumber;
    }

    public static void forblivLoggetInd() {
        if (authenticatedUser == null) {
            System.out.println("Du skal logge ind for at benytte banksystemet");
            while (!login()) {
                System.out.println("Forkert brugernavn eller adgangskode");
            }
        }
    }
}