import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminUser {

    public static void adminMenu(Scanner scanner) {

        while (true) {
            try {
                System.out.println("\n--- Admin Panel ---");
                System.out.println("1. Vis alle oprettede brugere i systemet.");
                System.out.println("2. Nulstil en brugers adgangskode.");
                System.out.println("3. Slet en bruger fra systemet");
                System.out.println("4. Afslut Admin Menu");
                System.out.println("5. Afslut programmet");
                System.out.println("Vælg en af ovenstående muligheder");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> seAlleBrugere();
                    case 2 -> nulstilAdgangskode(scanner);
                    case 3 -> sletBruger(scanner);
                    case 4 -> {
                        System.out.println("Du bliver nu returneret til hovedmenuen...");
                        return;
                    }
                    case 5 -> {
                        System.out.println("Afslutter programmet...");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Ugyldigt valg, prøv igen.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input. Indtast et gyldigt valg (1-5).");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("Der opstod en uventet fejl: " + e.getMessage());
            }
        }
    }

    public static void seAlleBrugere() {

        System.out.println("\n--- Liste over alle brugere ---");
        for (EventManager.BankInfo bankInfo : EventManager.info) {
            System.out.println();
            System.out.println("Brugernavn: " + bankInfo.getUsername());
            System.out.println("Kontonummber: " + bankInfo.getAccountNumber());
            System.out.println("Saldo: " + bankInfo.getBalance());
        }

    }

    public static void nulstilAdgangskode(Scanner scanner) {

        System.out.println("\n--- Nulstil adgangskode ---");
        System.out.println("Indtast et brugernavn: ");
        String username = scanner.nextLine();

        for (EventManager.UserAccount account : EventManager.accounts) {
            if (account.getUsername().equals(username)) {
                System.out.println("Indtast en ny adgangskode: ");
                String nytPassword = scanner.nextLine();
                account.setPassword(nytPassword);
                System.out.println("Adgangskoden er blevet nulstillet for brugeren: " + account.getUsername());
                return;
            }
        }
        System.out.println("Brugernavn blev ikke fundet");

    }

    public static void sletBruger(Scanner scanner) {

        System.out.println("\n--- Slet en bruger ---");
        System.out.println("Indtast et brugernavn: ");
        String username = scanner.nextLine();

        boolean userDeleted = EventManager.accounts.removeIf(account -> account.getUsername().equals(username));
        if (userDeleted) {
            System.out.println("Brugeren er blevet slettet fra systemet");
        } else {
            System.out.println("Brugeren blev ikke fundet");
        }

        seAlleBrugere();

    }

    public static boolean adminLogin(Scanner scanner) {
        System.out.print("Indtast admin-brugernavn: ");
        String username = scanner.nextLine();
        System.out.print("Indtast admin-adgangskode: ");
        String password = scanner.nextLine();

        return username.equals("admin") && password.equals("admin");
    }
}