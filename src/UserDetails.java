import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class UserDetails {

    public static boolean login(Scanner scanner) {
        System.out.println("Indtast dit brugernavn: ");
        String loginUsername = scanner.nextLine();
        System.out.println("Indtast din adgangskode: ");
        String loginPassword = scanner.nextLine();

        boolean isAuthenticated = EventManager.authenticate(loginUsername, loginPassword);

        if (isAuthenticated) {
            BankUser.setAuthenticatedUser(loginUsername);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            System.out.println("Login er succesfuldt, velkommen til banksystemet.");
            System.out.println("Du loggede ind klokken: " + now.format(formatter));
            return true;
        } else {
            System.out.println();
            return false;
        }
    }

    public static void opretBruger(Scanner scanner) {
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

    private static String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            accountNumber = String.format("%08d", random.nextInt(100000000));
        } while (EventManager.checkIfAccountNumberExists(accountNumber));

        return accountNumber;
    }
}
