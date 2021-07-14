public class BinaryExponentiation {
  public static void main(String[] args) {
    int n1 = 5;
    int n2 = 100;
    int p1 = 4;
    int p2 = 1;
    int p3 = 200;
    int p4 = 0;
    int p5 = 25;
    System.out.println(Integer.toString(n1) + "^" + Integer.toString(p1) + " = "
        + Double.toString(binaryExp(n1, p1)));
  }

  // raises n to the pth power more efficiently than
  // naively multiplying n by itself p times by
  // changing p into its binary version and taking advantage of
  // this, thus reducing the problem's runtime to log(n)

  // for example 3^5 is the same as 3^(101)
  // 3^(101) splits into 3^4 and 3^1, where
  // 3^4 splits into 3^2 * 3^2

  // recursive implementation
  private static double binaryExpRec(int n, int p) {
    if (p == 0) { // base case
      return 1;
    }
    else if (p == 1) { // base case
      return n;
    }
    else if (p % 2 == 0) { // p is even
      return Math.pow(binaryExpRec(n, p / 2), 2);
    }
    else { // p is odd
      return Math.pow(binaryExpRec(n, (p - 1) / 2), 2) * n;
    }
  }

  // non-recursive implementation
  private static double binaryExp(int n, int p) {
    if (p == 0) {
      return 1;
    }
    else if (p == 1) {
      return n;
    }

    int result = 1;

    // perform bit operations to save the cost of converting from decimal to binary
    // manually
    while (p > 0) {
      if ((p & 1) == 1) { // check if p's last binary digit is 1
        result *= n;
      }
      n *= n;
      p >>= 1; // shifts p's bits over to the right by 1 digit
    }
    return result;
  }
}
