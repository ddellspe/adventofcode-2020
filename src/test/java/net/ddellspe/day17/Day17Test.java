package net.ddellspe.day17;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day17Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day17.part1("test.txt"), is(equalTo(112L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 17 Part 1 output: " + Day17.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day17.part2("test.txt"), is(equalTo(848L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 17 Part 2 output: " + Day17.part2("input.txt"));
  }
}
