package com.kharim.games.blackjack.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.kharim.games.blackjack.cards.CARDS;

/**
 * Class to create the dealer shoe after shuffling the deck(s)
 *
 * kharim
 */
public class DealerShoeRetrieverImpl implements DealerShoeRetriever {
    private final CARDS[] dealersShoe;

    // Convert this to static method, no need for an instance
    public DealerShoeRetrieverImpl(int numOfPacks) {
        List<CARDS> allCards = new ArrayList<>();
        for (int i = 0; i < numOfPacks; i++) {
            CARDS[] deck = CARDS.values();
            // Shuffle each 'pack'
            Collections.shuffle(Arrays.asList(deck), new Random());
            allCards.addAll(Arrays.asList(deck));
        }
        
        if (allCards.isEmpty()) {
            throw new IllegalArgumentException("Dealer shoe shouldn't be empty");
        }

        // Shuffle all of the cards in the dealer shoe
        Collections.shuffle(allCards, new Random());
        this.dealersShoe = allCards.toArray(new CARDS[0]);
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