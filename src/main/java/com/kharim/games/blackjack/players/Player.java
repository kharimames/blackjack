package com.kharim.games.blackjack.players;

import java.util.Arrays;

import com.kharim.games.blackjack.bets.BetType;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * kharim
 */
public class Player extends AbstractPlayer {
    private Double balance = 0d;
    private final String id;
    private boolean isActive;

    /**
     * ctor
     *
     * @param id Player ID
     * @param balance Player balance
     */
    public Player(String id, Double balance) {
        this.id = id;
        this.balance = balance;
        this.isActive = isNotBroken();
    }

    /**
     * Adds the delta to the balance after a round
     *
     * @param delta Balance to increase (or decrease) the balance by
     */
    public void updateBalance(Double delta) {
        balance += delta;
        this.isActive = isNotBroken();
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.PLAYER;
    }

    @Override
    public Double getBalance() {
        return balance;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    private boolean isNotBroken() {
        return NumberUtils.DOUBLE_ZERO.doubleValue() != balance.doubleValue();
    }

    /**
     * Calculate the players balance based on the bet type after a round
     *
     * @param bet Bet placed
     * @param betType Bet type
     */
    @Override
    public void calculateWinnings(Double bet, BetType betType) {
        switch(betType) {
            case BLACKJACK:     this.updateBalance(bet * 1.5);
                break;
            case DOUBLE:        this.updateBalance(bet); //TODO Confirm this
                break;
            case STAND:         this.updateBalance(bet);
                break;
            case BUST:
            case LOST:          this.updateBalance(bet * -1);
                break;
            case DEALER_MATCH:
            case INSURANCE:     throw new UnsupportedOperationException("Pending");
            default:
                throw new IllegalArgumentException("Bet type has to be: " + Arrays.toString(BetType.values()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (balance != null ? !balance.equals(player.balance) : player.balance != null) return false;
        if (id != null ? !id.equals(player.id) : player.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = balance != null ? balance.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Player [" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ']';
    }
}
