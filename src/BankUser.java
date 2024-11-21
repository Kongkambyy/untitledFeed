import java.util.InputMismatchException;
import java.util.Scanner;

public class BankUser {

    private static String authenticatedUser = null;

    public static void setAuthenticatedUser(String username) {
        authenticatedUser = username;
    }

    public static void deposit(Scanner scanner) {
        if (authenticatedUser == null) {
            System.out.println("Ingen bruger er logget ind.");
            return;
        }

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
                afslutEllerTilbage(scanner);
            } else {
                System.out.println("Ugyldigt beløb");
            }
        } catch (InputMismatchException e) {
            System.out.println("Ugyldigt valg");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Der opstod en fejl");
        }
    }

    public static void withdraw(Scanner scanner) {
        if (authenticatedUser == null) {
            System.out.println("Ingen bruger er logget ind.");
            return;
        }

        EventManager.BankInfo userBankInfo = EventManager.getBankInfo(authenticatedUser);

        if (userBankInfo == null) {
            System.out.println("Bankoplysningerne kunne ikke findes");
            return;
        }

        try {
            System.out.println("Indtast beløbet du vil hæve fra din konto");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (amount > 0 && userBankInfo.getBalance() >= amount) {
                double newBalance = userBankInfo.getBalance() - amount;
                userBankInfo.setBalance(newBalance);
                System.out.println("Dine nye saldo er: " + newBalance + "DKK");
                afslutEllerTilbage(scanner);
            } else {
                System.out.println("Ugyldigt beløb, du har ikke nok penge på din konto");
            }
        } catch (InputMismatchException e) {
            System.out.println("Ugyldigt valg");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Der opstod en fejl");
        }
    }

    public static void getBalance() {
        if (authenticatedUser == null) {
            System.out.println("Ingen bruger er logget ind.");
            return;
        }

        EventManager.BankInfo userBankInfo = EventManager.getBankInfo(authenticatedUser);

        if (userBankInfo == null) {
            System.out.println("Bankoplysningerne kunne ikke findes");
            return;
        }

        System.out.println("Kontoinformationer for bruger: " + authenticatedUser);
        System.out.println("Brugerens kontonummer: " + userBankInfo.getAccountNumber());
        System.out.println("Saldo: " + userBankInfo.getBalance() + " DKK");
    }

    public static void afslutEllerTilbage(Scanner scanner) {
        System.out.println("\n--- Valgmenu ---");
        System.out.println("1. Tilbage til hovedmenuen");
        System.out.println("2. Afslut programmet");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> System.out.println("Du vil nu blive taget tilbage til hovedmenuen");
            case 2 -> {
                System.out.println("Programmet bliver nu afsluttet, tak for at benytte dig af banksystemet");
                scanner.close();
                System.exit(0);
            }
            default -> System.out.println("Ugyldigt valg");
        }
    }

    public static void forblivLoggetInd() {
        if (authenticatedUser == null) {
            System.out.println("Du er ikke længere logget ind. Venligst genstart programmet.");
            System.exit(0);
        }
    }

}
