package net.ddellspe.day20;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day20Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day20.part1("test.txt"), is(equalTo(20899048083289L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 20 Part 1 output: " + Day20.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day20.part2("test.txt"), is(equalTo(273L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 20 Part 2 output: " + Day20.part2("input.txt"));
  }
}
