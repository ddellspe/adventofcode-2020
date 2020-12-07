package net.ddellspe.day7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HandyHaversacks {

  public static List<BagNode> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(HandyHaversacks.class.getResourceAsStream(fileName)))) {
      List<BagNode> bags = new ArrayList<BagNode>();
      bags = reader.lines().map(row -> new BagNode(row)).collect(Collectors.toList());
      return bags;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Map<String, List<String>> generateParentTree(List<BagNode> bagNodes) {
    Map<String, List<String>> parentTree = new HashMap<String, List<String>>();
    for (BagNode node : bagNodes) {
      if (!parentTree.containsKey(node.getPrimaryBag())) {
        parentTree.put(node.getPrimaryBag(), new ArrayList<String>());
      }
      for (Bag child : node.getChildBags()) {
        if (parentTree.get(child.getBagColor()) == null) {
          parentTree.put(child.getBagColor(), new ArrayList<String>());
        }
        parentTree.get(child.getBagColor()).add(node.getPrimaryBag());
      }
    }
    return parentTree;
  }

  public static Set<String> getParentBags(Map<String, List<String>> bags, String currentBag) {
    Set<String> parentBags = new HashSet<String>();
    if (!bags.containsKey(currentBag)) {
      return parentBags;
    }
    for (String parent : bags.get(currentBag)) {
      if (!parentBags.contains(parent)) {
        parentBags.add(parent);
        parentBags.addAll(getParentBags(bags, parent));
      }
    }
    return parentBags;
  }

  public static int getChildBagCount(Map<String, BagNode> bags, String currentBag) {
    if (bags.get(currentBag).getChildBags().isEmpty()) {
      return 1;
    }
    int bagCount = 0;
    for (Bag bag : bags.get(currentBag).getChildBags()) {
      int childCount = getChildBagCount(bags, bag.getBagColor());
      bagCount += bag.getBagCount() * (childCount == 1 ? childCount : childCount + 1);
    }
    return bagCount;
  }

  public static int part1(String fileName, String bagColor) {
    List<BagNode> bags = readFile(fileName);
    Map<String, List<String>> bagTree = generateParentTree(bags);
    Set<String> parentBags = getParentBags(bagTree, bagColor);
    return parentBags.size();
  }

  public static int part2(String fileName, String bagColor) {
    List<BagNode> bags = readFile(fileName);
    Map<String, BagNode> bagMap =
        bags.stream().collect(Collectors.toMap(BagNode::getPrimaryBag, bagNode -> bagNode));
    return getChildBagCount(bagMap, bagColor);
  }

  public static class BagNode {
    private String primaryBag;
    private List<Bag> childBags;
    public static final Pattern BAG_COLOR_REGEX = Pattern.compile("^([a-z ]+) bags contain (.+)$");
    private static final Pattern CHILD_BAG_COLOR_REGEX =
        Pattern.compile("^(\\d+) ([a-z ]+) bag[s]*[,.]*[ ]*(.+)$");

    public BagNode(String row) {
      Matcher bagColorMatcher = BAG_COLOR_REGEX.matcher(row);
      if (bagColorMatcher.find()) {
        primaryBag = bagColorMatcher.group(1);
        childBags = new ArrayList<Bag>();
        String remainingBags = bagColorMatcher.group(2);
        if (!"no other bags.".equals(remainingBags)) {
          Matcher childColorMatcher = CHILD_BAG_COLOR_REGEX.matcher(remainingBags);
          while (childColorMatcher.find()) {
            childBags.add(
                new Bag(childColorMatcher.group(2), Integer.parseInt(childColorMatcher.group(1))));
            remainingBags = childColorMatcher.group(3);
            childColorMatcher = CHILD_BAG_COLOR_REGEX.matcher(remainingBags);
          }
        }
      } else {
        System.out.println("Could not find any matches in: " + row);
      }
    }

    public String getPrimaryBag() {
      return primaryBag;
    }

    public List<Bag> getChildBags() {
      return childBags;
    }

    @Override
    public String toString() {
      return "BagNode [primaryBag=" + primaryBag + ", childBags=" + childBags + "]";
    }
  }

  public static class Bag {
    private int bagCount;
    private String bagColor;

    public Bag(String bagColor, int bagCount) {
      this.bagColor = bagColor;
      this.bagCount = bagCount;
    }

    public String getBagColor() {
      return bagColor;
    }

    public int getBagCount() {
      return bagCount;
    }

    @Override
    public String toString() {
      return "Bag [bagColor=" + bagColor + ", bagCount=" + bagCount + "]";
    }
  }
}
