import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class PermutationCycles {
  public static void main(String[] args) {
  }

  // assumes that elems and permutation are the same length, and
  // that all the elements in elems are in permutation
  public static ArrayList<ArrayList<Integer>> genPermutationCycleRep(int[] elems,
      int[] permutation) {
    // create directed graph representation of bijection between elems and
    // permutation
    HashMap<Integer, Integer> dirGraph = new HashMap<>();
    for (int i = 0; i < elems.length; i += 1) {
      dirGraph.put(elems[i], permutation[i]);
    }
    return findingCyclesWithDFS(elems[0], dirGraph);
  }

  // returns a list of lists where each nested list corresponds to the keys
  // contained within a cycle in the given graph
  public static ArrayList<ArrayList<Integer>> findingCyclesWithDFS(int root,
      HashMap<Integer, Integer> dirGraph) {
    Stack<Integer> beingTraversed = new Stack<Integer>();
    List<Integer> visited = new ArrayList<Integer>();
    List<Integer> nodesWithCycles = new ArrayList<Integer>();
    beingTraversed.push(root);
    while (!beingTraversed.empty()) {
      int currNode = beingTraversed.pop();
      int child = dirGraph.get(currNode);
      visited.add(currNode);
      if (visited.contains(child)) {
        nodesWithCycles.add(child);
      }
      else {
        beingTraversed.push(child);
      }
    }
    return new ArrayList<ArrayList<Integer>>();
  }
}
