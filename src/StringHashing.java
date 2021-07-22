import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class StringHashing {
  public static void main(String[] args) throws IOException {
    Path filePath = Paths.get("C:", "Users", "ashna", "Documents", "College", "CS5800",
        "HashAndTrees", "src", "main", "java", "small.txt");
    // Creating an object of Scanner class
    Scanner sc = new Scanner(filePath);
    StringBuilder result = new StringBuilder();
    // It holds true till there is single element left
    // via hasNext() method
    while (sc.hasNext()) {
      // Iterating over elements in object
      result.append(sc.next()).append(" ");
    }

    // Closing scanner class object to avoid errors and
    // free up memory space
    sc.close();
    HashTable ht = new HashTable(result.toString());

    System.out.println(ht.toString());
    ht.delete("especially");
    System.out.println(ht.getCount("especially"));
  }
}

class HashTable {
  LinkedList<Pair>[] hashTable;

  HashTable() {
    int m = (int) (Math.pow(10, 5) + 7);
    this.hashTable = new LinkedList[m];
  }

  HashTable(String s) {
    this();
    Pattern p = Pattern.compile("[a-zA-Z]+");

    Matcher m1 = p.matcher(s);

    while (m1.find()) {
      this.insert(m1.group());
    }
  }

  public String toString() {
    List<LinkedList<Pair>> noNulls = Arrays.stream(this.hashTable).filter(Objects::nonNull)
        .collect(Collectors.toList());
    StringBuilder result = new StringBuilder();
    for (LinkedList<Pair> l : noNulls) {
      for (Pair p : l) {
        result.append(p.toString()).append("\n");
      }
    }
    return result.toString();
  }

  int hashCode(String word) {
    word = word.toLowerCase();
    int prime = 33;
    int m = (int) (Math.pow(10, 5) + 7);
    int hash = 0;
    int pow = 1;
    for (int i = 0; i < word.length(); i += 1) {
      int charNum = Character.getNumericValue(word.charAt(i)) - Character.getNumericValue('a') + 1;
      hash = (hash + charNum * pow) % m;
      pow = (pow * prime) % m;
    }
    return hash;
  }

  boolean increase(String word) {
    word = word.toLowerCase();
    if (!word.matches("[a-zA-Z]+"))
      return false;
    LinkedList<Pair> currList = findList(word);
    if (currList == null) {
      return false;
    }
    for (Pair p : currList) {
      if (p.word.equals(word)) {
        p.count += 1;
        return true;
      }
    }
    return false;
  }

  void insert(String word) {
    word = word.toLowerCase();
    if (!word.matches("[a-zA-Z]+"))
      return;
    LinkedList currList = this.hashTable[hashCode(word)];
    if (currList == null) {
      currList = new LinkedList<Pair>();
      this.hashTable[hashCode(word)] = currList;
    }
    if (!increase(word)) {
      currList.add(new Pair(word, 1));
    }
  }

  void delete(String word) {
    word = word.toLowerCase();
    if (!word.matches("[a-zA-Z]+"))
      return;
    LinkedList<Pair> currList = findList(word);
    if (currList == null) {
      return;
    }
    for (Pair p : currList) {
      if (p.word.equals(word)) {
        currList.remove(p);
        return;
      }
    }
  }

  int getCount(String word) {
    word = word.toLowerCase();
    LinkedList<Pair> currList = findList(word);
    if (currList == null) {
      return 0;
    }
    for (Pair p : currList) {
      if (p.word.equals(word)) {
        return p.count;
      }
    }
    return 0;
  }

  boolean find(String word) {
    word = word.toLowerCase();
    LinkedList<Pair> currList = findList(word);
    if (currList == null) {
      return false;
    }
    for (Pair p : currList) {
      if (p.word.equals(word)) {
        return true;
      }
    }
    return false;
  }

  LinkedList<Pair> findList(String word) {
    word = word.toLowerCase();
    if (!word.matches("[a-zA-Z]+"))
      return new LinkedList<>();
    return this.hashTable[hashCode(word)];
  }

}

class Pair {
  String word;
  int count;

  Pair(String word, int count) {
    this.word = word;
    this.count = count;
  }

  public String toString() {
    return this.word + " : " + this.count;
  }
}