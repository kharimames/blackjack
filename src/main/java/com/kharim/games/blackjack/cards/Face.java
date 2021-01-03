package com.kharim.games.blackjack.cards;

/**
 * kharim
 */
public enum Face {
    JACK(10, null),
    KING(10, null),
    QUEEN(10, null),
    ACE(11, 10);
    private final Integer baseValue;
    private final Integer diffValue;    // Difference between main value and optional value

    Face(Integer baseValue, Integer diffValue) {
        this.baseValue = baseValue;
        this.diffValue = diffValue;
    }

    public Integer getBaseValue() {
        return this.baseValue;
    }

    public Integer getDiffValue() {
        return diffValue;
    }

    @Override
    public String toString() {
        return "Face (" + this.name() + ") [" +
                "baseValue=" + this.baseValue +
                ", diffValue=" + this.diffValue +
                ']';
    }
}
