package net.ddellspe.day21;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day21Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day21.part1("test.txt"), is(equalTo(5L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 21 Part 1 output: " + Day21.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day21.part2("test.txt"), is(equalTo("mxmxvkd,sqjhc,fvjkl")));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 21 Part 2 output: " + Day21.part2("input.txt"));
  }
}
