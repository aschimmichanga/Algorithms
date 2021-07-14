public class MatrixExponentiation {
  public static void main(String[] args) {
    int n1 = 5;
    int n2 = 100;
    int p1 = 4;
    int p2 = 1;
    int p3 = 200;
    int p4 = 0;
    int p5 = 25;
    System.out.println("The " + Integer.toString(n1) + "th fibonacci number is "
        + Integer.toString(fibMatrixExp(n1)));
  }

  /* in general, matrix exponentiation applies to problems that can be
   * described similar to the the linear recurrence F(n) = a*F(n-1) + b*F(n-2) + c*F(n-3)   for n >= 3 (ex for fibonacci)
   * the dimensions of the intermediate transformation matrix is determined by the initial matrix
   * and final result matrix
  */

  /*
   * applying this approach to fibonacci means trying to transform [f(n-3),f(n-2),f(n-1)] into
   * [f(n-2),f(n-1),f(n)], resulting in a 3x3 transformation matrix with a=1,b=1,c=1
   * To get to the nth term, this transformation matrix would be applied n-2 times to 
   * the initial base case matrix [f(0),f(1),f(2)],
   * which can be made more efficient by raising the transformation matrix to the power of n-2 and using
   * binary exponentiation
   */
  private static int fibMatrixExp(int n) {
    int initMatrix[][] = { { 0, 1, 1 } }; // will always be value of [f(0),f(1),f(2)]
    int a = 1;
    int b = 1;
    int c = 1;
    int transformMatrix[][] = { { a, b, c }, { 1, 0, 0 }, { 0, 1, 0 } };
    int resultMatrix[][] = multiplyTwoMatrices(initMatrix, matrixExpRec(transformMatrix, n - 2)); // [f(n-2),f(n-1),f(n]
    return resultMatrix[0][2]; // extracts and returns f(n)
  }

//recursive implementation of matrix exponentiation
  private static int[][] matrixExpRec(int matrix[][], int p) {
    if (p == 0) { // base case
      int identityMatrix[][] = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
      return identityMatrix;
    }
    else if (p == 1) { // base case
      return matrix;
    }

    if (p % 2 == 0) { // p is even
      int temp[][] = matrixExpRec(matrix, p / 2);
      return multiplyTwoMatrices(temp, temp);
    }
    else { // p is odd
      int temp[][] = matrixExpRec(matrix, (p - 1) / 2);
      return multiplyTwoMatrices(multiplyTwoMatrices(temp, temp), matrix);
    }
  }

  // assumes that the matrices are properly formed and
  // the dimensions of the matrices to be m1 is a x c and m2 is c x b
  // so the resulting matrix is a x b
  public static int[][] multiplyTwoMatrices(int m1[][], int m2[][]) {
    int result[][] = new int[3][3];
    for (int i = 0; i < m1.length; i += 1) {
      for (int j = 0; j < m2[0].length; j += 1) {
        result[i][j] = 0;
        for (int k = 0; k < m1[0].length; k += 1)
          result[i][j] += m1[i][k] * m2[k][j];
      }
    }
    return result;
  }
}
