package net.ddellspe.day25;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day25Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day25.part1("test.txt"), is(equalTo(14897079L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 25 Part 1 output: " + Day25.part1("input.txt"));
  }
}
