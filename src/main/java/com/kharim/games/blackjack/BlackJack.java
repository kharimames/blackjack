/***
 *
 *
 *
 */
package com.kharim.games.blackjack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.kharim.games.blackjack.bets.BetType;
import com.kharim.games.blackjack.bets.BettingBox;
import com.kharim.games.blackjack.bets.Choice;
import com.kharim.games.blackjack.cards.CARDS;
import com.kharim.games.blackjack.players.DealerRule;
import com.kharim.games.blackjack.players.IPlayer;
import com.kharim.games.blackjack.players.Player;
import com.kharim.games.blackjack.players.PlayerType;
import com.kharim.games.blackjack.players.PlayersDecision;
import com.kharim.games.blackjack.util.BlackJackConstants;
import com.kharim.games.blackjack.util.DealerRuleResolver;
import com.kharim.games.blackjack.util.DealerShoeRetriever;
import com.kharim.games.blackjack.util.DealerShoeRetrieverImpl;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Soft 17 rule by default
 *
 *
 * kharim
 *
 * First argument - number of players
 * Use command line parser to initialize
 */
public class BlackJack {
    private static final int DECK_COUNT = CARDS.values().length; // Deck size
    private static final String DEFAULT_NUM_OF_CARD_DECKS = "4";
    private static int numOfCardDecks_;
    private static int numOfPlayers_;
    private static DealerRuleResolver dealerRuleResolver_;
    private static Scanner scanner_;

    public static void main(String[] args) {
        // Parse the arguments accordingly
        parseArgs(args);
        Deque<CARDS> dealerShoe = new ArrayDeque<>(numOfCardDecks_ * DECK_COUNT);
        // Defaulting to soft 17 house rules for now
        dealerRuleResolver_ = new DealerRuleResolver(DealerRule.SOFT_17);

        System.out.println("Number of players is " + numOfPlayers_);
        Rounds rounds = new Rounds();

        // Create deck of shuffled cards for dealer shoe
        DealerShoeRetriever dealerShoeRetriever = new DealerShoeRetrieverImpl(numOfCardDecks_);
        CARDS[] deck = dealerShoeRetriever.getDealersShoe();

        // Add cards to the dealer shoe in the same order
        updateDealerShoe(dealerShoe, deck);
        initializeScanner();

        // Initialize the players and their balances
        try {
            for (int i = 1; i <= numOfPlayers_; i++) {
                String id = "player" + i;
                System.out.println(id + ": please enter initial balance:");
                double balance = scanner_.nextDouble();
                Player player = new Player(id, balance);
                rounds.updatePlayers(player);
            }

            playGame(rounds, dealerShoe);
        }
        finally {
            scanner_.close();
        }
    }

    private static void playerAction(PlayersDecision playersDecision, CARDS[] cards){}

    /**
     * Copy the array of shuffled cards into the
     * dealer shoe
     *
     * @param deque FIFO data structure representing the
     *              dealer shoe
     * @param cards Array of shuffled cards
     */
    private static void updateDealerShoe(Deque<CARDS> deque, CARDS[] cards) {
        for (CARDS card : cards) {
            deque.addLast(card);
        }
    }

    /**
     * Parses the arguments and sets the initial
     * number of players in the starting round
     * of the game
     *
     * @param args
     */
    private static void parseArgs(String[] args) {
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        Option playersOption = OptionBuilder.withArgName("numOfPlayers_")
                .isRequired()
                .withType(Integer.class)
                .hasArg()
                .withDescription("Number of players <int>")
                .create("players");
        Option numOfCardDecksOption = OptionBuilder.withArgName("numOfCardDecks_")
                .isRequired(false)
                .withType(Integer.class)
                .hasArg()
                .withDescription("Number of card decks, default is 4 <int>")
                .create("decks");
        options.addOption(playersOption);
        options.addOption(numOfCardDecksOption);
        try {
            CommandLine cmdLine =  parser.parse(options, args);

            if (cmdLine != null) {
                numOfPlayers_ = Integer.parseInt(cmdLine.getOptionValue("players"));

                numOfCardDecks_ = Integer.parseInt(cmdLine.getOptionValue("decks", DEFAULT_NUM_OF_CARD_DECKS));
            }
        } catch (ParseException parseEx) {
            System.err.println("Exception parsing arguments");
            if (options != null)
                new HelpFormatter().printHelp("blackjack", options);
        } catch (NumberFormatException numEx) {
            System.err.println("Check numeric arguments");
            new HelpFormatter().printHelp("blackjack", options);
        }
    }

    /**
     * Starts the round
     *
     * @param round
     * @param dealerShoe
     */
    private static void playGame(Rounds round, Deque<CARDS> dealerShoe){
        try {
            // Start the game
            while (!dealerShoe.isEmpty()) { //TODO Whats the minimum number of cards that have to be in the pack to cease the game??
                // New round
                System.out.println("Let's begin the round");
                round.resetBets();

                List<IPlayer> players = round.getTable();

                // Record bets for the round
                //TODO Display the balances first for each player

                for (IPlayer player : players) {
                    if (player.getPlayerType() == PlayerType.PLAYER) {
                        boolean isInvalidBet = false;
                        do {
                            // Don't let player play if they have a zero balance
                            if (player.getBalance().doubleValue() == NumberUtils.DOUBLE_ZERO) {
                                System.out.println(player.getId() + " has zero balance, will skip");
                                continue;
                            }
                            System.out.println(player.getId() + " - Please enter your bet:");
                            double bet = scanner_.nextDouble();//TODO Add validation for the bet (should be less than the balance) and make it a string to avoid InputMismatchException
                            if (bet > player.getBalance()) {
                                System.out.println(String.format("Bet ($%.2f) cannot be more than balance ($%.2f)", bet, player.getBalance()));
                                isInvalidBet = true;
                            }
                            else if (bet <= 0) {
                                System.out.println("Bet invalid: " + bet);
                                isInvalidBet = true;
                            }
                            else {
                                BettingBox bettingBox = new BettingBox(bet);
                                round.updateBets(player, bettingBox);
                                isInvalidBet = false; // In case this was made true previously due to invalid entries
                            }
                        } while(isInvalidBet);
                    }
                    else if (player.getPlayerType() == PlayerType.DEALER) {
                        // Instantiate dealers betting box
                        round.updateBets(player, new BettingBox(0d));
                    }
                    else
                        throw new IllegalStateException("Player type not supported here: " + player.getPlayerType());
                }

                // Pop two cards from the shoe per player (including dealer)
                Map<IPlayer, CARDS[]> table = new HashMap<>(); //TODO Change this to a class to get the players and dealer in the table order - apache commons orderedmap???
                for (IPlayer player : players) {
                    // Check if player is inactive i.e. has a zero balance or opted out of the round
                    if (!player.isActive()) {
                        System.out.println(player.getId() + " not in this round, will skip");
                        continue;
                    }

                    // Play the first two cards
                    CARDS card1 = dealerShoe.pop();
                    CARDS card2 = dealerShoe.pop();

                    // Print the cards so the players can see obviously
                    System.out.println(player.getId() + "'s cards: " + card1.name() + " and " + card2.name());
                    System.out.println(player.getId() + "'s score is " + Arrays.toString(CARDS.calculateValue(numOfCardDecks_, card1, card2)));
                    System.out.println();
                    player.addCards(card1, card2);
                    // Remove the line below after we confirm the hand property works
                    table.put(player, new CARDS[]{card1, card2});
                }

                // Get bets from all of the players and store it in the round, this is where we play the game
                for (IPlayer player : players) {
                    if (!player.isActive()) {
                        System.out.println(player.getId() + " not in this round");
                        continue;
                    }
                    boolean playerHasBust = false;
                    boolean playerRequestsStay = false;
                    boolean playerHasBlackJack = false;

                    // Play the first two cards
                    /*CARDS card1 = dealerShoe.pop();
                    CARDS card2 = dealerShoe.pop();
                    CARDS[] cards = new CARDS[21];// Use constant variable
                    ArrayUtils.addAll(cards, card1, card2);
                    System.out.println(player.getId() + "'s cards: " + card1.name() + " and " + card2.name());*/

                    while (!playerHasBust && !playerHasBlackJack && !playerRequestsStay) { // add blackjack flag to while statement
                        // Take the bets from the players given their cards
                        //CARDS[] cards = table.get(player);
                        CARDS[] cards = player.getHand();

                        // Filter out cards that 'bust'
                        int[] scores = Arrays.stream(CARDS.calculateValue(numOfCardDecks_, cards))
                                .filter(i -> i <= BlackJackConstants.BLACKJACK)
                                .toArray();

                        PlayersDecision playersDecision = null;

                        // Check if the player has bust
                        if (scores.length == 0) {
                            round.updateBetType(player, BetType.BUST, 0); // Record bust score
                            System.out.println(CARDS.prettyPrint(player.getId(), numOfCardDecks_, cards));
                            System.out.println(player.getId() + " bust!");
                            playerHasBust = true;
                            continue;
                        }

                        // Check if the player has black jack
                        if (ArrayUtils.contains(scores, BlackJackConstants.BLACKJACK)) {
                            //TODO Check if the bet type is double
                            round.updateBetType(player, BetType.BLACKJACK, BlackJackConstants.BLACKJACK); // Record blackjack score
                            System.out.println(player.getId() + " has blackjack, well done");
                            System.out.println(CARDS.prettyPrint(player.getId(), numOfCardDecks_, cards));
                            playerHasBlackJack = true;
                            continue;
                        }

                        System.out.println(player.getId() + " points: " + Arrays.toString(scores));

                        if (player.getPlayerType() == PlayerType.PLAYER) {
                            System.out.println(player.getId() + ": Hit or stay?");
                            boolean isResponseValid = false;
                            do {
                                System.out.println("Press 1) for hit");
                                System.out.println("Press 2) to stay");
                                int response = parsePlayerResponse();
                                playersDecision = PlayersDecision.getDecisionByCode(response);
                                switch (playersDecision) {
                                    case HIT:
                                        isResponseValid = true;

                                        // For a hit, add a card to the betting box and continue
                                        CARDS card = dealerShoe.pop();
                                        System.out.println(player.getId() + "'s new card: " + card.toString());
                                        player.addCard(card);
                                        //TODO Remove table call below
                                        table.put(player, ArrayUtils.addAll(cards, card));
                                        break;
                                    case STAND:
                                        isResponseValid = true;
                                        playerRequestsStay = true;

                                        System.out.println(player.getId() + " stands");
                                        // Record new score
                                        round.updateBetType(player, BetType.STAND, Arrays.stream(scores).max().getAsInt());
                                        System.out.println(CARDS.prettyPrint(player.getId(), numOfCardDecks_, cards));
                                        break;
                                    case DOUBLE:
                                        //playersDecision = PlayersDecision.DOUBLE;
                                        // Add some logic to double the score if blackjack is scored
                                        //isResponseValid = true;
                                        //break;
                                    case SPLIT:
                                        //playersDecision = PlayersDecision.SPLIT;
                                        //isResponseValid = true;
                                        //break;
                                    case SURRENDER:
                                        //playersDecision = PlayersDecision.SURRENDER;
                                        //isResponseValid = true;
                                        //break;
                                    case INVALID_DECISION:
                                        isResponseValid = false;
                                        break;
                                    default:
                                        // Should never come here
                                        System.out.println("Please enter a valid response");
                                        break;
                                }
                            } while (!isResponseValid);
                            // Player decision recorded - TODO Can we move this logic inside of the switch statement?
                            if (PlayersDecision.HIT.equals(playersDecision)) {
                                // For a hit, add a card to the betting box and continue
                                /*CARDS card = dealerShoe.pop();
                                System.out.println(player.getId() + "'s new card: " + card.toString());
                                player.addCard(card);
                                //TODO Remove table call below
                                table.put(player, ArrayUtils.addAll(cards, card));*/
                            } else if (PlayersDecision.STAND.equals(playersDecision)) {
                                /*System.out.println(player.getId() + " stands");
                                // Record new score
                                round.updateBetType(player, BetType.STAND, Arrays.stream(scores).max().getAsInt());
                                System.out.println(CARDS.prettyPrint(player.getId(), numOfCardDecks_, cards));*/
                            } else
                                throw new IllegalStateException("Unsupported player decision: " + playersDecision.name());
                        }
                        else if (player.getPlayerType() == PlayerType.DEALER) {
                            // Check if the dealer should stand based on the table rules
                            if (dealerRuleResolver_.shouldStand(numOfCardDecks_, cards, round.maxPlayersScore())) {
                                round.updateBetType(player, BetType.STAND, Arrays.stream(scores).max().getAsInt());
                                System.out.println(CARDS.prettyPrint(player.getId(), numOfCardDecks_, cards));
                                playerRequestsStay = true; // or just break???
                            }
                            else {
                                // Pop another card and try again
                                CARDS card = dealerShoe.pop();
                                player.addCard(card);
                                table.put(player, ArrayUtils.addAll(cards, card));
                            }

                        }
                    }
                }
                //Players are finished with round;
                //update the players balances with the bets
                round.determineWinner();
                System.out.println(round.printBalances());
                updatePlayersAccounts(round);
            }
            // Dealer shoe empty -> room for refactoring this out to separate method
        }
        catch (Exception ex) {
            System.err.println("Error caught: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Updates the players accounts between rounds
     *
     * @param rounds Round/game
     */
    private static void updatePlayersAccounts(Rounds rounds) {
        for (IPlayer player : rounds.getPlayers().values()) {
            System.out.println(player.getId() + ": Would you like to update your account (y or n)?");
            // Get choice input from command line
            String choiceInput = scanner_.next();
            // Parse choice to get the enum value
            Choice choice = Choice.parseChoice(choiceInput);

            //TODO Add a max counter to avoid this going in a loop
            while (choice == null) {
                System.out.println(String.format("You entered an invalid choice (%s), please enter y or n!:", choiceInput));
                choiceInput = scanner_.next();
                choice = Choice.parseChoice(choiceInput);
            }

            if (Choice.YES == choice) {
                System.out.println("Please enter additional balance " + player + ":");
                Double balance = scanner_.nextDouble();
                player.updateBalance(balance);
                System.out.println(String.format("%s balance is now $%.2f", player.getId(), player.getBalance()));
            }
        }
    }

    /**
     * Parses the player's response to prevent misatch exceptions
     * from breaking the entire game
     *
     * @return Response from the player's input
     */
    private static int parsePlayerResponse() {
        int response = PlayersDecision.INVALID_DECISION.getDecisionCode();
        try {
            response = scanner_.nextInt();
        } catch (InputMismatchException inputMismatchEx) {
            System.err.println("Invalid response entered, please enter an int");
            initializeScanner();
        }
        return response;
    }

    /**
     * Initiates the scanner using the input stream
     *
     */
    private static void initializeScanner() {
        scanner_ = new Scanner(System.in);
    }
}
