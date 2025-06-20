package com.kharim.games.blackjack.cards;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(value).isEqualTo(95 * SUITES.values().length);
    }
}
