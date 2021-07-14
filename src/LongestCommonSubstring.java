class LongestCommonSubstring {
  public static void main(String[] args) {
    String s1 = "hello there";
    String s2 = "hlo";
    String s3 = "is a frog";

    System.out.println("Longest common substring between " + s1 + " and " + s2 + " is "
        + findLongestCommonSubstringLength(s1, s2) + " characters long.");

  }

  /*
   * The longest common suffix has following optimal substructure property. 
  If last characters match, then we reduce both lengths by 1 
  LCSuff(X, Y, m, n) = LCSuff(X, Y, m-1, n-1) + 1 if X[m-1] = Y[n-1] 
  If last characters do not match, then result is 0, i.e., 
  LCSuff(X, Y, m, n) = 0 if (X[m-1] != Y[n-1])
  Now we consider suffixes of different substrings ending at different indexes. 
  The maximum length Longest Common Suffix is the longest common substring. 
  LCSubStr(X, Y, m, n) = Max(LCSuff(X, Y, i, j)) where 1 <= i <= m and 1 <= j <= n 
   */

  private static int findLongestCommonSubstringLength(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();
    // stores subproblems, which is the length of the longest common substring of s1
    // and s2
    // which ends at index i in s1 and index j in s2
    int[][] comparison_pairs = new int[m][n];

    // want to accumulate the result so that we dont need to waste resources
    // traversing through the subproblem table
    int result = 0;

    // represents index in iterating through s1
    for (int i = 0; i < m; i += 1) {
      // represents index in iterating through s2
      for (int j = 0; j < n; j += 1) {
        // represents the base case, when the string being compared is empty
        if (i == 0 || j == 0) {
          comparison_pairs[i][j] = 0;
        }
        else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          comparison_pairs[i][j] = comparison_pairs[i - 1][j - 1] + 1;
          result = Math.max(result, comparison_pairs[i][j]);
        }
        else {
          // decided to write the case where the chars are not equal
          // separately from base case even though they result in the same behavior for
          // clarity
          comparison_pairs[i][j] = 0;
        }
      }
    }

    return result;
  }
}
