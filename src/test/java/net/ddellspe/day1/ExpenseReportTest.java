package net.ddellspe.day1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ExpenseReportTest {
  @Test
  public void providedInputTestPart1() {
    assertThat(ExpenseReport.fixExpenseReportPart1("test.txt"), is(equalTo(514579)));
  }

  @Test
  public void part1() {
    System.out.println(
        "Day 1 Part 1 Answer is: " + ExpenseReport.fixExpenseReportPart1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertThat(ExpenseReport.fixExpenseReportPart2("test.txt"), is(equalTo(241861950)));
  }

  @Test
  public void part2() {
    System.out.println(
        "Day 1 Part 2 Answer is: " + ExpenseReport.fixExpenseReportPart2("input.txt"));
  }
}
