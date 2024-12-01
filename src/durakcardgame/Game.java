package durakcardgame;

import java.util.ArrayList;

/**
 * Abstract class representing a generic game.
 * Provides a framework for game-specific functionality such as gameplay, player management, and win conditions.
 */
public abstract class Game {
    private final String name; // Name of the game
    private ArrayList<Player> players; // List of players participating in the game

    /**
     * Constructor to initialize the game with a name.
     *
     * @param name The name of the game.
     */
    public Game(String name) {
        this.name = name;
        this.players = new ArrayList<>(); // Initialize the list of players
    }

    /**
     * Retrieves the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the list of players in the game.
     *
     * @return An ArrayList containing all the players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Adds a player to the game.
     *
     * @param player The player to add.
     */
    public void addPlayer(Player player) {
        players.add(player); // Add the player to the list
    }

    /**
     * Abstract method to handle the gameplay logic.
     * This must be implemented by subclasses to define game-specific behavior.
     */
    public abstract void play();

    /**
     * Abstract method to declare the winner of the game.
     * This must be implemented by subclasses to define win conditions.
     */
    public abstract void declareWinner();

    /**
     * Abstract method to determine if the game is over.
     *
     * @return True if the game has ended, otherwise false.
     * This must be implemented by subclasses to define end-of-game conditions.
     */
    public abstract boolean isGameOver();
}
