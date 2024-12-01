package durakcardgame;

import java.util.Scanner;

/**
 * The main class for running the Durak card game.
 * It initializes the game, collects player information, and starts the gameplay.
 */
public class DurakCardGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome message
        System.out.println("Welcome to Durak!");

        // Prompt the user to enter the number of players
        System.out.print("Enter number of players (2-6): ");
        int numPlayers = scanner.nextInt();

        // Validate the number of players (must be between 2 and 6)
        while (numPlayers < 2 || numPlayers > 6) {
            System.out.print("Invalid number. Enter players (2-6): ");
            numPlayers = scanner.nextInt();
        }

        // Collect player names
        String[] playerNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            playerNames[i] = scanner.next();
        }

        // Create and initialize the Durak game
        DurakGame game = new DurakGame("Durak", playerNames);

        // Start the gameplay
        game.play();
    }
}
