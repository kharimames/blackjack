package com.kharim.games.blackjack.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * kharim
 */
public class CardPopulationTest {
    @Test
    public void TestDealerShoeOrder() {
        Integer[] nums = new Integer[]{3, 35, 7, 12, 20};
        Collections.shuffle(Arrays.asList(nums));
        System.out.println(Arrays.toString(nums));

        Deque<Integer> deque = new ArrayDeque<>();
        for (Integer num : nums) {
            deque.addLast(num);
        }
        System.out.println(deque);
        int i = 0;
        while(!deque.isEmpty()) {
            assertThat(deque.pop(), is(nums[i++]));
        }
    }
}
