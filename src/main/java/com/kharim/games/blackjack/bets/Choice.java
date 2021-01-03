package com.kharim.games.blackjack.bets;

/**
 * kharim
 */
public enum Choice {
    YES("Y"),
    NO("N");

    private final String choiceLetter;

    Choice(String choiceLetter) {
        this.choiceLetter = choiceLetter;
    }

    public String getChoiceLetter() {
        return choiceLetter;
    }

    public static Choice parseChoice(String choiceLetter) {
        for (Choice choice : Choice.values()) {
            if (choice.choiceLetter.equalsIgnoreCase(choiceLetter)) {
                return choice;
            }
        }
        return null;
    }
}
