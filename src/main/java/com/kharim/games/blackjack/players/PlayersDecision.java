package com.kharim.games.blackjack.players;

import java.util.HashMap;
import java.util.Map;

/**
 * kharim
 */
public enum PlayersDecision {
    INVALID_DECISION(-1),
    HIT(1),
    STAND(2),
    DOUBLE(3),
    SPLIT(4),
    SURRENDER(5),
    LOST(6);

    private int decisionCode;

    private static Map<Integer, PlayersDecision> playersDecisionMap;

    static {
        playersDecisionMap = new HashMap<>(values().length);
        for (PlayersDecision playersDecision : values()) {
            playersDecisionMap.put(playersDecision.getDecisionCode(), playersDecision);
        }
    }

    PlayersDecision(int decisionCode) {
        this.decisionCode = decisionCode;
    }

    public int getDecisionCode() {
        return decisionCode;
    }

    public static PlayersDecision getDecisionByCode(int decisionCode) {
        return playersDecisionMap.get(decisionCode);
    }

    @Override
    public String toString() {
        return "PlayersDecision{" +
                "name=" + name() +
                "decisionCode=" + decisionCode +
                '}';
    }
}
