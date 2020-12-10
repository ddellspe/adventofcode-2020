package net.ddellspe.day10;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class AdapterArrayTest {
  @Test
  public void testSampleInputPart1() {
    assertThat(AdapterArray.part1("test.txt"), is(equalTo(35L)));
    assertThat(AdapterArray.part1("test2.txt"), is(equalTo(220L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 10 Part 1 output: " + AdapterArray.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(AdapterArray.part2("test.txt"), is(equalTo(8L)));
    assertThat(AdapterArray.part2("test2.txt"), is(equalTo(19208L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 10 Part 2 output: " + AdapterArray.part2("input.txt"));
  }
}
