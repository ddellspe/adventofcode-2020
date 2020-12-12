package net.ddellspe.day12;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class RainRiskTest {
  @Test
  public void testSampleInputPart1() {
    assertThat(RainRisk.part1("test.txt"), is(equalTo(25L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 12 Part 1 output: " + RainRisk.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(RainRisk.part2("test.txt"), is(equalTo(286L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 12 Part 2 output: " + RainRisk.part2("input.txt"));
  }
}
