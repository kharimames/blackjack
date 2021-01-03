package com.kharim.games.blackjack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.kharim.games.blackjack.cards.CARDS;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Class to create the dealer shoe after shuffling the deck(s)
 *
 * kharim
 */
public class DealerShoeRetrieverImpl implements DealerShoeRetriever {
    private final CARDS[] dealersShoe;

    // Convert this to static method, no need for an instance
    public DealerShoeRetrieverImpl(int numOfPacks) {
        List<CARDS[]> decks = new ArrayList<>();
        for (int i = 0; i < numOfPacks; i++) {
            CARDS[] deck = CARDS.values();
            // Shuffle each 'pack'
            Collections.shuffle(Arrays.asList(deck), new Random());
            decks.add(deck);
        }
        CARDS[] dealersShoe = null;
        for (int i = 0; i < decks.size(); i++) {
            dealersShoe = ArrayUtils.addAll(decks.get(i), dealersShoe);
        }
        if (dealersShoe == null)
            throw new IllegalArgumentException("Dealer shoe shouldn't be empty");

        // Shuffle all of the cards in the dealer shoe
        Collections.shuffle(Arrays.asList(dealersShoe), new Random());
        this.dealersShoe = dealersShoe;
    }

    public CARDS[] getDealersShoe() {
        // Return defensive copy of dealers shoe
        return this.dealersShoe.clone();
    }

    //TODO Remove this
    public static void main(String[] args) {
        DealerShoeRetriever r = new DealerShoeRetrieverImpl(4);
    }
}