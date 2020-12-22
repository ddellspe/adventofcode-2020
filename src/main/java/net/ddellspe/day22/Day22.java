package net.ddellspe.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day22 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day22.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Completes part one, which is a simple game of "War" with integer cards
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of (size-index) * value for each element in the winning players hand (the first
   *     card would be multiplied by the number of cards)
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    Map<Integer, Queue<Integer>> playerHands = new HashMap<>();
    int player = 0;
    for (String line : data) {
      if (line.isEmpty()) continue;
      if (line.startsWith("Player")) {
        player = Integer.parseInt(line.split(" ")[1].split(":")[0]);
        continue;
      }
      if (playerHands.get(player) == null) {
        playerHands.put(player, new LinkedList<Integer>());
      }
      playerHands.get(player).add(Integer.parseInt(line));
    }
    while (playerHands.values().stream().filter(value -> value.isEmpty()).count() == 0) {
      int p1Card = playerHands.get(1).poll();
      int p2Card = playerHands.get(2).poll();
      if (p1Card > p2Card) {
        playerHands.get(1).add(p1Card);
        playerHands.get(1).add(p2Card);
      } else {
        playerHands.get(2).add(p2Card);
        playerHands.get(2).add(p1Card);
      }
    }
    final int finalP =
        playerHands.entrySet().stream()
            .filter(entry -> entry.getValue().size() > 0)
            .mapToInt(Map.Entry::getKey)
            .boxed()
            .collect(Collectors.toList())
            .get(0);
    long size = playerHands.get(finalP).size();
    return LongStream.range(0L, size)
        .map(ind -> (size - ind) * playerHands.get(finalP).poll())
        .sum();
  }

  /**
   * Completes part two, where player 1 wins if the exact same series of cards have been seen
   * before, if the players have at least as many cards in their hand as the card they drew, the
   * game recurses with the number of cards drawn for each player, otherwise the game progresses as
   * it did in part 1.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of (size-index) * value for each element in the winning players hand (the first
   *     card would be multiplied by the number of cards)
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    Map<Integer, List<Integer>> playerHands = new HashMap<>();
    int player = 0;
    for (String line : data) {
      if (line.isEmpty()) continue;
      if (line.startsWith("Player")) {
        player = Integer.parseInt(line.split(" ")[1].split(":")[0]);
        continue;
      }
      if (playerHands.get(player) == null) {
        playerHands.put(player, new ArrayList<Integer>());
      }
      playerHands.get(player).add(Integer.parseInt(line));
    }
    final int winner = recurseGame(playerHands.get(1), playerHands.get(2));
    long size = playerHands.get(winner).size();
    return LongStream.range(0L, size)
        .map(ind -> (size - ind) * playerHands.get(winner).get((int) ind))
        .sum();
  }

  /**
   * Plays Recursive combat with the follwoing rules:
   *
   * <ol>
   *   <li>If this exact same card order for BOTH players is identical to a previous version of this
   *       level of the game, player 1 is declared the winner
   *   <li>If the players each have at least as many cards in their hand as the value of the card
   *       they drew, the game recurses with the number of cards for each player based on the card
   *       they drew the winner of the recursion puts cards back in their hand in the order of
   *       winners card followed by losers card
   *   <li>If at least one player doesn't have enough cards to recurse, the player with the higher
   *       value card drawn wins and puts cards back in their hand in the order of winners card
   *       followed by losers card
   *
   * @param p1Hand Player 1's hand (List of Integer cards)
   * @param p2Hand Player 2's hand (List of Integer cards)
   * @return the winner of the recursive game
   */
  public static int recurseGame(List<Integer> p1Hand, List<Integer> p2Hand) {
    List<String> plays = new ArrayList<String>();
    while (p1Hand.size() > 0 && p2Hand.size() > 0) {
      String state =
          p1Hand.stream().map(val -> val.toString()).collect(Collectors.joining(","))
              + "-"
              + p2Hand.stream().map(val -> val.toString()).collect(Collectors.joining(","));
      if (plays.contains(state)) {
        return 1;
      }
      int winner = 0;
      int p1Card = p1Hand.iterator().next();
      p1Hand.remove(Integer.valueOf(p1Card));
      int p2Card = p2Hand.iterator().next();
      p2Hand.remove(Integer.valueOf(p2Card));
      if (p1Hand.size() >= p1Card && p2Hand.size() >= p2Card) {
        winner =
            recurseGame(
                p1Hand.subList(0, p1Card).stream().collect(Collectors.toList()),
                p2Hand.subList(0, p2Card).stream().collect(Collectors.toList()));
      } else {
        winner = p1Card > p2Card ? 1 : 2;
      }
      if (winner == 1) {
        p1Hand.add(p1Card);
        p1Hand.add(p2Card);
      } else if (winner == 2) {
        p2Hand.add(p2Card);
        p2Hand.add(p1Card);
      }
      plays.add(state);
    }
    return p1Hand.size() > 0 ? 1 : 2;
  }
}
