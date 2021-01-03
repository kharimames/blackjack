package com.kharim.games.blackjack.util;

import java.util.Arrays;

import com.kharim.games.blackjack.cards.CARDS;
import com.kharim.games.blackjack.players.DealerRule;

/**
 * kharim
 */
public class DealerRuleResolver {
    private DealerRule dealerRule;

    /**
     * ctor
     *
     * @param dealerRule Dealer rule to use in this game
     */
    public DealerRuleResolver(DealerRule dealerRule) {
        this.dealerRule = dealerRule;
    }

    /**
     * Dealer rule used in the game
     *
     * @return Dealer rule
     */
    public DealerRule getDealerRule() {
        return dealerRule;
    }

    /**
     * Determines if the dealer should stand
     *
     * @param numOfDecks Number of decks in the game
     * @param cards Cards in the dealer's hand
     * @param maxPlayersScore Highest score on the table minus the dealer
     * @return True is the dealer should stand
     */
    public boolean shouldStand(int numOfDecks, CARDS[] cards, int maxPlayersScore) {
        boolean shouldStand = false;
        switch(getDealerRule()) {
            case SOFT_17:
                // We don't get to this code if the dealer bust or got black jack
                int dealerMax = Arrays.stream(CARDS.calculateValue(numOfDecks, cards))
                        .filter(i -> i <= BlackJackConstants.BLACKJACK)
                        .max()
                        .getAsInt();
                if ((dealerMax > maxPlayersScore) || // Dealer has won the table, also handles the case where maxPlayersScore is black jack
                        (CARDS.hasAce(cards) && dealerMax >= BlackJackConstants.DEALER_LIMIT))
                    shouldStand = true;
                break;
            default:
                throw new IllegalArgumentException("Unsupported dealer rule: " + this.dealerRule);
        }
        return shouldStand;
    }
}
