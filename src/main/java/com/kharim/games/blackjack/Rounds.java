package com.kharim.games.blackjack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kharim.games.blackjack.bets.BetType;
import com.kharim.games.blackjack.bets.BettingBox;
import com.kharim.games.blackjack.players.Dealer;
import com.kharim.games.blackjack.players.IPlayer;
import com.kharim.games.blackjack.players.PlayerType;

/**
 * Round object, contains the players and their bets
 *
 * kharim
 */
public class Rounds {
    //private IPlayer[] players_;
    private Map<String, IPlayer> players_ = new HashMap<>();
    private Map<String, BettingBox> playersBets_ = new HashMap<>();
    private boolean blackjackScored_ = false;
    private final IPlayer dealer_ = new Dealer();

    public void updatePlayers(IPlayer player) {
        players_.put(player.getId(), player);
    }

    /**
     * Returns the list of players in the round
     *
     * @return Map of players
     */
    public Map<String, IPlayer> getPlayers() {
        // Return defensive copy of players_ map
        return new HashMap<>(players_);
    }

    public IPlayer getDealer() {
        return dealer_;
    }

    /**
     * Clears all bets to start a new round
     *
     */
    public void resetBets() {
        for (IPlayer player : players_.values())
            player.resetHand();
        this.dealer_.resetHand();
        playersBets_.clear();
        blackjackScored_ = false;
    }

    public BettingBox getBettingBoxForPlayer(IPlayer player) {
        return playersBets_.get(player.getId());
    }

    public BettingBox getBettingBoxForPlayer(String playerId) {
        return playersBets_.get(playerId);
    }

    /**
     * Updates the bet for the player after a round based on the bet type
     *
     * @param player Player placing the bet
     * @param betType Type of bet e.g. STAND, BUST, BLACKJACK
     * @param score Points scored
     */
    public void updateBetType(IPlayer player, BetType betType, int score) {
        if (betType == BetType.BLACKJACK)
            blackjackScored_ = true;
        BettingBox bettingBox = this.playersBets_.get(player.getId());
        if (bettingBox == null)
            bettingBox = new BettingBox(0d, betType, score);
        else {
            bettingBox.setBetType(betType);
            bettingBox.setScore(score);
        }
        //TODO Does this work without the put as bettingbox is passed by reference
        //this.updateBets(player, bettingBox);
    }

    /**
     * Records the bet placed by the player per round
     *
     * @param player Player placing the bet
     * @param bettingBox Betting box (with the amount of the bet)
     */
    public void updateBets(IPlayer player, BettingBox bettingBox) {
        playersBets_.put(player.getId(), bettingBox);
        if (bettingBox.getBetType() == BetType.BLACKJACK)
            blackjackScored_ = true;
    }

    //TODO How do we handle the double blackjack???
    /**
     * Determines the winner for a given round
     *
     */
    public void determineWinner() {
        if (this.playersBets_.isEmpty())
            throw new IllegalStateException("Can't determine a winner for a round with no bets");
        // Everyone who has blackjack should get 1.5 of their bet
        if (blackjackScored_) {
            for (Map.Entry<String, BettingBox> entry : playersBets_.entrySet()) {
                IPlayer player = this.players_.get(entry.getKey());
                if (player != null && player.getPlayerType() == PlayerType.PLAYER) {
                    BettingBox bettingBox = entry.getValue();
                    // Find which betting boxes have black jack and set their balances accordingly
                    if (BetType.BLACKJACK == bettingBox.getBetType()) {
                        player.calculateWinnings(bettingBox.getBet(), bettingBox.getBetType()); // We already know the betting type, should refactor
                        System.out.println(player.getId() + " has blackjack");
                    }
                    // Take away bets from players who lost
                    else {
                        player.calculateWinnings(bettingBox.getBet(), BetType.LOST);
                    }
                }
            }
        }
        else {
            // If no one has black jack, find the winner
            int maxScore = this.maxScore();
            for (Map.Entry<String, BettingBox> entry : playersBets_.entrySet()) {
                IPlayer player = players_.get(entry.getKey());
                if (player != null && player.getPlayerType() == PlayerType.PLAYER) {
                    BettingBox bettingBox = entry.getValue();
                    if (maxScore == bettingBox.getScore()) {
                        player.calculateWinnings(bettingBox.getBet(), BetType.DOUBLE); // Check if player took insurance or dealer match
                    } else {
                        player.calculateWinnings(bettingBox.getBet(), BetType.LOST);
                    }
                }
            }
        }
    }

    /**
     * Get the maximum score out of the player's bets
     *
     * @return Maximum score for the round
     */
    private int maxScore() {
        int max = 0;
        for (BettingBox bettingBox : playersBets_.values()) {
            if (bettingBox.getScore() > max)
                max = bettingBox.getScore();
        }
        return max;
    }

    /**
     * Get the maximum score out of the player's bets minus the dealer
     *
     * @return Maximum score for the players in the round excluding the dealer
     */
    public int maxPlayersScore() {
        int max = 0;
        for (Map.Entry<String, BettingBox> playerBet : playersBets_.entrySet()) {
            // ContainsKey in case we want to support players leaving the table during the round
            if (this.getPlayers().containsKey(playerBet.getKey()) && this.getPlayers().get(playerBet.getKey()).getPlayerType() != PlayerType.DEALER) { // Just confirm that player is not a dealer, although no dealers are in this map
                if (playerBet.getValue().getScore() > max)
                    max = playerBet.getValue().getScore();
            }
        }
        return max;
    }

    /**
     * Returns the players in the round in the order on the
     * table
     *
     * @return List of players in order of table
     */
    public List<IPlayer> getTable() {
        List<IPlayer> table = new ArrayList<>();
        for (IPlayer player : players_.values())
            table.add(player);
        // Ensure dealer is last element in the array
        table.add(dealer_);
        return table;
    }

    /**
     * Displays the players balances in a legible format
     *
     * @return String printout of balances of players in the round
     */
    public String printBalances() {
        StringBuilder print = new StringBuilder("Balances as of current round:\n");
        for (IPlayer player : players_.values()) {
            print.append(String.format("%s balance: $%.2f\n", player.getId(), player.getBalance()));
        }
        return print.toString();
    }

    public void updatePlayersAccounts() {
        printBalances();
        for (IPlayer player : players_.values()) {
            System.out.println(player + ": Would you like to update your account?");
        }
    }
}