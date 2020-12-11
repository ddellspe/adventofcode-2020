package net.ddellspe.day11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatingSystem {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings. A tree is
   * represented as a '#' while an open space is represented by a '.'.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings that represent the map.
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(SeatingSystem.class.getResourceAsStream(fileName)))) {
      return reader.lines().map(str -> str.trim().strip()).collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Counts the occupied neighbors for part 1, which is just the immediate 8 (up, down, left, right,
   * up-left, up-right, down-left, down-right) from the current position and returns how many are
   * occupied ('#').
   *
   * @param data List of Strings containing the seat map
   * @param row the current row to look at the number of occupied neighbors for
   * @param col the current column to look at the number of occupied neighbors for
   * @param rows the total number of rows available
   * @param cols the total number of columns available
   * @return the number of immediate neighbors that are occupied (an int between 0 and 8 inclusive)
   */
  public static int getNeighborCountPart1(List<String> data, int row, int col, int rows, int cols) {
    int count = 0;
    for (int r = row - 1; r <= row + 1; r++) {
      if (r < 0 || r >= rows) {
        continue;
      }
      for (int c = col - 1; c <= col + 1; c++) {
        if (c < 0 || c >= cols || (r == row && c == col)) {
          continue;
        }
        if (data.get(r).charAt(c) == '#') {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Counts the occupied neighbors for part 2, which is just the first seat in the 8 directions (up,
   * down, left, right, up-left, up-right, down-left, down-right) from the current position and
   * returns how many are occupied ('#').
   *
   * @param data List of Strings containing the seat map
   * @param row the current row to look at the number of occupied neighbors for
   * @param col the current column to look at the number of occupied neighbors for
   * @param rows the total number of rows available
   * @param cols the total number of columns available
   * @return the number of first seats in each of the 8 directions that are occupied (an int between
   *     0 and 8 inclusive)
   */
  public static int getNeighborCountPart2(List<String> data, int row, int col, int rows, int cols) {
    int count = 0;
    for (int rc = -1; rc <= 1; rc++) {
      for (int cc = -1; cc <= 1; cc++) {
        if (rc == 0 && cc == 0) {
          continue;
        }
        int r = row + rc;
        int c = col + cc;
        while (r < rows && r >= 0 && c < cols && c >= 0) {
          if (data.get(r).charAt(c) == '.') {
            r += rc;
            c += cc;
            continue;
          } else {
            if (data.get(r).charAt(c) == '#') {
              count++;
              break;
            } else {
              break;
            }
          }
        }
      }
    }
    return count;
  }

  /**
   * Calculates the number of occupied seats when the changing of seats stops changing based on the
   * criteria of 4 or more people being seated directly adjacent to your seat.
   *
   * @param fileName the name of the file (in the resource path) that has the seat map
   * @return the number of occupied seats once the seat pattern no longer has changes
   */
  public static long part1(String fileName) {
    List<String> seatingChart = readFile(fileName);
    List<String> prevChart = seatingChart.stream().collect(Collectors.toList());
    boolean change = true;
    do {
      change = false;
      prevChart = seatingChart.stream().collect(Collectors.toList());
      List<String> newChart = new ArrayList<String>();
      for (int row = 0; row < prevChart.size(); row++) {
        String strRow = seatingChart.get(row);
        for (int col = 0; col < strRow.length(); col++) {
          if (strRow.charAt(col) != '.') {
            int cnt = getNeighborCountPart1(prevChart, row, col, prevChart.size(), strRow.length());
            if (strRow.charAt(col) == 'L' && cnt == 0) {
              char[] chars = strRow.toCharArray();
              chars[col] = '#';
              strRow = String.valueOf(chars);
              change = true;
            } else if (strRow.charAt(col) == '#' && cnt >= 4) {
              char[] chars = strRow.toCharArray();
              chars[col] = 'L';
              strRow = String.valueOf(chars);
              change = true;
            }
          }
        }
        newChart.add(strRow);
      }
      seatingChart = newChart.stream().collect(Collectors.toList());
    } while (change);
    return seatingChart
        .stream()
        .mapToLong(str -> str.chars().filter(chr -> chr == (int) '#').count())
        .sum();
  }

  /**
   * Calculates the number of occupied seats when the changing of seats stops changing based on the
   * criteria of 5 or more people being seated in the closest chairs in each of 8 directions from
   * the central seat.
   *
   * @param fileName the name of the file (in the resource path) that has the seat map
   * @return the number of occupied seats once the seat pattern no longer has changes
   */
  public static long part2(String fileName) {
    List<String> seatingChart = readFile(fileName);
    List<String> prevChart = seatingChart.stream().collect(Collectors.toList());
    boolean change = true;
    do {
      change = false;
      prevChart = seatingChart.stream().collect(Collectors.toList());
      List<String> newChart = new ArrayList<String>();
      for (int row = 0; row < prevChart.size(); row++) {
        String strRow = seatingChart.get(row);
        for (int col = 0; col < strRow.length(); col++) {
          if (strRow.charAt(col) != '.') {
            int cnt = getNeighborCountPart2(prevChart, row, col, prevChart.size(), strRow.length());
            if (strRow.charAt(col) == 'L' && cnt == 0) {
              char[] chars = strRow.toCharArray();
              chars[col] = '#';
              strRow = String.valueOf(chars);
              change = true;
            } else if (strRow.charAt(col) == '#' && cnt >= 5) {
              char[] chars = strRow.toCharArray();
              chars[col] = 'L';
              strRow = String.valueOf(chars);
              change = true;
            }
          }
        }
        newChart.add(strRow);
      }
      seatingChart = newChart.stream().collect(Collectors.toList());
    } while (change);
    return seatingChart
        .stream()
        .mapToLong(str -> str.chars().filter(chr -> chr == (int) '#').count())
        .sum();
  }
}
