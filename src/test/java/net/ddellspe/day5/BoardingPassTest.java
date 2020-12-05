package net.ddellspe.day5;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class BoardingPassTest {
  @Test
  public void providedInputTestPart1() {
    assertThat(BoardingPass.getBoardingPassSeatId("BFFFBBFRRR"), is(equalTo(567L)));
    assertThat(BoardingPass.getBoardingPassSeatId("FFFBBBFRRR"), is(equalTo(119L)));
    assertThat(BoardingPass.getBoardingPassSeatId("BBFFBBFRLL"), is(equalTo(820L)));
    assertThat(BoardingPass.maxBoardingPassSeatId("test.txt"), is(equalTo(820L)));
  }

  @Test
  public void part1() {
    System.out.println(
        "Day 5 Part 1 Answer is: " + BoardingPass.maxBoardingPassSeatId("input.txt"));
  }

  @Test
  public void part2() {
    System.out.println("Day 5 Part 2 Answer is: " + BoardingPass.findMyBoardingPass("input.txt"));
  }
}
