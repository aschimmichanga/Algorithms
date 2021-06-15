import java.util.ArrayList;
import java.util.List;

class MergeSort {
  public static void main(String[] args) {
    List<Integer> intList1 = new ArrayList<Integer>(List.of(-2, -1, 0, 1, 2, 3, 4, 5));
    List<String> strList1 = new ArrayList<String>(List.of("hello", "world", "!", "world", "hello"));
  }

  private static <T> List<T> mergeWithRec(ArrayList<T> list, int p, int q, int r) {
    int n1 = q - p + 1;
    int n2 = r - q;

    return new ArrayList<T>();
  }

  /*
  private static <T> List<T> mergeWithLoop(List<T> list1, List<T> list2) {
    return new ArrayList<T>();
  }
  */

}