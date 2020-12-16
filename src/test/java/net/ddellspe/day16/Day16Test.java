package net.ddellspe.day16;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day16Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day16.part1("test.txt"), is(equalTo(71L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 16 Part 1 output: " + Day16.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day16.part2("test2.txt"), is(equalTo(1L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 16 Part 2 output: " + Day16.part2("input.txt"));
  }
}
