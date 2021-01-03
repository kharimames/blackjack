package com.kharim.games.blackjack.cards;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * kharim
 */
public class CardsTest {
    @Test
    public void testCardPopulation() {
        CARDS[] deck = CARDS.values();
        int value = 0;
        for (CARDS card : deck) {
            value += card.getValue();
        }
        assertThat(value, is(95 * SUITES.values().length));
    }
}
