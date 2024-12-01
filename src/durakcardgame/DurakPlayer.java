package durakcardgame;

import java.util.ArrayList;

/**
 * Represents a player in the Durak card game.
 * Each player has a hand of cards and can perform actions such as taking, playing, or showing cards.
 */
public class DurakPlayer extends Player {
    private ArrayList<Card> hand; // The player's hand of cards

    /**
     * Constructor to create a new player with the specified name.
     *
     * @param name The name of the player.
     */
    public DurakPlayer(String name) {
        super(name); // Call the superclass constructor to set the player's name
        this.hand = new ArrayList<>(); // Initialize the player's hand as an empty list
    }

    /**
     * Adds a single card to the player's hand.
     *
     * @param card The card to add.
     */
    public void takeCard(Card card) {
        hand.add(card);
    }

    /**
     * Adds multiple cards to the player's hand.
     *
     * @param cards The list of cards to add.
     */
    public void takeCards(ArrayList<Card> cards) {
        hand.addAll(cards);
    }

    /**
     * Plays a card from the player's hand at the specified index.
     * The card is removed from the hand and returned.
     *
     * @param index The index of the card to play.
     * @return The played card, or null if the index is invalid.
     */
    public Card playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            return hand.remove(index); // Remove and return the card at the specified index
        }
        return null; // Return null if the index is invalid
    }

    /**
     * Returns the number of cards currently in the player's hand.
     *
     * @return The size of the hand.
     */
    public int getHandSize() {
        return hand.size();
    }

    /**
     * Returns the player's hand as a list of cards.
     *
     * @return The list of cards in the player's hand.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Returns a string representation of the player's hand for display purposes.
     *
     * @return The string representation of the player's hand.
     */
    public String showHand() {
        return hand.toString();
    }

    /**
     * This method is not used in the Durak game, but is required by the superclass.
     * It can be left empty or overridden for specific implementations if needed.
     */
    @Override
    public void play() {
        // Not needed for this implementation
    }
}
