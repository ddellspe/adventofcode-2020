package net.ddellspe.day24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day24 {
  static final int size = 20;

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day24.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Reads in movements from origin to determine which tiles to flip black and returns the number of
   * tiles that are black after all of the flips take place (2 flips goest from white to black to
   * white)
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return The number of tiles with an odd number of flips
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    Pattern tilePattern = Pattern.compile("(se|sw|e|ne|nw|w)(.*)");
    List<List<String>> allMoves = new ArrayList<List<String>>();
    for (String row : data) {
      Matcher matcher = tilePattern.matcher(row);
      List<String> moves = new ArrayList<String>();
      while (matcher.find()) {
        moves.add(matcher.group(1));
        matcher = tilePattern.matcher(matcher.group(2));
      }
      allMoves.add(moves);
    }
    Map<Coordinate, Integer> flips = new HashMap<Coordinate, Integer>();
    for (List<String> moves : allMoves) {
      int x = 0;
      int y = 0;
      for (String move : moves) {
        switch (move) {
          case "se":
            x += 1;
            y -= 2;
            break;
          case "sw":
            x -= 1;
            y -= 2;
            break;
          case "w":
            x -= 2;
            break;
          case "nw":
            x -= 1;
            y += 2;
            break;
          case "ne":
            x += 1;
            y += 2;
            break;
          case "e":
            x += 2;
        }
      }
      Coordinate pos = new Coordinate(x, y);
      flips.put(pos, flips.get(pos) == null ? 1 : flips.get(pos) + 1);
    }
    return flips.values().stream().filter(val -> val % 2 == 1).count();
  }

  /**
   * Returns the number of black tiles visible after day 0 flip plus 100 iterations "conway's game
   * of life" for hexagons, using 2 neighbors as the cutoff point for white to black flip.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return The number of black tiles showing after the initial flip (from part 1) and the result
   *     of 100 hexagonal versions of conway's game of life.
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    Pattern tilePattern = Pattern.compile("(se|sw|e|ne|nw|w)(.*)");
    List<List<String>> allMoves = new ArrayList<List<String>>();
    for (String row : data) {
      Matcher matcher = tilePattern.matcher(row);
      List<String> moves = new ArrayList<String>();
      while (matcher.find()) {
        moves.add(matcher.group(1));
        matcher = tilePattern.matcher(matcher.group(2));
      }
      allMoves.add(moves);
    }
    Map<Coordinate, Integer> flips = new HashMap<Coordinate, Integer>();
    for (List<String> moves : allMoves) {
      int x = 0;
      int y = 0;
      for (String move : moves) {
        switch (move) {
          case "se":
            x += 1;
            y -= 2;
            break;
          case "sw":
            x -= 1;
            y -= 2;
            break;
          case "w":
            x -= 2;
            break;
          case "nw":
            x -= 1;
            y += 2;
            break;
          case "ne":
            x += 1;
            y += 2;
            break;
          case "e":
            x += 2;
        }
      }
      Coordinate pos = new Coordinate(x, y);
      flips.put(pos, flips.get(pos) == null ? 1 : flips.get(pos) + 1);
    }
    List<Coordinate> blackTiles =
        flips.entrySet().stream()
            .filter(entry -> entry.getValue() % 2 == 1)
            .map(entry -> entry.getKey())
            .collect(Collectors.toList());
    List<Coordinate> directions =
        Arrays.asList(
            new Coordinate(2, 0),
            new Coordinate(-2, 0),
            new Coordinate(1, 2),
            new Coordinate(-1, 2),
            new Coordinate(1, -2),
            new Coordinate(-1, -2));
    for (int i = 0; i < 100; i++) {
      Map<Coordinate, Integer> counts = new HashMap<Coordinate, Integer>();
      for (Coordinate tile : blackTiles) {
        for (Coordinate dir : directions) {
          Coordinate pos = new Coordinate(tile.x + dir.x, tile.y + dir.y);
          counts.put(pos, counts.get(pos) == null ? 1 : counts.get(pos) + 1);
        }
      }
      List<Coordinate> toRemove =
          blackTiles.stream()
              .filter(tile -> counts.get(tile) == null || counts.get(tile) > 2)
              .collect(Collectors.toList());
      List<Coordinate> toAdd =
          counts.entrySet().stream()
              .filter(entry -> !blackTiles.contains(entry.getKey()) && entry.getValue() == 2)
              .map(entry -> entry.getKey())
              .collect(Collectors.toList());
      for (Coordinate removal : toRemove) {
        blackTiles.remove(removal);
      }
      for (Coordinate add : toAdd) {
        blackTiles.add(add);
      }
    }
    return blackTiles.size();
  }

  static class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Coordinate other = (Coordinate) obj;
      return x == other.x && y == other.y;
    }
  }
}
