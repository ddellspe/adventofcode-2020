package net.ddellspe.day13;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day13Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day13.part1("test.txt"), is(equalTo(295L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 13 Part 1 output: " + Day13.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(Day13.part2("test.txt"), is(equalTo(1068781L)));
    assertThat(Day13.part2("test2.txt"), is(equalTo(3417L)));
    assertThat(Day13.part2("test3.txt"), is(equalTo(754018L)));
    assertThat(Day13.part2("test4.txt"), is(equalTo(779210L)));
    assertThat(Day13.part2("test5.txt"), is(equalTo(1261476L)));
    assertThat(Day13.part2("test6.txt"), is(equalTo(1202161486L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 13 Part 2 output: " + Day13.part2("input.txt"));
  }
}
