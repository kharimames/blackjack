package com.kharim.games.blackjack.cards;

import java.util.Arrays;

import com.kharim.games.blackjack.util.BlackJackConstants;

/**
 * Enumeration of cards, their suites, their faces and their values
 *
 * kharim
 */
public enum CARDS {
    //ONE_HEARTS(1, null, SUITES.HEARTS, null),
    TWO_HEARTS(2, null, SUITES.HEARTS, null),
    THREE_HEARTS(3, null, SUITES.HEARTS, null),
    FOUR_HEARTS(4, null, SUITES.HEARTS, null),
    FIVE_HEARTS(5, null, SUITES.HEARTS, null),
    SIX_HEARTS(6, null, SUITES.HEARTS, null),
    SEVEN_HEARTS(7, null, SUITES.HEARTS, null),
    EIGHT_HEARTS(8, null, SUITES.HEARTS, null),
    NINE_HEARTS(9, null, SUITES.HEARTS, null),
    TEN_HEARTS(10, null, SUITES.HEARTS, null),
    JACK_HEARTS(10, null, SUITES.HEARTS, Face.JACK),
    QUEEN_HEARTS(10, null, SUITES.HEARTS, Face.QUEEN),
    KING_HEARTS(10, null, SUITES.HEARTS, Face.KING),
    ACE_HEARTS(11, 1, SUITES.HEARTS, Face.ACE),

    //ONE_DIAMONDS(1, null, SUITES.DIAMONDS, null),
    TWO_DIAMONDS(2, null, SUITES.DIAMONDS, null),
    THREE_DIAMONDS(3, null, SUITES.DIAMONDS, null),
    FOUR_DIAMONDS(4, null, SUITES.DIAMONDS, null),
    FIVE_DIAMONDS(5, null, SUITES.DIAMONDS, null),
    SIX_DIAMONDS(6, null, SUITES.DIAMONDS, null),
    SEVEN_DIAMONDS(7, null, SUITES.DIAMONDS, null),
    EIGHT_DIAMONDS(8, null, SUITES.DIAMONDS, null),
    NINE_DIAMONDS(9, null, SUITES.DIAMONDS, null),
    TEN_DIAMONDS(10, null, SUITES.DIAMONDS, null),
    JACK_DIAMONDS(10, null, SUITES.DIAMONDS, Face.JACK),
    QUEEN_DIAMONDS(10, null, SUITES.DIAMONDS, Face.QUEEN),
    KING_DIAMONDS(10, null, SUITES.DIAMONDS, Face.KING),
    ACE_DIAMONDS(11, 1, SUITES.DIAMONDS, Face.ACE),

    //ONE_SPADES(1, null, SUITES.SPADES, null),
    TWO_SPADES(2, null, SUITES.SPADES, null),
    THREE_SPADES(3, null, SUITES.SPADES, null),
    FOUR_SPADES(4, null, SUITES.SPADES, null),
    FIVE_SPADES(5, null, SUITES.SPADES, null),
    SIX_SPADES(6, null, SUITES.SPADES, null),
    SEVEN_SPADES(7, null, SUITES.SPADES, null),
    EIGHT_SPADES(8, null, SUITES.SPADES, null),
    NINE_SPADES(9, null, SUITES.SPADES, null),
    TEN_SPADES(10, null, SUITES.SPADES, null),
    JACK_SPADES(10, null, SUITES.SPADES, Face.JACK),
    QUEEN_SPADES(10, null, SUITES.SPADES, Face.QUEEN),
    KING_SPADES(10, null, SUITES.SPADES, Face.KING),
    ACE_SPADES(11, 1, SUITES.SPADES, Face.ACE),

    //ONE_CLUBS(1, null, SUITES.CLUBS, null),
    TWO_CLUBS(2, null, SUITES.CLUBS, null),
    THREE_CLUBS(3, null, SUITES.CLUBS, null),
    FOUR_CLUBS(4, null, SUITES.CLUBS, null),
    FIVE_CLUBS(5, null, SUITES.CLUBS, null),
    SIX_CLUBS(6, null, SUITES.CLUBS, null),
    SEVEN_CLUBS(7, null, SUITES.CLUBS, null),
    EIGHT_CLUBS(8, null, SUITES.CLUBS, null),
    NINE_CLUBS(9, null, SUITES.CLUBS, null),
    TEN_CLUBS(10, null, SUITES.CLUBS, null),
    JACK_CLUBS(10, null, SUITES.CLUBS, Face.JACK),
    QUEEN_CLUBS(10, null, SUITES.CLUBS, Face.QUEEN),
    KING_CLUBS(10, null, SUITES.CLUBS, Face.KING),
    ACE_CLUBS(11, 1, SUITES.CLUBS, Face.ACE);

    private final Integer value;
    private final Integer optValue;
    private final SUITES suite;
    private final Face face;

    /**
     * ctor
     *
     * @param value Score of the card
     * @param optValue Optional score of the card (e.g. Ace can be 11 or 1)
     * @param suite Suite (Ace, Clubs, Diamonds or Spades)
     * @param face Face (King, Queen, Jack or Ace)
     */
    CARDS(Integer value, Integer optValue, SUITES suite, Face face) {
        this.value = value;
        this.optValue = optValue;
        this.suite = suite;
        this.face = face;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getOptValue() {
        return optValue;
    }

    public SUITES getSuite() {
        return suite;
    }

    public Face getFace() {
        return face;
    }

    /**
     * Calculates all of the possible scores given one
     * hand
     *
     * @param numOfDecks Number of decks in this game
     * @param cards Cards in a player's hand
     * @return All possible scores given the number of
     *          decks in the dealer's shoe and the cards
     *          in a hand
     */
    public static int[] calculateValue(int numOfDecks, CARDS... cards) {
        int[] scores = new int[numOfDecks * 4]; // Account for the total number of 'aces' in a hand
        int aceCount = 0;
        for (CARDS card : cards) {
            scores[0] += card.getValue();
            if (card.getFace() == Face.ACE)
                aceCount++;
        }
        if (aceCount > 0) {
            for (int idx = 1; idx <= aceCount; idx++) {
                scores[idx] = scores[0] - (Face.ACE.getDiffValue() * idx);
            }
        }
        // Remove 0-ed elements
        return Arrays.stream(scores).filter(i -> i != 0).sorted().toArray();
    }

    /**
     * Utility method for checking for a given face card
     *
     * @param face Face that we're looking for
     * @param cards Array of cards that we want to look in
     * @return True is face is in array of cards
     */
    public static boolean hasFace(Face face, CARDS... cards) {
        if (face == null)
            throw new IllegalArgumentException("Face cannot be null");
        for (CARDS card : cards) {
            if (card.getFace() == face)
                return true;
        }
        return false;
    }

    /**
     * Check if any of the cards is an Ace
     *
     * @param cards Array of cards
     * @return True is any if the cards is an Ace
     */
    public static boolean hasAce(CARDS... cards) {
        return hasFace(Face.ACE, cards);
    }

    /**
     * Prints out the scores
     *
     * @param playerId Player to display the score for
     * @param numOfDecks Number of decks in the game
     * @param cards Cards to calculate the scores for
     * @return String with legible possible scores for a players hand
     */
    public static String prettyPrint(String playerId, int numOfDecks, CARDS... cards) {
        String print = playerId + "'s hand: " + Arrays.toString(cards);
        // Exclude scores over 21 when printing
        print += "\nScores: " + Arrays.toString(calculateValue(numOfDecks, cards));
        //print += "\nScores: " + Arrays.toString(Arrays.stream(calculateValue(numOfDecks, cards))
                //.filter(i -> i <= BlackJackConstants.BLACKJACK).toArray()); // Exclude scores higher than blackjack (traditionally 21)
                //.max()  // Show only the max value
                //.getAsInt();
        print += "\n";
        return print;
    }

    @Override
    public String toString() {
        return "CARDS [" +
                "value=" + value +
                ", optValue=" + optValue +
                ", suite=" + suite +
                ", face=" + face +
                ", name=" + this.name() +
                ']';
    }
}