package com.kharim.games.blackjack.cards;

/**
 * kharim
 */
public enum SUITES {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    SPADES("Spades"),
    CLUBS("Clubs");

    private String name_;

    SUITES(String name) {
        this.name_ = name;
    }

    public String getName() {
        return name_;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
