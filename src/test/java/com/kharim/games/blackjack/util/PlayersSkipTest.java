package com.kharim.games.blackjack.util;

import java.util.Scanner;

import com.kharim.games.blackjack.Rounds;
import com.kharim.games.blackjack.players.IPlayer;
import com.kharim.games.blackjack.players.Player;

/**
 * kharim
 */
public class PlayersSkipTest {
    public static void main(String[] args) {
        int numOfPlayers = 4;
        Rounds rounds = new Rounds();
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= numOfPlayers; i++) {
            String id = "player" + i;
            System.out.println(id + ": please enter initial balance:");
            double balance = scanner.nextDouble();
            Player player = new Player(id, balance);
            rounds.updatePlayers(player);
        }
        System.out.println(rounds.getPlayers());
    }
}
