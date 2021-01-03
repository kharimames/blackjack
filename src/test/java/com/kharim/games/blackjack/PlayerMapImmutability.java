package com.kharim.games.blackjack;

import java.util.Map;

import com.kharim.games.blackjack.players.IPlayer;
import com.kharim.games.blackjack.players.Player;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Test immutability of the round object
 *
 * kharim
 */
public class PlayerMapImmutability {
    @Test
    public void testPlayerMapImmutbility() {
        String playerId1 = "1";
        String playerId2 = "2";

        Rounds rounds = new Rounds();
        IPlayer player1 = new Player(playerId1, 100d);
        IPlayer player2 = new Player(playerId2, 20d);

        rounds.updatePlayers(player1);
        rounds.updatePlayers(player2);

        System.out.println(rounds.getPlayers());
        Map<String, IPlayer> map = rounds.getPlayers();

        map.clear();
        System.out.println(rounds.getPlayers());
        assertThat(rounds.getPlayers().size(), is(2));
        assertThat(rounds.getPlayers().get(playerId1).getBalance(), is(player1.getBalance()));
        assertThat(rounds.getPlayers().get(playerId2).getBalance(), is(player2.getBalance()));
    }
}
