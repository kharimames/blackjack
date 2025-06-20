package com.kharim.games.blackjack.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(95 * SUITES.values().length, value);
    }
}
