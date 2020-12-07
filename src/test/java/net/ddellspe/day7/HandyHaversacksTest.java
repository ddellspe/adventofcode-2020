package net.ddellspe.day7;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class HandyHaversacksTest {
  @Test
  public void sampleInputPart1() {
    assertThat(HandyHaversacks.part1("test.txt", "shiny gold"), is(equalTo(4)));
  }

  @Test
  public void aswerPart1() {
    System.out.println(
        "Day 7 Part 1 result is: " + HandyHaversacks.part1("input.txt", "shiny gold"));
  }

  @Test
  public void sampleInputPart2() {
    assertThat(HandyHaversacks.part2("test.txt", "shiny gold"), is(equalTo(32)));
  }

  @Test
  public void sampleInput2Part2() {
    assertThat(HandyHaversacks.part2("test2.txt", "shiny gold"), is(equalTo(126)));
  }

  @Test
  public void aswerPart2() {
    System.out.println(
        "Day 7 Part 2 result is: " + HandyHaversacks.part2("input.txt", "shiny gold"));
  }
}
