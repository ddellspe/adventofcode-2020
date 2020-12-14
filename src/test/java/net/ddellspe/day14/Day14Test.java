package net.ddellspe.day14;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day14Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day14.part1("test.txt"), is(equalTo(165L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 14 Part 1 output: " + Day14.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day14.part2("test2.txt"), is(equalTo(208L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 14 Part 2 output: " + Day14.part2("input.txt"));
  }
}
