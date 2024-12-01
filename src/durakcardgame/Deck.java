package durakcardgame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a deck of cards used in the Durak card game.
 * The deck consists of all standard playing cards (52 cards),
 * and provides methods for shuffling, drawing, and managing the deck.
 */
public class Deck {
    // Private attribute to store the list of cards in the deck
    private ArrayList<Card> cards;

    /**
     * Constructs a standard 52-card deck with all suits and ranks.
     * Suits: Hearts, Diamonds, Clubs, Spades.
     * Ranks: 2, 3, ..., 10, Jack, Queen, King, Ace.
     */
    public Deck() {
        this.cards = new ArrayList<>(); // Initialize the deck
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        // Populate the deck with all combinations of suits and ranks
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank)); // Create and add a new card to the deck
            }
        }
    }

    /**
     * Shuffles the deck using the Collections.shuffle method.
     * Ensures that the card order is randomized for fair gameplay.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return The card drawn from the top of the deck, or null if the deck is empty.
     */
    public Card drawCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    /**
     * Adds a card back to the bottom of the deck.
     * Typically used to place the trump card at the bottom after it is revealed.
     *
     * @param card The card to be added back to the deck.
     */
    public void addTrumpCardBack(Card card) {
        cards.add(card);
    }

    /**
     * Gets the number of cards remaining in the deck.
     *
     * @return The number of cards left in the deck.
     */
    public int getRemainingCards() {
        return cards.size();
    }

    /**
     * Checks if the deck is empty.
     *
     * @return True if the deck has no cards left, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
