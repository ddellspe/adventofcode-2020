package net.ddellspe.day19;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day19Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day19.part1("test.txt"), is(equalTo(2L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 19 Part 1 output: " + Day19.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day19.part1("test2.txt"), is(equalTo(3L)));
    assertThat(Day19.part2("test2.txt"), is(equalTo(12L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 19 Part 2 output: " + Day19.part2("input.txt"));
  }
}
