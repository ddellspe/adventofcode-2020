package net.ddellspe.day1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExpenseReport {
  /**
   * Reads in the integers from the file in the resource path with the given file name and returns
   * the integers in a list.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of integers found in the file
   */
  public static List<Integer> readInExpenses(String fileName) {
    List<Integer> values = new ArrayList<Integer>();
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(ExpenseReport.class.getResourceAsStream(fileName)));
    reader.lines().mapToInt(value -> Integer.parseInt(value)).forEach(value -> values.add(value));
    return values;
  }

  /**
   * Given two entries in the list of expenses, if the sum of the two items is 2020, return the
   * product of those two items.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the product of the two numbers that sum to 2020, 0 if not found.
   */
  public static int calculateExpensesPart1(String fileName) {
    List<Integer> values = readInExpenses(fileName);
    for (int i = 0; i < values.size(); i++) {
      for (int j = i + 1; j < values.size(); j++) {
        if (values.get(i) + values.get(j) == 2020) {
          return values.get(i) * values.get(j);
        }
      }
    }
    return 0;
  }

  /**
   * Given three entries in the list of expenses, if the sum of the three items is 2020, return the
   * product of those three items.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the product of the three numbers that sum to 2020, 0 if not found.
   */
  public static int calculateExpensesPart2(String fileName) {
    List<Integer> values = readInExpenses(fileName);
    for (int i = 0; i < values.size(); i++) {
      for (int j = i + 1; j < values.size(); j++) {
        for (int k = j + 1; k < values.size(); k++) {
          if (values.get(i) + values.get(j) + values.get(k) == 2020) {
            return values.get(i) * values.get(j) * values.get(k);
          }
        }
      }
    }
    return 0;
  }
}
