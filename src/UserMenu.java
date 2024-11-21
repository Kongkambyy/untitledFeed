import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMenu {

    static Scanner scanner = new Scanner(System.in);
    static boolean isAuthenticated = false;
    public static void loginScreen() {

        System.out.println("\n--- Velkommen til banksystemet ---");

        while (!isAuthenticated) {
            try {
                System.out.println("1. Log ind");
                System.out.println("2. Registrer en ny bruger");
                System.out.println("3. Log ind som Administrator");
                System.out.println("4. Afslut programmet");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        isAuthenticated = UserDetails.login(scanner);
                        if (isAuthenticated) {
                            mainMenu();
                        }
                    }
                    case 2 -> UserDetails.opretBruger(scanner);
                    case 3 -> {
                        if (AdminUser.adminLogin(scanner)) {
                            AdminUser.adminMenu(scanner);
                        } else {
                            System.out.println("Du har indtastet et forkert Admin log ind.");
                        }
                    }
                    case 4 -> {
                        System.out.println("Programmet bliver nu afsluttet.");
                        scanner.close();
                        return;
                    }
                    default -> System.out.println("Ugyldigt valg, prøv igen.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ugyldigt input. Vælg et tal mellem 1 og 4.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("Der opstod en uventet fejl: " + e.getMessage());
            }
        }
    }

    public static void mainMenu() {
        if (!isAuthenticated) {
            loginScreen();

        }
        while (true) {
            System.out.println("1. Se dit kontonummer og saldo");
            System.out.println("2. Indskyd penge til din konto");
            System.out.println("3. Hæv penge fra din konto");
            System.out.println("4. Vend tilbage til hovedmenuen");
            System.out.println("5. Afslut programmet");
            System.out.println("Vælg en af ovenstående muligheder: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    BankUser.forblivLoggetInd();
                    BankUser.getBalance();
                }
                case 2 -> {
                    BankUser.forblivLoggetInd();
                    BankUser.deposit(scanner);
                }
                case 3 -> {
                    BankUser.forblivLoggetInd();
                    BankUser.withdraw(scanner);
                }
                case 4 -> {
                    System.out.println("Du bliver nu vendt tilbage til hovedmenuen");
                    isAuthenticated = false;
                    loginScreen();
                }
                case 5 -> {
                    System.out.println("Afslutter programmet...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Ugyldigt valg, prøv igen.");
            }
        }
    }
}
