package durakcardgame;

/**
 * Represents a single card in the Durak card game.
 * Each card has a suit (e.g., Hearts) and a rank (e.g., 6, 7, Ace).
 */
public class Card {
    // Private attributes to ensure encapsulation
    private String suit; // The suit of the card (e.g., Hearts, Diamonds, Clubs, Spades)
    private String rank; // The rank of the card (e.g., 6, 7, Jack, Ace)

    /**
     * Constructor for creating a new card with a given suit and rank.
     *
     * @param suit The suit of the card (e.g., Hearts, Diamonds).
     * @param rank The rank of the card (e.g., 6, Jack, Ace).
     */
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return The suit of the card.
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return The rank of the card.
     */
    public String getRank() {
        return rank;
    }

    /**
     * Retrieves the rank value of the card for comparison purposes.
     * Cards are ranked in ascending order: 6 < 7 < 8 ... < King < Ace.
     *
     * @return The numeric value of the rank (6 = 0, Ace = 8).
     * Returns -1 if the rank is invalid.
     */
    public int getRankValue() {
        // Array to define the rank hierarchy
        String[] ranks = {"6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i; // Return the index as the rank value
            }
        }
        return -1; // Invalid rank
    }

    /**
     * Provides a string representation of the card in the format "Rank of Suit".
     *
     * @return The string representation of the card.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
