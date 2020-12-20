package net.ddellspe.day20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day20 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day20.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Finds the product of the 4 corners of the final picture. This doesn't require actual generation
   * of the picture in any way, as all Tiles with only 2 edges will be known to be the corners.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the product of the 4 tile numbers in the corners.
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    String tileString = data.get(0);
    List<String> tileData = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();
    for (String row : data) {
      if (row.startsWith("Tile")) {
        tileString = row;
      } else if (row.isBlank()) {
        tiles.add(new Tile(tileString, tileData));
        tileData = new ArrayList<>();
      } else {
        tileData.add(row);
      }
    }
    tiles.add(new Tile(tileString, tileData));
    Map<Integer, List<Integer>> tileMatches = new HashMap<>();
    for (Tile tile : tiles) {
      tileMatches.put(
          tile.tileNumber,
          tiles.stream()
              .filter(tile2 -> tile.tileNumber != tile2.tileNumber)
              .filter(
                  tile2 ->
                      tile.getEdges().stream()
                              .filter(
                                  edge ->
                                      tile2.getEdges().stream()
                                              .filter(edge2 -> edge.equals(edge2))
                                              .count()
                                          > 0)
                              .count()
                          > 0)
              .mapToInt(tile2 -> tile2.tileNumber)
              .boxed()
              .collect(Collectors.toList()));
    }
    return tileMatches.entrySet().stream()
        .filter(entry -> entry.getValue().size() == 2)
        .mapToLong(entry -> entry.getKey())
        .reduce(1L, (a, b) -> a * b);
  }

  /**
   * Completes part two of the Day 20 puzzle, this takes the input tile data, finds how how it's
   * arranged (generall) with the general practice being based on the number of neighbors, so with
   * 16 tiles, the neighbor count would be:
   *
   * <pre>
   * 2 3 3 2
   * 3 4 4 3
   * 3 4 4 3
   * 2 3 3 2
   * </pre>
   *
   * Once the tile locations are found, orientation is set (from top-left to bottom right in the
   * grid), by checking appropriate edges for equality with potential neighboring tiles and then
   * making sure based on the grid location, the appropriate edges match (top left tile would be the
   * bottom and right edge matching for instance).
   *
   * <p>Once the grid is locked and tiles are in place, the tiles are joined using the {@link
   * Tile}'s edgeless data for the full puzzle size. This full puzzle is then rotated and/or flipped
   * until the sea monster mask (below) is found within the puzzle. The final determination is that
   * the number of matches for the mask (times the number of '#' in the mask) is removed from the
   * total count of '#' in the final puzzle as the final result.
   *
   * <pre>
   *                   #
   * #    ##    ##    ###
   *  #  #  #  #  #  #
   * </pre>
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of '#' in the final picture excluding those in the sea monster mask
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    String tileString = data.get(0);
    List<String> tileData = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();
    for (String row : data) {
      if (row.startsWith("Tile")) {
        tileString = row;
      } else if (row.isBlank()) {
        tiles.add(new Tile(tileString, tileData));
        tileData = new ArrayList<>();
      } else {
        tileData.add(row);
      }
    }
    tiles.add(new Tile(tileString, tileData));
    Map<Integer, Tile> tilesByNumber =
        tiles.stream().collect(Collectors.toMap(tile -> tile.tileNumber, tile -> tile));
    Map<Integer, List<Integer>> tileMatches = new HashMap<>();
    for (Tile tile : tiles) {
      tileMatches.put(
          tile.tileNumber,
          tiles.stream()
              .filter(tile2 -> tile.tileNumber != tile2.tileNumber)
              .filter(
                  tile2 ->
                      tile.getEdges().stream()
                              .filter(
                                  edge ->
                                      tile2.getEdges().stream()
                                              .filter(edge2 -> edge.equals(edge2))
                                              .count()
                                          > 0)
                              .count()
                          > 0)
              .mapToInt(tile2 -> tile2.tileNumber)
              .boxed()
              .collect(Collectors.toList()));
    }
    // get the size of the board
    final int size = (int) Math.sqrt(tiles.size() + 1);
    int[][] board = new int[size][size];

    // generate a map of the neighbor count and list of candidate tiles for that neighbor count
    Map<Integer, List<Integer>> countToNeighbors = new HashMap<Integer, List<Integer>>();
    IntStream.range(2, 5)
        .forEach(
            count ->
                countToNeighbors.put(
                    count,
                    tileMatches.entrySet().stream()
                        .filter(entry -> entry.getValue().size() == count)
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList())));

    // go through the entire board to lock tiles into a location, for location row==0 && col==0
    // arbitrarily pick one of the 2-neighbor tiles to put in that location.
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        if (row == 0) {
          if (col == 0) {
            board[row][col] = countToNeighbors.get(2).get(0);
            countToNeighbors.get(2).remove(0);
          } else if (col == size - 1) {
            board[row][col] =
                tileMatches.get(board[row][col - 1]).stream()
                    .filter(val -> countToNeighbors.get(2).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(2).remove(Integer.valueOf(board[row][col]));
          } else {
            board[row][col] =
                tileMatches.get(board[row][col - 1]).stream()
                    .filter(val -> countToNeighbors.get(3).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(3).remove(Integer.valueOf(board[row][col]));
          }
        } else if (row == size - 1) {
          if (col == 0) {
            board[row][col] =
                tileMatches.get(board[row - 1][col]).stream()
                    .filter(val -> countToNeighbors.get(2).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(2).remove(Integer.valueOf(board[row][col]));
          } else if (col == size - 1) {
            board[row][col] = countToNeighbors.get(2).get(0);
            countToNeighbors.get(2).remove(0);
          } else {
            board[row][col] =
                tileMatches.get(board[row][col - 1]).stream()
                    .filter(val -> countToNeighbors.get(3).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(3).remove(Integer.valueOf(board[row][col]));
          }
        } else {
          if (col == 0) {
            board[row][col] =
                tileMatches.get(board[row - 1][col]).stream()
                    .filter(val -> countToNeighbors.get(3).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(3).remove(Integer.valueOf(board[row][col]));
          } else if (col == size - 1) {
            board[row][col] =
                tileMatches.get(board[row - 1][col]).stream()
                    .filter(val -> countToNeighbors.get(3).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(3).remove(Integer.valueOf(board[row][col]));
          } else {
            board[row][col] =
                tileMatches.get(board[row - 1][col]).stream()
                    .filter(val -> countToNeighbors.get(4).contains(val))
                    .collect(Collectors.toList())
                    .get(0);
            countToNeighbors.get(4).remove(Integer.valueOf(board[row][col]));
          }
        }
      }
    }

    // rotate/flip tiles in their location to make joining all of the tiles together later better
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        Tile tile = tilesByNumber.get(board[row][col]);
        int matches = 0;
        do {
          matches = 0;
          // right
          if (col == 0
              && tilesByNumber.get(board[row][col + 1]).getEdges().contains(tile.getEdges().get(0)))
            matches++;
          // left
          if (col != 0
              && tilesByNumber.get(board[row][col - 1]).getEdges().contains(tile.getEdges().get(2)))
            matches++;
          if (matches < 1) {
            tile.tileContents = rotateClockwise(tile.tileContents);
          } else if (matches == 1) {
            // bottom
            if (row < size - 1
                && tilesByNumber
                    .get(board[row + 1][col])
                    .getEdges()
                    .contains(tile.getEdges().get(1))) matches++;
            // top
            if (row > 0
                && tilesByNumber
                    .get(board[row - 1][col])
                    .getEdges()
                    .contains(tile.getEdges().get(3))) matches++;
            if (matches == 1) {
              Collections.reverse(tile.tileContents);
            }
          }
        } while (matches <= 1);
      }
    }

    // Join the edgeless rows of the tiles together building upon the columns
    List<String> finalImage =
        IntStream.range(0, size)
            .mapToObj(
                row ->
                    IntStream.range(0, tilesByNumber.get(board[row][0]).getEdgelessData().size())
                        .mapToObj(
                            innerRow ->
                                IntStream.range(0, size)
                                    .mapToObj(
                                        col ->
                                            tilesByNumber
                                                .get(board[row][col])
                                                .getEdgelessData()
                                                .get(innerRow))
                                    .reduce("", (a, b) -> (a + b)))
                        .collect(Collectors.toList()))
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());

    // Set the sea monster mask list
    List<String> seaMonsterMask =
        Arrays.asList("                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   ");
    boolean found = false;
    int attempt = 0;
    int monsterCount = 0;

    // Rotate/flip the board until the monster is found in the puzzle, count the number of monsters
    // found in the tiles
    while (!found && attempt < 8) {
      List<String> image = Collections.unmodifiableList(finalImage);
      for (int row = 0; row < image.size() - seaMonsterMask.size(); row++) {
        final int r = row;
        for (int col = 0; col < image.get(0).length() - seaMonsterMask.get(0).length(); col++) {
          final int c = col;
          int goodRows = 0;
          for (int innerRow = 0; innerRow < seaMonsterMask.size(); innerRow++) {
            final int ir = innerRow;
            String mask = seaMonsterMask.get(innerRow);
            if (IntStream.range(0, mask.length())
                    .filter(ind -> mask.charAt(ind) == '#')
                    .filter(ind -> image.get(r + ir).charAt(c + ind) == '#')
                    .count()
                != IntStream.range(0, mask.length())
                    .filter(ind -> mask.charAt(ind) == '#')
                    .count()) {
              break;
            }
            goodRows++;
          }
          if (goodRows == seaMonsterMask.size()) {
            found = true;
            monsterCount++;
          }
        }
      }
      if (found) {
        break;
      }
      finalImage = rotateClockwise(finalImage);
      attempt++;
      // We've gone through all rotations, let's flip the order of the puzzle and start rotations
      // again
      if (attempt == 4) {
        Collections.reverse(finalImage);
      }
    }
    return (tiles.stream()
            .flatMap(tile -> tile.getEdgelessData().stream())
            .flatMap(str -> str.chars().boxed())
            .filter(chr -> chr == (int) '#')
            .count())
        - (seaMonsterMask.stream()
                .flatMap(str -> str.chars().boxed())
                .filter(chr -> chr == (int) '#')
                .count()
            * monsterCount);
  }

  /**
   * Rotates the List of Strings in a clockwise direction, for instance:
   *
   * <pre>
   * 123
   * 456
   * 789
   * </pre>
   *
   * becomes
   *
   * <pre>
   * 741
   * 852
   * 963
   * </pre>
   *
   * @param input
   * @return
   */
  public static List<String> rotateClockwise(List<String> input) {
    return IntStream.range(0, input.size())
        .mapToObj(
            ind ->
                input.stream()
                    .map(line -> new String("" + line.charAt(line.length() - ind - 1)))
                    .reduce("", (a, b) -> a + b))
        .collect(Collectors.toList());
  }

  public static class Tile {
    int tileNumber;
    List<String> tileContents;

    public Tile(String tileString, List<String> tileInput) {
      tileNumber =
          Integer.parseInt(
              tileString.substring(tileString.indexOf(' ') + 1, tileString.indexOf(':')));
      tileContents = tileInput.stream().collect(Collectors.toList());
    }

    /**
     * Returns the string of all 4 edges in both forward and reverse. Order is right, bottom, left,
     * top, right (reverse), bottom (reverse), left (reverse), top (reverse). This can be used to
     * attempt to find neighboring tiles to this tile.
     *
     * @return a list of edges for this tile
     */
    public List<String> getEdges() {
      List<String> edges = new ArrayList<>();
      edges.add(
          tileContents.stream()
              .map(line -> new String("" + line.charAt(line.length() - 1)))
              .reduce("", (a, b) -> a + b)); // right
      edges.add(
          new StringBuilder(tileContents.get(tileContents.size() - 1))
              .reverse()
              .toString()); // bottom
      edges.add(
          tileContents.stream()
              .map(line -> new String("" + line.charAt(0)))
              .reduce("", (a, b) -> b + a)); // left
      edges.add(tileContents.get(0)); // top
      for (int i = 0; i < 4; i++) {
        edges.add(new StringBuilder(edges.get(i)).reverse().toString());
      }
      return edges;
    }

    /**
     * Returns the tile contents except for the data in the first row, last row, first column, and
     * last column.
     *
     * @return the tile contents without first and last row or column
     */
    public List<String> getEdgelessData() {
      return IntStream.range(1, tileContents.size() - 1)
          .mapToObj(ind -> tileContents.get(ind).substring(1, tileContents.get(ind).length() - 1))
          .collect(Collectors.toList());
    }
  }
}
