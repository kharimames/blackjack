package com.kharim.games.blackjack.bets;

/**
 * kharim
 */
public final class BettingBox {
    private Double bet;
    private BetType betType;
    private int score;

    public BettingBox(Double bet) {
        this.bet = bet;
        this.setBetType(BetType.BET_NOT_MADE);
    }

    public BettingBox(Double bet, BetType betType, int score) {
        this.bet = bet;
        this.betType = betType;
        this.score = score;
    }

    public Double getBet() {
        return bet;
    }

    public BetType getBetType() {
        return betType;
    }

    public int getScore() {
        return score;
    }

    public void setBet(Double bet) {
        this.bet = bet;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Bet [" +
                "score=" + score +
                ", bet=" + bet +
                ", betType=" + betType.name() +
                ']';
    }
}
