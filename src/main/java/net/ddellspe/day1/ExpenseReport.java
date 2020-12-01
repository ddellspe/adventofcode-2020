package net.ddellspe.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExpenseReport {
  public static int fixExpenseReportPart1(String fileName) {
    List<Integer> expenses = new ArrayList<Integer>();
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(ExpenseReport.class.getResourceAsStream(fileName)));
    String line;
    try {
      line = reader.readLine();
      while (line != null) {
        expenses.add(Integer.parseInt(line));
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return calculateExpensesPart1(expenses);
  }

  public static int calculateExpensesPart1(List<Integer> values) {
    for (int i = 0; i < values.size(); i++) {
      for (int j = i + 1; j < values.size(); j++) {
        if (values.get(i) + values.get(j) == 2020) {
          return values.get(i) * values.get(j);
        }
      }
    }
    return 0;
  }

  public static int fixExpenseReportPart2(String fileName) {
    List<Integer> expenses = new ArrayList<Integer>();
    BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(ExpenseReport.class.getResourceAsStream(fileName)));
    String line;
    try {
      line = reader.readLine();
      while (line != null) {
        expenses.add(Integer.parseInt(line));
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return calculateExpensesPart2(expenses);
  }

  public static int calculateExpensesPart2(List<Integer> values) {
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
