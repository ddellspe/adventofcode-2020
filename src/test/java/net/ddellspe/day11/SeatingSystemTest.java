package net.ddellspe.day11;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class SeatingSystemTest {
  @Test
  public void testIOException() {
    assertThat(SeatingSystem.readFile("blah.txt"), is(nullValue()));
  }

  @Test
  public void testSampleInputPart1() {
    assertThat(SeatingSystem.part1("test.txt"), is(equalTo(37L)));
  }

  @Test
  public void testPuzzleInputPart1() {
    System.out.println("Day 11 Part 1 output: " + SeatingSystem.part1("input.txt"));
  }

  @Test
  public void testSampleInputPart2() {
    assertThat(SeatingSystem.part2("test.txt"), is(equalTo(26L)));
  }

  @Test
  public void testPuzzleInputPart2() {
    System.out.println("Day 11 Part 2 output: " + SeatingSystem.part2("input.txt"));
  }
}
