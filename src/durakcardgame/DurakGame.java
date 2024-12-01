package durakcardgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents the core gameplay mechanics of the Durak card game.
 * Handles player actions, card distribution, and game flow.
 */

public class DurakGame extends Game {
    private Deck deck; // The deck of cards for the game
    private Card trumpCard; // The trump card that determines the trump suit
    private int attackerIndex; // The index of the current attacker
    private int defenderIndex; // The index of the current defender
    private ArrayList<Card> tableCards; // Cards currently on the table
    private boolean defenderSkipped = false; // Tracks if the defender chose to skip their turn

   /**
     * Initializes the Durak game with the specified name and player names.
     *
     * @param name        The name of the game.
     * @param playerNames The names of the players participating.
     */
    
    public DurakGame(String name, String[] playerNames) {
        super(name);
        this.deck = new Deck(); // Create a new deck
        this.deck.shuffle(); // Shuffle the deck
        this.trumpCard = deck.drawCard(); // Draw the trump card
        deck.addTrumpCardBack(trumpCard); // Place the trump card at the bottom of the deck
        System.out.println("Trump card: " + trumpCard);
        System.out.println("Trump suit: " + trumpCard.getSuit());

        this.tableCards = new ArrayList<>(); // Initialize the table cards

        // Add players to the game
        for (String playerName : playerNames) {
            addPlayer(new DurakPlayer(playerName));
        }

        // Distribute 6 cards to each player
        for (Player player : getPlayers()) {
            DurakPlayer p = (DurakPlayer) player;
            for (int j = 0; j < 6; j++) {
                p.takeCard(deck.drawCard());
            }
        }

        determineFirstAttacker(); // Determine the first attacker
    }

    /**
     * Determines the first attacker based on the smallest trump card.
     */
    private void determineFirstAttacker() {
        Card smallestTrump = null;
        DurakPlayer firstAttacker = null;

        // Iterate through all players to find the smallest trump card
        for (Player player : getPlayers()) {
            DurakPlayer durakPlayer = (DurakPlayer) player;
            for (Card card : durakPlayer.getHand()) {
                if (card.getSuit().equals(trumpCard.getSuit())) {
                    if (smallestTrump == null || card.getRankValue() < smallestTrump.getRankValue()) {
                        smallestTrump = card;
                        firstAttacker = durakPlayer;
                    }
                }
            }
        }
        
        // Assign attacker and defender indices
        if (firstAttacker != null) {
            attackerIndex = getPlayers().indexOf(firstAttacker);
            defenderIndex = (attackerIndex + 1) % getPlayers().size();
            System.out.println(firstAttacker.getName() + " is the first attacker with " + smallestTrump);
        } else {
            attackerIndex = 0;
            defenderIndex = 1;
            System.out.println(getPlayers().get(attackerIndex).getName() + " is the first attacker by default.");
        }
    }

    /**
     * Starts the game loop and handles player actions.
     */

    @Override
    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            displayCurrentHands();

            DurakPlayer attacker = (DurakPlayer) getPlayers().get(attackerIndex);
            DurakPlayer defender = (DurakPlayer) getPlayers().get(defenderIndex);

            if (defenderSkipped) {
                defenderSkipped = false;
                attackerIndex = (attackerIndex + 1) % getPlayers().size();
                defenderIndex = (attackerIndex + 1) % getPlayers().size();
                continue;
            }

            System.out.println(attacker.getName() + " is attacking " + defender.getName() + ".");
            tableCards.clear();

            boolean roundInProgress = true;

            while (roundInProgress) {
                if (tableCards.size() % 2 == 0) { // Attacker's turn
                    System.out.println(attacker.getName() + "'s hand: " + attacker.showHand());
                    System.out.println("Cards on the table: " + tableCards);
                    System.out.print(attacker.getName() + ", choose a card to attack with (index, or -1 to skip): ");
                    int attackIndex = scanner.nextInt();

                    if (attackIndex == -1) {
                        System.out.println(attacker.getName() + " ends their attack.");
                        allowOthersToAddCards(scanner, defender);
                        roundInProgress = false;
                        break;
                    }

                    Card attackCard = attacker.playCard(attackIndex);
                    if (attackCard == null) {
                        System.out.println("Invalid card! Try again.");
                    } else {
                        tableCards.add(attackCard);
                    }
                }

                if (tableCards.size() % 2 != 0 && !defenderSkipped) { // Defender's turn
                    while (true) {
                        System.out.println("Cards on the table: " + tableCards);
                        System.out.println(defender.getName() + "'s hand: " + defender.showHand());
                        System.out.print(defender.getName() + ", choose a card to defend with (index, or -1 to pick up): ");
                        int defendIndex = scanner.nextInt();

                        if (defendIndex == -1) { // Defender gives up
                            System.out.println(defender.getName() + " picks up all cards.");
                            defender.takeCards(new ArrayList<>(tableCards));
                            tableCards.clear();
                            defenderSkipped = true;
                            askOtherPlayersToAddCards(scanner, defender);
                            roundInProgress = false;
                            break;
                        }

                        Card defendCard = defender.playCard(defendIndex);
                        if (isValidDefense(tableCards.get(tableCards.size() - 1), defendCard)) {
                            System.out.println("Successful defense!");
                            tableCards.add(defendCard);
                            break;
                        } else {
                            System.out.println("Invalid defense card! Try again.");
                            if (defendCard != null) {
                                defender.takeCard(defendCard);
                            }
                        }
                    }
                }
            }

            refillAllHands(); // Replenish cards for players
            attackerIndex = (attackerIndex + 1) % getPlayers().size(); // Update attacker
            defenderIndex = (attackerIndex + 1) % getPlayers().size(); // Update defender
        }

        declareWinner(); // End the game and declare the winner
    }
    
    /**
     * Allows other players (excluding the defender) to add cards to the table.
     *
     * @param scanner  Scanner object for user input.
     * @param defender The current defending player.
     */
    private void allowOthersToAddCards(Scanner scanner, DurakPlayer defender) {
        boolean cardsAdded = false; // Flag to check if any cards were added
        Set<Player> alreadyAsked = new HashSet<>(); // Tracks players already asked to add cards

        for (Player player : getPlayers()) {
            if (player != defender && !alreadyAsked.contains(player)) { // Skip defender and already-asked players
                DurakPlayer addingPlayer = (DurakPlayer) player;
                System.out.println("Cards on the table: " + tableCards);
                System.out.println(addingPlayer.getName() + "'s hand: " + addingPlayer.showHand());
                System.out.print(addingPlayer.getName() + ", do you want to add a card? (index, or -1 to skip): ");
                int throwIndex = scanner.nextInt();

                if (throwIndex == -1) { // Player chooses to skip
                    alreadyAsked.add(addingPlayer);
                    continue;
                }

                Card throwCard = addingPlayer.playCard(throwIndex);
                if (throwCard != null && isValidThrow(throwCard)) { // Validate the added card
                    tableCards.add(throwCard);
                    cardsAdded = true;
                    System.out.println("Cards on the table: " + tableCards);
                } else { // Invalid card logic
                    System.out.println("Invalid card for throwing! Try again.");
                    if (throwCard != null) {
                        addingPlayer.takeCard(throwCard); // Return the card back to player's hand
                    }
                }
            }
        }

        if (!cardsAdded) {
            System.out.println("No additional cards were added.");
        }
    }

    /**
     * Asks other players to add cards to the table after the defender chooses to pick up cards.
     *
     * @param scanner  Scanner object for user input.
     * @param defender The current defending player.
     */
    private void askOtherPlayersToAddCards(Scanner scanner, DurakPlayer defender) {
        boolean cardsAdded = false; // Flag to track card additions
        Set<Player> alreadyAsked = new HashSet<>(); // Tracks players already asked to add cards

        for (Player player : getPlayers()) {
            if (player != defender && !alreadyAsked.contains(player)) { // Skip defender and already-asked players
                DurakPlayer addingPlayer = (DurakPlayer) player;
                System.out.println(addingPlayer.getName() + "'s hand: " + addingPlayer.showHand());
                System.out.println("Cards on the table: " + tableCards);
                System.out.print(addingPlayer.getName() + ", do you want to add a card? (index, or -1 to skip): ");
                int throwIndex = scanner.nextInt();

                if (throwIndex == -1) { // Player chooses to skip
                    alreadyAsked.add(addingPlayer);
                    continue;
                }

                Card throwCard = addingPlayer.playCard(throwIndex);
                if (throwCard != null && isValidThrow(throwCard)) { // Validate the added card
                    tableCards.add(throwCard);
                    cardsAdded = true;
                    System.out.println("Cards on the table: " + tableCards);
                } else { // Invalid card logic
                    System.out.println("Invalid card for throwing! Try again.");
                    if (throwCard != null) {
                        addingPlayer.takeCard(throwCard); // Return the card back to player's hand
                    }
                }
            }
        }

        if (!cardsAdded) {
            System.out.println("No additional cards were added.");
        }
    }

    /**
     * Displays the current hands of all players and the trump card.
     */
    private void displayCurrentHands() {
        System.out.println("\n--- Current Hands ---");
        for (Player player : getPlayers()) {
            DurakPlayer durakPlayer = (DurakPlayer) player;
            System.out.println(durakPlayer.getName() + "'s hand: " + durakPlayer.showHand());
        }
        System.out.println("Trump card: " + trumpCard);
        System.out.println("---------------------");
    }

    /**
     * Declares the winner of the game. The last player with cards is the "Durak" (loser).
     */
    @Override
    public void declareWinner() {
        System.out.println("Game over!");
        for (Player player : getPlayers()) {
            if (((DurakPlayer) player).getHandSize() > 0) { // Check if the player still has cards
                System.out.println(player.getName() + " is the Durak (loser)!");
                return;
            }
        }
    }

    /**
     * Validates whether the defending card can beat the attacking card.
     *
     * @param attackCard The attacking card.
     * @param defendCard The defending card.
     * @return True if the defense is valid, false otherwise.
     */
    private boolean isValidDefense(Card attackCard, Card defendCard) {
        return defendCard != null &&
                ((defendCard.getSuit().equals(attackCard.getSuit()) && defendCard.getRankValue() > attackCard.getRankValue())
                        || defendCard.getSuit().equals(trumpCard.getSuit()));
    }

    /**
     * Validates whether a card can be thrown onto the table.
     *
     * @param card The card to be validated.
     * @return True if the card is valid for throwing, false otherwise.
     */
    private boolean isValidThrow(Card card) {
        for (Card tableCard : tableCards) {
            if (tableCard.getRank().equals(card.getRank())) { // Check if ranks match any card on the table
                return true;
            }
        }
        return false;
    }

    /**
     * Refills each player's hand to 6 cards if the deck is not empty.
     */
    private void refillAllHands() {
        for (Player player : getPlayers()) {
            DurakPlayer durakPlayer = (DurakPlayer) player;
            while (durakPlayer.getHandSize() < 6 && !deck.isEmpty()) {
                durakPlayer.takeCard(deck.drawCard()); // Draw cards until the player has 6 cards
            }
        }
    }

    /**
     * Determines if the game is over. The game ends when only one player has cards left.
     *
     * @return True if the game is over, false otherwise.
     */
    @Override
    public boolean isGameOver() {
        int playersWithCards = 0;
        for (Player player : getPlayers()) {
            if (((DurakPlayer) player).getHandSize() > 0) { // Count players with cards
                playersWithCards++;
            }
        }
        return playersWithCards <= 1; // Game ends if only one player has cards
    }
}
