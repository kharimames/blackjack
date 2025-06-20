package com.kharim.games.blackjack.cards;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * kharim
 */
public class ScoreCalculatorTest {
    private final static int NUM_OF_CARD_DECKS = 4;
    @Test
    public void testBasicScoreCalc() {
        CARDS twoOfClubs = CARDS.TWO_CLUBS;
        CARDS kingOfHearts = CARDS.KING_HEARTS;
        // Score here would be 12
        int[] results = {twoOfClubs.getValue() + kingOfHearts.getValue()};
        int[] scores = CARDS.calculateValue(NUM_OF_CARD_DECKS, twoOfClubs, kingOfHearts);
        assertThat(scores).isEqualTo(results);
    }

    @Test
    public void testAceScoreCalc() {
        CARDS eightOfSpades = CARDS.EIGHT_SPADES;
        CARDS aceOfDiamonds = CARDS.ACE_DIAMONDS;
        // Possible scores here are 19 and 9
        int[] results = {
                eightOfSpades.getValue() + aceOfDiamonds.getValue(),
                eightOfSpades.getValue() + aceOfDiamonds.getOptValue()
        };
        Arrays.sort(results);
        int[] scores = CARDS.calculateValue(NUM_OF_CARD_DECKS, aceOfDiamonds, eightOfSpades);
        Arrays.sort(scores);
        assertThat(scores).isEqualTo(results);
    }

    /**
     * This is not likely but we should test all possibilities
     *
     */
    @Test
    public void testFourAcesCalc() {
        CARDS ace1 = CARDS.ACE_CLUBS;
        CARDS ace2 = CARDS.ACE_DIAMONDS;
        CARDS ace3 = CARDS.ACE_HEARTS;
        CARDS ace4 = CARDS.ACE_SPADES;
        int[] results = {
                ace1.getValue() + ace2.getValue() + ace3.getValue() + ace4.getValue(),
                ace1.getValue() + ace2.getValue() + ace3.getValue() + ace4.getOptValue(),
                ace1.getValue() + ace2.getValue() + ace3.getOptValue() + ace4.getOptValue(),
                ace1.getValue() + ace2.getOptValue() + ace3.getOptValue() + ace4.getOptValue(),
                ace1.getOptValue() + ace2.getOptValue() + ace3.getOptValue() + ace4.getOptValue(),
        };
        Arrays.sort(results);
        int[] scores = CARDS.calculateValue(NUM_OF_CARD_DECKS, ace1, ace2, ace3, ace4);
        Arrays.sort(scores);
        assertThat(scores).isEqualTo(results);
    }

    /**
     * This is not likely but we should test all possibilities
     *
     */
    @Test
    public void testFourAcesSameSuiteCalc() {
        CARDS ace1 = CARDS.ACE_CLUBS;
        CARDS ace2 = CARDS.ACE_CLUBS;
        CARDS ace3 = CARDS.ACE_CLUBS;
        CARDS ace4 = CARDS.ACE_CLUBS;
        int[] results = {
                ace1.getValue() + ace2.getValue() + ace3.getValue() + ace4.getValue(),
                ace1.getValue() + ace2.getValue() + ace3.getValue() + ace4.getOptValue(),
                ace1.getValue() + ace2.getValue() + ace3.getOptValue() + ace4.getOptValue(),
                ace1.getValue() + ace2.getOptValue() + ace3.getOptValue() + ace4.getOptValue(),
                ace1.getOptValue() + ace2.getOptValue() + ace3.getOptValue() + ace4.getOptValue(),
        };
        Arrays.sort(results);
        int[] scores = CARDS.calculateValue(NUM_OF_CARD_DECKS, ace1, ace2, ace3, ace4);
        Arrays.sort(scores);
        assertThat(scores).isEqualTo(results);
    }
}
