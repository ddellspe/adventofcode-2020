package net.ddellspe.day24;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day24Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day24.part1("test.txt"), is(equalTo(10L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 24 Part 1 output: " + Day24.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day24.part2("test.txt"), is(equalTo(2208L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 24 Part 2 output: " + Day24.part2("input.txt"));
  }
}
