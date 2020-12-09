package net.ddellspe.day9;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class EncodingErrorTest {
  @Test
  public void sampleInputPart1() {
    assertThat(EncodingError.part1("test.txt", 5), is(equalTo(127L)));
  }

  @Test
  public void answerPart1() {
    System.out.println("Day 9 Part 1 result is: " + EncodingError.part1("input.txt", 25));
  }

  @Test
  public void sampleInputPart2() {
    assertThat(EncodingError.part2("test.txt", 5), is(equalTo(62L)));
  }

  @Test
  public void aswerPart2() {
    System.out.println("Day 9 Part 2 result is: " + EncodingError.part2("input.txt", 25));
  }
}
