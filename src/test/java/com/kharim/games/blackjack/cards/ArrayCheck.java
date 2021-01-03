package com.kharim.games.blackjack.cards;

import java.util.Arrays;
import java.util.Collections;

/**
 * kharim
 */
public class ArrayCheck {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {5, 4, 3, 2, 1};
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        System.out.println(Arrays.equals(arr1, arr2));
    }
}
