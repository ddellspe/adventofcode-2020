package net.ddellspe.day23;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day23Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day23.part1("389125467"), is(equalTo("67384529")));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 23 Part 1 output: " + Day23.part1("792845136"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day23.part2("389125467"), is(equalTo(149245887792L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 23 Part 2 output: " + Day23.part2("792845136"));
  }
}
