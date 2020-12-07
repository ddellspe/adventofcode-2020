package net.ddellspe.day6;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class CustomCustomsTest {
  @Test
  public void sampleInputPart1() {
    assertThat(CustomCustoms.part1("test.txt"), is(equalTo(11L)));
  }

  @Test
  public void puzzleInputPart1() {
    System.out.println("Day 6 Part 1 answer is: " + CustomCustoms.part1("input.txt"));
  }

  @Test
  public void sampleInputPart2() {
    assertThat(CustomCustoms.part2("test.txt"), is(equalTo(6L)));
  }

  @Test
  public void testFormProcessing() {
    assertThat(CustomCustoms.countUniqueCharacters("abc-abc"), is(equalTo(3L)));
    assertThat(CustomCustoms.countUniqueCharacters("cba-cade"), is(equalTo(2L)));
    assertThat(CustomCustoms.countUniqueCharacters("zjsdk-ddjsz"), is(equalTo(4L)));
    assertThat(CustomCustoms.countUniqueCharacters("cba-cade"), is(equalTo(2L)));
  }

  @Test
  public void puzzleInputPart2() {
    System.out.println("Day 6 Part 2 answer is: " + CustomCustoms.part2("input.txt"));
  }
}
