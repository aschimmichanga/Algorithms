import java.util.ArrayList;
import java.util.List;

class SieveOfEratosthenes {
  public static void main(String[] args) {
    sieve(60);
  }

  // Given a number n, print all primes smaller than or equal to n. It is also
  // given that n is a small, positive number.
  private static void sieve(int n) {
    List<Integer> primes = new ArrayList<Integer>(n + 1);

    // disregard indexes 0 and 1
    primes.add(-1);
    primes.add(-1);

    // make each index from 2 to n + 1 store itself
    for (int i = 2; i < n + 1; i += 1) {
      primes.add(i);
    }

    // at curr starting at 2, mark every multiple of curr that is greater than the
    // square of curr
    // as not a prime AKA disregarded
    int curr = 2;
    while (Math.pow(curr, 2) < n) {
      int sqr = (int) Math.pow(curr, 2);
      for (int i = sqr + curr; i < n + 1; i += curr) {
        primes.set(i, -1);
      }
      curr += 1;
    }

    // filter out all the disregarded numbers and print out the unmarked numbers,
    // which are the primes
    primes.stream().filter(num -> num != -1).forEach(System.out::println);
  }
}
