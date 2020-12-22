package net.ddellspe.day22;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day22Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day22.part1("test.txt"), is(equalTo(306L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 22 Part 1 output: " + Day22.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day22.part2("test.txt"), is(equalTo(291L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 22 Part 2 output: " + Day22.part2("input.txt"));
  }
}
