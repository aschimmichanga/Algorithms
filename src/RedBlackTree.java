import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

class Main {
  public static void main(String[] args) {
    RBT tree = new RBT(1);
    tree.insert(new RBT(2));
    tree.insert(new RBT(3));
    tree.insert(new RBT(4));
    // tree.insert(new RBT(5));
    Graph graph = RBT.root.convertToGraph(new SingleGraph("RBT"));

    graph.setAttribute("ui.stylesheet",
        "url('C:\\Users\\ashna\\Documents\\College\\CS5800\\HashAndTrees\\src\\main\\java\\stylesheet.css')");
    for (org.graphstream.graph.Node node : graph) {
      node.setAttribute("ui.label", node.getId());
    }

    // marking root node with purple text
    graph.getNode(RBT.root.nodeID + "").setAttribute("ui.style", "text-color: purple;");

    org.graphstream.graph.Node node = graph.getNode(0);
    System.out.println(node);

//        for (int i = 0; i < graph.getNodeCount(); i++) {
//            org.graphstream.graph.Node node = graph.getNode(i);
//            System.out.println(node.getId());
//        }

    // org.graphstream.graph.Node node = graph.getNode("A");
    // node.setAttribute("ui.style", "fill-color: rgb(255,0,0);");
    // node.setAttribute("x", 1);
    // node.setAttribute("y", 6);

//        graph.addEdge("AB", "A", "B");
//        graph.addEdge("AC", "A", "C");
//        graph.addEdge("CD", "C", "D");
//        graph.addEdge("CE", "C", "E");
//        graph.addEdge("BF", "B", "F");
//        graph.addEdge("BG", "B", "G");

    System.setProperty("org.graphstream.ui", "swing");
    graph.display();

    System.out.println("hi");
  }
}

enum RBTColor {
  RED, BLACK
}

// path from grandparent to current node
enum RotationConfig {
  LL, LR, RR, RL
};

class RBT {
  int value;
  RBT left, right, parent;
  RBTColor color;
  static RBT root;
  static int ID;
  int nodeID;

  /*
  Rules That Every Red-Black Tree Follows:
  
  Every node has a colour either red or black.
  The root of the tree is always black.
  There are no two adjacent red nodes (A red node cannot have a red parent or red child).
  Every path from a node (including root) to any of its descendants NULL nodes has the same number of black nodes.
   */

  // binary tree procedures sort, search, min, max, successor, predecessor
  // specific red-black procedures rotation, insert, delete

  SingleGraph convertToGraph(SingleGraph graph) {

    String currNodeID = nodeID + "";
    graph.addNode(currNodeID);
    String rgb = this.color == RBTColor.RED ? "rgb(255,0,0)" : "rgb(0,0,0)";
    graph.getNode(nodeID + "").setAttribute("ui.style", "fill-color: " + rgb + ";");

    if (parent != null) {
      String parentID = parent.nodeID + "";
      graph.addEdge(parentID + currNodeID, parentID, currNodeID);
    }

    if (this.left != null) {
      graph = this.left.convertToGraph(graph);
    }
    if (this.right != null) {
      graph = this.right.convertToGraph(graph);
    }

    return graph;
  }

  RBT(int value, RBT left, RBT right, RBT parent, RBTColor color) {
    this.value = value;
    this.left = left;
    this.right = right;
    this.parent = parent;
    this.color = color;
    this.nodeID = ID;
    ID += 1;
  }

  RBT(int value) {
    this(value, null, null, null, RBTColor.BLACK);
  }

  RBT(String input) {
    String[] arr = input.replace("[", "").replace("]", "").split(",");
    List<RBT> nodes = new ArrayList<>();
    for (int i = 0; i < arr.length; i += 1) {
      if (!arr[i].equals("null")) {
        nodes.add(i, new RBT(Integer.parseInt(arr[i])));
      }
    }

    if (nodes.isEmpty())
      throw new IllegalArgumentException("need valid numbers");

    root = nodes.get(0);
    for (int i = 1; i < nodes.size(); i += 1) {
      root.insert(nodes.get(i));
    }
  }

  RBT search(int value) {
    if (this.value == value) {
      return this;
    }
    else if (this.value > value) {
      if (this.left == null) {
        return null;
      }
      return this.left.search(value);
    }
    else {
      if (this.right == null) {
        return null;
      }
      return this.right.search(value);
    }
  }

  RBT min() {
    if (this.left == null) {
      return this;
    }
    return this.left.min();
  }

  RBT max() {
    if (this.right == null) {
      return this;
    }
    return this.right.max();
  }

  RBT predecessor() {
    RBT curr = this.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  RBT successor() {
    RBT curr = this.right;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  void rotation(RotationConfig config) {

    switch (config) {
    case LL:
      LLRotation();
      break;
    case LR:
      LRRotation();
      break;
    case RR:
      RRRotation();
      break;
    case RL:
      RLRotation();
    }
  }

  void LLRotation() {
    RBT gp = this.getGrandParent();
    gp.left = this.parent.right;
    RBT tempNode = gp.parent;
    gp.parent = this.parent;
    this.parent.parent = tempNode;
    if (this.parent.parent == null) {
      root = this.parent;
    }

    this.parent.right = gp;

    // swap parent and gp's colors
    RBTColor temp = this.parent.color;
    this.parent.color = gp.color;
    gp.color = temp;
  }

  void LRRotation() {
    RBT gp = this.getGrandParent();

    this.parent.right = this.left;
    this.parent.parent = this;

    this.left = this.parent;
    this.parent = gp;
    gp.left = this;

    this.LLRotation();
  }

  void RRRotation() {
    RBT gp = this.getGrandParent();

    gp.right = this.parent.left;
    RBT tempNode = gp.parent;
    gp.parent = this.parent;
    this.parent.parent = tempNode;
    if (this.parent.parent == null) {
      root = this.parent;
    }
    this.parent.left = gp;

    // swap parent and gp's colors
    RBTColor temp = this.parent.color;
    this.parent.color = gp.color;
    gp.color = temp;
  }

  void RLRotation() {
    RBT gp = this.getGrandParent();

    this.parent.left = this.right;
    this.parent.parent = this;

    this.right = this.parent;
    this.parent = gp;
    gp.right = this;

    this.RRRotation();
  }

  void insert(RBT node) {
    if (node == null) {
      return;
    }

    // standard BST insertion
    this.insertBST(node);

    node.color = RBTColor.RED;
    while (node.parent != null) {
      node.color = RBTColor.RED;
      if (node.parent.color == RBTColor.BLACK) {
        return;
      }
      else {
        RBT unc = node.getUncle();
        RBT gp = node.getGrandParent();
        RBT parent = node.parent;
        if (unc == null || unc.color == RBTColor.BLACK) {
          // TODO: check what rotation should be called on
          if (gp.left == parent && parent.left == node) {
            node.rotation(RotationConfig.LL);
          }
          else if (gp.left == parent && parent.right == node) {
            node.rotation(RotationConfig.LR);
          }
          else if (gp.right == parent && parent.left == node) {
            node.rotation(RotationConfig.RL);
          }
          else if (gp.right == parent && parent.right == node) {
            node.rotation(RotationConfig.RR);
          }
        }
        else {
          parent.color = RBTColor.BLACK;
          unc.color = RBTColor.BLACK;
          gp.color = RBTColor.RED;
          node = gp;
        }
      }
    }
    node.color = RBTColor.BLACK;
  }

  void insertBST(RBT newNode) {
    if (newNode.value <= this.value) {
      if (this.left == null) {
        this.left = newNode;
        newNode.parent = this;
        return;
      }
      this.left.insertBST(newNode);
    }
    else {
      if (this.right == null) {
        this.right = newNode;
        newNode.parent = this;
        return;
      }
      this.right.insertBST(newNode);
    }
  }

  void delete(RBT node) {
    if (node.left != null && node.right != null) {
      twoChildDelete(node);
    }
    else if (node.left != null || node.right != null) {
      oneChildDelete(node);
    }
    else {
      if (node == node.parent.left) {
        node.parent.left = null;
      }
      else {
        node.parent.right = null;
      }
    }
  }

  void twoChildDelete(RBT node) {
    RBT successor = node.successor();
    node.left.parent = successor;
    node.right.parent = successor;
    successor.left = node.left;
    successor.right = node.right;
    successor.parent = node.parent;
    successor.color = findNewReplacementColor(node, successor);
  }

  void oneChildDelete(RBT node) {
    RBT child;
    if (node.right != null) {
      child = node.right;
    }
    else {
      child = node.left;
    }

    child.parent = node.parent;
    if (node == node.parent.left) {
      node.parent.left = child;
    }
    else {
      node.parent.right = child;
    }
    child.color = findNewReplacementColor(node, child);
  }

  RBTColor findNewReplacementColor(RBT deleted, RBT replacement) {
    RBTColor deletedColor = deleted.color;
    RBTColor replacementColor = replacement.color;

    if (deletedColor == RBTColor.RED || replacementColor == RBTColor.RED) {
      return RBTColor.BLACK;
    }
    else { // both are black
      // replacement is double black so must do rotations to restore balance
      // the color of null is also considered black, so even if it was a leaf,
      // deletion of a black leaf causes double black
      // TODO
      return RBTColor.RED;
    }
  }

  // helpers

  // assuming that parent is not the root node
  RBT getGrandParent() {
    return this.parent.parent;
  }

  RBT getUncle() {
    if (this.parent == this.getGrandParent().left) {
      return this.getGrandParent().right;
    }
    else {
      return this.getGrandParent().left;
    }
  }
}
