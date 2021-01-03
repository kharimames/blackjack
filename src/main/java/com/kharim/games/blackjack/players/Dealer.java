package com.kharim.games.blackjack.players;

import com.kharim.games.blackjack.bets.BetType;

/**
 * kharim
 */
public class Dealer extends AbstractPlayer {


    @Override
    public PlayerType getPlayerType() {
        return PlayerType.DEALER;
    }

    @Override
    public Double getBalance() {
        throw new UnsupportedOperationException("Dealer/bank doesn't have a balance");
    }

    @Override
    public void updateBalance(Double delta) {
        throw new UnsupportedOperationException("Dealer/bank doesn't have a balance to update");
    }

    @Override
    public String getId() {
        return "Dealer/Bank";
    }

    @Override
    public void calculateWinnings(Double bet, BetType betType) {
        throw new UnsupportedOperationException("Dealer/bank doesn't have a balance to calculate winnings");
    }

    /**
     * Dealer is always active
     *
     * @return
     */
    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String toString() {
        return "Dealer [" +
                "id='" + this.getId() + '\'' + ']';
    }
}
