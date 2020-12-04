package net.ddellspe.day3;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TobogganTrajectoryTest {
  @Test
  public void providedInputTestPart1() {
    assertThat(TobogganTrajectory.getTreesInPath("test.txt", 3, 1), is(equalTo(7L)));
  }

  @Test
  public void part1() {
    System.out.println(
        "Day 3 Part 1 Answer is: " + TobogganTrajectory.getTreesInPath("input.txt", 3, 1));
  }

  @Test
  public void providedInputTestPart2() {
    List<int[]> moves =
        Arrays.asList(
            new int[] {1, 1},
            new int[] {3, 1},
            new int[] {5, 1},
            new int[] {7, 1},
            new int[] {1, 2});
    assertThat(TobogganTrajectory.getProductTreesInPath("test.txt", moves), is(equalTo(336L)));
  }

  @Test
  public void part2() {
    List<int[]> moves =
        Arrays.asList(
            new int[] {1, 1},
            new int[] {3, 1},
            new int[] {5, 1},
            new int[] {7, 1},
            new int[] {1, 2});
    System.out.println(
        "Day 3 Part 2 Answer is: " + TobogganTrajectory.getProductTreesInPath("input.txt", moves));
  }
}
