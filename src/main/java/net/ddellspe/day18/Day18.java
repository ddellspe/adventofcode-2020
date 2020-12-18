package net.ddellspe.day18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day18 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day18.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Computes the sum of the part 1 equation evaluation of the equations (multiplication and
   * addition are to be done in order)
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of the evaluation of the equations based on the requirements in part 1
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    return data.stream().mapToLong(val -> evaluateRow(val)).sum();
  }

  /**
   * Computes the sum of the part 2 equation evaluation of the equations (parentheses, then
   * addition, then multiplication)
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of the evaluation of the equations based on the requirements in part 2
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    return data.stream().mapToLong(val -> evaluateRowPart2(val)).sum();
  }

  /**
   * For a row (equation row) processes the data from the point of the parentheses (replacing with
   * the appropriate value) then continues on until only values are left (no parentheses) and then
   * once that occurs, processes the equation appropriately based on the part 1 criteria.
   *
   * @param row the input equation
   * @return the result of the equation using the part 1 logic.
   */
  public static long evaluateRow(String row) {
    String compressedRow = row.replace(" ", "");
    List<NumberGroup> numberGroups = getNumberGroups(compressedRow);
    while (numberGroups.size() > 0) {
      NumberGroup numGroup = numberGroups.get(0);
      compressedRow =
          compressedRow.replace(
              compressedRow.substring(numGroup.startPos - 1, numGroup.endPos + 1),
              Long.toString(
                  evaluateGroup(compressedRow.substring(numGroup.startPos, numGroup.endPos))));
      numberGroups = getNumberGroups(compressedRow);
    }
    return evaluateGroup(compressedRow);
  }

  /**
   * For a row (equation row) processes the data from the point of the parentheses (replacing with
   * the appropriate value) then continues on until only values are left (no parentheses) and then
   * once that occurs, processes the equation appropriately based on the part 2 criteria.
   *
   * @param row the input equation
   * @return the result of the equation using the part 2 logic.
   */
  public static long evaluateRowPart2(String row) {
    String compressedRow = row.replace(" ", "");
    List<NumberGroup> numberGroups = getNumberGroups(compressedRow);
    while (numberGroups.size() > 0) {
      NumberGroup numGroup = numberGroups.get(0);
      compressedRow =
          compressedRow.replace(
              compressedRow.substring(numGroup.startPos - 1, numGroup.endPos + 1),
              Long.toString(
                  evaluateGroupPart2(compressedRow.substring(numGroup.startPos, numGroup.endPos))));
      numberGroups = getNumberGroups(compressedRow);
    }
    return evaluateGroupPart2(compressedRow);
  }

  /**
   * Provides a list of number groupings (positions where the same level parentheses exist for a
   * given number) and if there are internal parentheses (nested)
   *
   * @param row the full equation to look for parenthesis in
   * @return the list of {@link NumberGroup}s found in the equation.
   */
  public static List<NumberGroup> getNumberGroups(String row) {
    List<NumberGroup> numberGroups = new ArrayList<NumberGroup>();
    for (int i = 0; i < row.length(); i++) {
      if (row.charAt(i) == '(') {
        int start = i + 1;
        int end = i + 1 + findEndingParentheses(row.substring(i + 1));
        numberGroups.add(new NumberGroup(start, end, row.substring(start, end).indexOf('(') > -1));
      }
    }
    numberGroups.sort(Comparator.comparing(group -> -group.startPos));
    return numberGroups;
  }

  /**
   * Evaluates the grouped values based on the criteria of part 1 where operators have no order of
   * operations other than from left to right
   *
   * @param group the group of data in the equation with no spaces between digits and mathematical
   *     symbols
   * @return the result using only left-to-right order of execution
   */
  public static long evaluateGroup(String group) {
    long total = 0;
    int operation = 0; // add == 0, multiply == 1
    Pattern expressionPattern = Pattern.compile("(\\d+)([\\+\\*])(.+)");
    String currentData = group;
    Matcher matcher = expressionPattern.matcher(currentData);
    while (matcher.find()) {
      if (operation == 0) {
        total += Long.parseLong(matcher.group(1));
      } else {
        total *= Long.parseLong(matcher.group(1));
      }
      operation = matcher.group(2).equals("+") ? 0 : 1;
      currentData = matcher.group(3);
      matcher = expressionPattern.matcher(currentData);
    }
    if (operation == 0) {
      total += Long.parseLong(currentData);
    } else {
      total *= Long.parseLong(currentData);
    }
    return total;
  }

  /**
   * Evaluates the grouped values based on the criteria of part 2 where addition occurs prior to
   * multiplication.
   *
   * @param group the group of data in the equation with no spaces between digits and mathematical
   *     symbols
   * @return the result using reverse order of operations between multiplication and addition
   *     (addition first)
   */
  public static long evaluateGroupPart2(String group) {
    Pattern addPattern = Pattern.compile("(\\d+)([\\+])(\\d+)");
    String currentData = group;
    Matcher matcher = addPattern.matcher(currentData);
    while (matcher.find()) {
      long value = Long.parseLong(matcher.group(1)) + Long.parseLong(matcher.group(3));
      currentData =
          currentData.substring(0, matcher.start())
              + Long.toString(value)
              + currentData.substring(matcher.end());
      matcher = addPattern.matcher(currentData);
    }
    long total =
        Arrays.stream(currentData.split("\\*"))
            .mapToLong(val -> Long.parseLong(val))
            .reduce(1L, (a, b) -> a * b);
    return total;
  }

  /**
   * Finds the index of the closing parentheses that occurred prior to the input string
   *
   * @param row the string after the the open parentheses to look for the matching close parentheses
   * @return the index of the close parentheses
   */
  public static int findEndingParentheses(String row) {
    int parenCount = 0;
    int endIndex = row.length();
    for (int i = 0; i < row.length(); i++) {
      if (row.charAt(i) == '(') {
        parenCount++;
        continue;
      }
      if (row.charAt(i) == ')' && parenCount > 0) {
        parenCount--;
        continue;
      }
      if (row.charAt(i) == ')') {
        endIndex = i;
        break;
      }
    }
    return endIndex;
  }

  public static class NumberGroup {
    int startPos;
    int endPos;
    boolean nested;

    public NumberGroup(int start, int end, boolean nested) {
      startPos = start;
      endPos = end;
      this.nested = nested;
    }

    @Override
    public String toString() {
      return "NumberGroup [startPos=" + startPos + ",endPos=" + endPos + ",nested=" + nested + "]";
    }
  }
}
