package com.kharim.games.blackjack.players;

import com.kharim.games.blackjack.bets.BetType;
import com.kharim.games.blackjack.cards.CARDS;

/**
 * kharim
 */
public interface IPlayer {
    public PlayerType getPlayerType();
    public CARDS[] getHand();
    public void addCard(CARDS card);
    public void addCards(CARDS... cards);
    public void resetHand();
    public Double getBalance();
    public void updateBalance(Double delta);
    public String getId();
    public void calculateWinnings(Double bet, BetType betType);// Default is DOUBLE
    public boolean isActive();
}
