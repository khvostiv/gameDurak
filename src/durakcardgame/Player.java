package durakcardgame;

/**
 * Abstract class representing a generic player in a game.
 * Provides a framework for player-specific attributes and actions.
 */
public abstract class Player {
    private String name; // The name of the player

    /**
     * Constructor to initialize the player with a name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets or updates the player's name.
     *
     * @param name The new name for the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Abstract method to define the player's behavior during the game.
     * This must be implemented by subclasses to define specific player actions.
     */
    public abstract void play();
}
