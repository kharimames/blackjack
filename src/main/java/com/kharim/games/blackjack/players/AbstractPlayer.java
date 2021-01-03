package com.kharim.games.blackjack.players;

import com.kharim.games.blackjack.cards.CARDS;

import org.apache.commons.lang3.ArrayUtils;

/**
 * kharim
 */
public abstract class AbstractPlayer implements IPlayer {
    //TODO What the the max possible number of cards can a hand hold???
    private static final CARDS[] NEW_HAND = new CARDS[0];
    protected CARDS[] hand_ = NEW_HAND;
    @Override
    public CARDS[] getHand() {
        return hand_;
    }

    @Override
    public void addCard(CARDS card) {
        hand_ = ArrayUtils.add(hand_, card);
    }

    @Override
    public void addCards(CARDS... cards) {
        for (CARDS card : cards)
            this.addCard(card);
    }

    @Override
    public void resetHand() {
        this.hand_ = NEW_HAND;
    }
}
