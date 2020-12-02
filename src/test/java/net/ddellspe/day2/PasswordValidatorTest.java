package net.ddellspe.day2;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

public class PasswordValidatorTest {
  @Test
  public void providedInputTestPart1() {
    assertThat(PasswordValidator.getValidPasswordCountPart1("test.txt"), is(equalTo(2)));
  }

  @Test
  public void part1() {
    System.out.println(
        "Day 2 Part 1 Answer is: " + PasswordValidator.getValidPasswordCountPart1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertThat(PasswordValidator.getValidPasswordCountPart2("test.txt"), is(equalTo(1)));
  }

  @Test
  public void part2() {
    System.out.println(
        "Day 2 Part 2 Answer is: " + PasswordValidator.getValidPasswordCountPart2("input.txt"));
  }
}
