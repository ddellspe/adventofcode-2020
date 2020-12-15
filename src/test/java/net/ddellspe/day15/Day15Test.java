package net.ddellspe.day15;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class Day15Test {
  @Test
  public void testSampleInputPart1() {
    assertThat(Day15.processList("0,3,6", 2020), is(equalTo(436L)));
    assertThat(Day15.processList("1,3,2", 2020), is(equalTo(1L)));
    assertThat(Day15.processList("2,1,3", 2020), is(equalTo(10L)));
    assertThat(Day15.processList("1,2,3", 2020), is(equalTo(27L)));
    assertThat(Day15.processList("2,3,1", 2020), is(equalTo(78L)));
    assertThat(Day15.processList("3,2,1", 2020), is(equalTo(438L)));
    assertThat(Day15.processList("3,1,2", 2020), is(equalTo(1836L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 15 Part 1 output: " + Day15.processList("14,3,1,0,9,5", 2020));
  }

  @Test
  public void testSampleInputprocessList() {
    assertThat(Day15.processList("0,3,6", 30000000), is(equalTo(175594L)));
    assertThat(Day15.processList("1,3,2", 30000000), is(equalTo(2578L)));
    assertThat(Day15.processList("2,1,3", 30000000), is(equalTo(3544142L)));
    assertThat(Day15.processList("1,2,3", 30000000), is(equalTo(261214L)));
    assertThat(Day15.processList("2,3,1", 30000000), is(equalTo(6895259L)));
    assertThat(Day15.processList("3,2,1", 30000000), is(equalTo(18L)));
    assertThat(Day15.processList("3,1,2", 30000000), is(equalTo(362L)));
  }

  @Test
  public void testPuzzleInputprocessList() {
    System.out.println("Day 15 Part 2 output: " + Day15.processList("14,3,1,0,9,5", 30000000));
  }
}
