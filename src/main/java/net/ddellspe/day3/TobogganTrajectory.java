package net.ddellspe.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TobogganTrajectory {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings. A tree is
   * represented as a '#' while an open space is represented by a '.'.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings that represent the map.
   */
  public static List<String> readInMap(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(TobogganTrajectory.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Calculates the number of trees in the path by moving from the top-left (0, 0) to the bottom row
   * by going down by the downMove step and right by the rightMove step. Each row repeats its values
   * "to infinity". The fileName is used to read the file in the resource path to use as the map. A
   * tree is classified as a '#' whereas an open square is classified as a '.'.
   *
   * @param fileName The filename to read the map from (must be in the resource path)
   * @param rightMove the number of spaces to move right for each movement path trial
   * @param downMove the number of spaces to move down for each movement
   * @return the number of trees (#) in the movement path
   */
  public static long getTreesInPath(String fileName, int rightMove, int downMove) {
    List<String> map = readInMap(fileName);
    return IntStream.range(0, map.size())
        .filter(downPos -> downPos % downMove == 0)
        .filter(downPos -> map.get(downPos).charAt((downPos * rightMove / downMove) % map.get(downPos).length()) == '#')
        .count();
  }

  /**
   * Calculates the product of all movement paths for the given map.
   *
   * @param fileName the filename to read the map from (must be in the resource path)
   * @param moves The list of moves to complete, this must be in the format of a List of 2-element
   *     arrays of integers that contain the right move value in the 0 position and down move value
   *     in the 1 position.
   * @return the product of all paths to be tested
   */
  public static long getProductTreesInPath(String fileName, List<int[]> moves) {
    return moves
        .stream()
        .map(move -> getTreesInPath(fileName, move[0], move[1]))
        .reduce(1L, (a, b) -> a * b);
  }
}
