package com.kharim.games.blackjack.cards;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.kharim.games.blackjack.players.IPlayer;
import com.kharim.games.blackjack.players.Player;

/**
 * kharim
 */
public class PlayersMapUpdateSkipTest {
    public static void main(String[] args) {
        Map<String, IPlayer> map = getMap();

        /*for (Map.Entry entry : map.entrySet()) {

        }*/
        for (String name : new String[] {"1", "2", "3"}) {
            Player player = (Player)map.get(name);
            player.updateBalance(player.getBalance() * 3);
        }
        System.out.println(map);

        int[] scores = Arrays.stream(new int[] {30, 100, 25})
                .filter(i -> i <= 21)
                .toArray();
        System.out.println(Arrays.toString(scores));
    }

    private static Map<String, IPlayer> getMap() {
        Map<String, IPlayer> map = new HashMap<>();
        map.put("1", new Player("1", 25d));
        map.put("2", new Player("2", 30d));
        map.put("3", new Player("3", 50d));
        return map;
    }
}
