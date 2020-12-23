package net.ddellspe.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day23 {

  /**
   * Runs part 1, which returns the updated list after 100 iterations of the crab manipulating the
   * cups
   *
   * @param inputCups the cup values in the given order
   * @return the new order of the cups with no spaces or delimiters to separate them
   */
  public static String part1(String inputCups) {
    Node head = null;
    List<Integer> cups =
        Arrays.stream(inputCups.split(""))
            .mapToInt(val -> Integer.parseInt(val))
            .boxed()
            .collect(Collectors.toList());
    Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
    Node temp = null;
    for (int cup : cups) {
      if (head == null) {
        head = temp = new Node(cup);
        nodeMap.put(cup, head);
      } else {
        temp = temp.add(cup);
        nodeMap.put(cup, temp);
      }
    }
    for (int i = 0; i < 100; i++) {
      Node three = head.next;
      head.next = head.next.next.next.next;
      List<Integer> values = Arrays.asList(three.data, three.next.data, three.next.next.data);
      int nextVal = head.data - 1;
      if (nextVal == 0) {
        nextVal = 9;
      }
      while (values.contains(nextVal)) {
        nextVal = nextVal - 1;
        if (nextVal == 0) {
          nextVal = 9;
        }
      }
      three.next.next.next = nodeMap.get(nextVal).next;
      nodeMap.get(nextVal).next = three;
      head = head.next;
    }
    return nodeMap.get(1).values().subList(1, 9).stream()
        .map(val -> val.toString())
        .collect(Collectors.joining());
  }

  /**
   * Adds the 1,000,000 cups and does 10,000,000 iterations of the cup swapping with the rules from
   * part 1. This returns the product of the two cups immediately after cup 1.
   *
   * @param inputCups the given cups (1-9) in the given order, the rest of the values 10-1,000,000)
   *     will be added in order to the end of the input list
   * @return the product of the two values after 1 after 10,000,000 iterations are run
   */
  public static long part2(String inputCups) {
    Node head = null;
    List<Integer> cups =
        Arrays.stream(inputCups.split(""))
            .mapToInt(val -> Integer.parseInt(val))
            .boxed()
            .collect(Collectors.toList());
    Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
    Node temp = null;
    for (int cup : cups) {
      if (head == null) {
        head = temp = new Node(cup);
        nodeMap.put(cup, head);
      } else {
        temp = temp.add(cup);
        nodeMap.put(cup, temp);
      }
    }
    for (int i = cups.size() + 1; i <= 1000000; i++) {
      temp = temp.add(i);
      nodeMap.put(i, temp);
    }
    for (int i = 0; i < 10000000; i++) {
      Node three = head.next;
      head.next = head.next.next.next.next;
      three.next.next.next = three;
      List<Integer> values = three.values();
      int nextVal = head.data - 1;
      if (nextVal == 0) {
        nextVal = 1000000;
      }
      while (values.contains(nextVal)) {
        nextVal = nextVal - 1;
        if (nextVal == 0) {
          nextVal = 1000000;
        }
      }
      three.next.next.next = nodeMap.get(nextVal).next;
      nodeMap.get(nextVal).next = three;
      head = head.next;
    }
    return (long) nodeMap.get(1).next.data * (long) nodeMap.get(1).next.next.data;
  }

  public static class Node {
    int data;
    Node next;

    Node(int d) {
      this.data = d;
      this.next = this;
    }

    /**
     * Adds a node with value from the input to the current node, and advances the pointer to this
     * next node
     *
     * @param val the integer value to add to this list
     * @return the node just added to the list
     */
    public Node add(int val) {
      Node newNode = new Node(val);
      newNode.next = this.next;
      this.next = newNode;
      return this.next;
    }

    /**
     * Returns the integer values from this node to the ending node (until the node matches this
     * node)
     *
     * @return a List of Integers containing in order the values in this node until the node.next is
     *     equal to the node itself
     */
    public List<Integer> values() {
      List<Integer> vals = new ArrayList<Integer>();
      vals.add(this.data);
      Node val = this.next;
      while (val != this) {
        vals.add(val.data);
        val = val.next;
      }
      return vals;
    }
  }
}
