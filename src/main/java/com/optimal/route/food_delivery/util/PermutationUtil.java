package com.optimal.route.food_delivery.util;

import java.util.ArrayList;
import java.util.List;

public class PermutationUtil {
    public static void generateValidPermutations(int n, List<Integer> currentPerm,
                                                  boolean[] restaurantVisited, boolean[] customerVisited, List<List<Integer>> permutations) {
        if (currentPerm.size() == 2 * n) {
            permutations.add(new ArrayList<>(currentPerm));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!restaurantVisited[i]) {
                // Visit restaurant
                restaurantVisited[i] = true;
                currentPerm.add(i);
                generateValidPermutations(n, currentPerm, restaurantVisited, customerVisited, permutations);
                currentPerm.remove(currentPerm.size() - 1);
                restaurantVisited[i] = false;
            }
            if (restaurantVisited[i] && !customerVisited[i]) {
                // Visit customer
                customerVisited[i] = true;
                currentPerm.add(i + n);
                generateValidPermutations(n, currentPerm, restaurantVisited, customerVisited, permutations);
                currentPerm.remove(currentPerm.size() - 1);
                customerVisited[i] = false;
            }
        }
    }

}
