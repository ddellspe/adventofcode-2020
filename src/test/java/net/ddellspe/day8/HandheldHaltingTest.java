package net.ddellspe.day8;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class HandheldHaltingTest {
  @Test
  public void sampleInputPart1() {
    assertThat(HandheldHalting.part1("test.txt"), is(equalTo(5)));
  }

  @Test
  public void answerPart1() {
    System.out.println("Day 8 Part 1 result is: " + HandheldHalting.part1("input.txt"));
  }

  @Test
  public void sampleInputPart2() {
    assertThat(HandheldHalting.part2("test.txt"), is(equalTo(8)));
  }

  @Test
  public void aswerPart2() {
    System.out.println("Day 8 Part 2 result is: " + HandheldHalting.part2("input.txt"));
  }
}
