import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class LinkedListComparison {
  public static void main(String[] args) {
    LinkedList<Integer> intList1 = new LinkedList<Integer>(List.of(1, 2, 3, 4, 5));
    LinkedList<Integer> intList2 = new LinkedList<Integer>(List.of(-2, -1, 0, 1, 2, 3, 4, 5));
    LinkedList<String> strList1 = new LinkedList<String>(
        List.of("hello", "world", "!", "world", "hello"));
    LinkedList<String> strList2 = new LinkedList<String>(List.of("!", "world", "hello"));

    Circle circ1 = new Circle(0, 0, 1);
    Circle circ2 = new Circle(0, 0, 2);
    Circle circ3 = new Circle(3, 0, 3);
    Circle circ4 = new Circle(0, 0, 4);
    Circle circ5 = new Circle(1, 0, 5);
    Circle circ6 = new Circle(3, 0, 6);

    LinkedList<Circle> circList1 = new LinkedList<Circle>(
        List.of(circ1, circ2, circ3, circ4, circ5, circ6));
    LinkedList<Circle> circList2 = new LinkedList<Circle>(
        List.of(new Circle(0, 0, 2), circ3, circ4, circ5, circ6));

    System.out.print(
        "List 1: " + listToString(circList1) + "\n" + "List 2: " + listToString(circList2) + "\n"
            + "First Common Element: " + findFirstCommonElement(circList1, circList2).toString());
  }

  private static <T> String listToString(List<T> seq) {
    String output = "";
    for (T elem : seq) {
      output += elem.toString() + " ";
    }
    return output;
  }

  private static <T> T findFirstCommonElement(LinkedList<T> list1, LinkedList<T> list2) {
    int lengthList1 = list1.size();
    int lengthList2 = list2.size();
    Iterator<T> list1Iter = list1.iterator();
    Iterator<T> list2Iter = list2.iterator();
    T currElemList1 = list1.get(0);
    T currElemList2 = list2.get(0);

    while (lengthList1 > lengthList2) {
      currElemList1 = list1Iter.next();
      lengthList1 -= 1;
    }
    while (lengthList2 > lengthList1) {
      currElemList2 = list2Iter.next();
      lengthList2 -= 1;
    }

    for (int i = 0; i < lengthList1; i += 1) {
      // given that the two lists must have a common element
      if (currElemList1 == currElemList2) {
        return currElemList1;
      }
      currElemList1 = list1Iter.next();
      currElemList2 = list2Iter.next();
    }
    throw new RuntimeException("should never reach here");
  }
}

//to represent a circle
class Circle {
  int x;
  int y;
  int rad;

  Circle(int x, int y, int rad) {
    this.x = x;
    this.y = y;
    this.rad = rad;
  }
}
