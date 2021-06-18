import java.util.ArrayList;
import java.util.List;

class RodCutting {
  public static void main(String[] args) {
    ArrayList<Integer> p1 = new ArrayList<Integer>(List.of(1, 5, 8, 9, 10, 17, 17, 20))
  }

  // returns the maximum value that can be obtained from cutting a rod
  // given a rod of length n inches
  // and an array list of prices that includes prices of all pieces of size
  // smaller than n
  private static int cuttingRod(ArrayList<Integer> prices, int n) {
    // add base case of a rod with 0 length and a price of 0
    prices.add(0, 0);
    return cuttingRod(prices, n, 0);
  }

  // returns the maximum value that can be obtained from cutting a rod
  // given a rod of length n inches
  // and an array list of prices that includes prices of all pieces of size
  // smaller than n
  // and an index of the current rod we are deciding whether or not to cut
  private static int cuttingRod(ArrayList<Integer> prices, int n, int i) {
    int currPrice = prices.remove(i);
    // make a binary choice for each rod assuming that the rest of the problem is
    // solved and choose the optimal result
    // note: this allows us to not have to deal with repeats in all 2^n possible
    // combinations of rods, as
    // cutting a rod with lengths 2,7,8 is the same as cutting a rod with lengths
    // 7,8,2
    int choiceOfNotCuttingCurrRod = cuttingRod(prices, n, i + 1);
    int choiceOfCuttingCurrRod = currPrice + cuttingRod(prices, n - i, i + 1);
    return Math.max(choiceOfNotCuttingCurrRod, choiceOfCuttingCurrRod);
  }
}