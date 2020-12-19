package net.ddellspe.day18;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day18Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day18.evaluateRow("2 * 3 + (4 * 5)"), is(equalTo(26L)));
    assertThat(Day18.evaluateRow("5 + (8 * 3 + 9 + 3 * 4 * 3)"), is(equalTo(437L)));
    assertThat(Day18.evaluateRow("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), is(equalTo(12240L)));
    assertThat(
        Day18.evaluateRow("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), is(equalTo(13632L)));
    assertThat(Day18.part1("test.txt"), is(equalTo(26335L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 18 Part 1 output: " + Day18.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day18.evaluateRowPart2("1 + (2 * 3) + (4 * (5 + 6))"), is(equalTo(51L)));
    assertThat(Day18.evaluateRowPart2("2 * 3 + (4 * 5)"), is(equalTo(46L)));
    assertThat(Day18.evaluateRowPart2("5 + (8 * 3 + 9 + 3 * 4 * 3)"), is(equalTo(1445L)));
    assertThat(
        Day18.evaluateRowPart2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), is(equalTo(669060L)));
    assertThat(
        Day18.evaluateRowPart2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"),
        is(equalTo(23340L)));
    assertThat(Day18.part2("test.txt"), is(equalTo(693891L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 18 Part 2 output: " + Day18.part2("input.txt"));
  }
}
