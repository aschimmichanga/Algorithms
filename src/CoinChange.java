import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoinChange {
  public static void main(String[] args) {
    ArrayList<Integer> c1 = new ArrayList<Integer>(List.of(25, 10, 5));
    int v1 = 30;
    // minimum 2 coins required, one 25 coin and one 5 coin

    ArrayList<Integer> c2 = new ArrayList<Integer>(List.of(9, 6, 5, 1));
    int v2 = 11;
    // minimum 2 coins required, one 5 coin and one 6 coin
    // dp approach addresses problems that greedy approach
    // would miss, like in greedy approach this would be minimum 3 coins,
    // one 9 coin and two 1 coins when that is not the optimal solution
  }

  // returns the minimum amount of coins needed to represent the given value
  // given an array of coin dimensions and that there are infinite available coins
  // for each dimension using the Greedy approach
  // (for example, the dimension of a quarter is 25, and there are infinite
  // quarters available in this problem)
  private static int minimumCoinsGreedy(ArrayList<Integer> coins, int value) {
    // sorts from greatest to least
    coins.sort(Collections.reverseOrder());

    // base case, when the complete value has been represented by coins
    if (value == 0) {
      return 0;
    }
    else {
      int currDimension = coins.remove(0);
      int maxCurrCoins = Math.floorDiv(value, currDimension);
      return maxCurrCoins + minimumCoinsGreedy(coins, value - (currDimension * maxCurrCoins));
    }
  }

//returns the minimum amount of coins needed to represent the given value
  // given an array of coin dimensions and that there are infinite available coins
  // for each dimension using the DP approach
  // (for example, the dimension of a quarter is 25, and there are infinite
  // quarters available in this problem)
  private static int minimumCoinsDP(ArrayList<Integer> coins, int value) {
    // sorts from greatest to least
    coins.sort(Collections.reverseOrder());

    // base case, when the complete value has been represented by coins
    if (value == 0) {
      return 0;
    }
    else if (coins.isEmpty()) {
      System.out.println("Complete coin representation not possible");
      return Integer.MIN_VALUE;
    }
    else {
      int currDimension = coins.remove(0);
      int maxCurrCoins = Math.floorDiv(value, currDimension);
      return maxCurrCoins + minimumCoinsGreedy(coins, value - (currDimension * maxCurrCoins));
    }
  }
}
