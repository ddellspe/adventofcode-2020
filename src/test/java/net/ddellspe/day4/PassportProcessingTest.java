package net.ddellspe.day4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class PassportProcessingTest {
  @Test
  public void providedInputTestPart1() {
    assertThat(PassportProcessing.getValidPassportCountPart1("test.txt"), is(equalTo(2L)));
  }

  @Test
  public void part1() {
    System.out.println(
        "Day 4 Part 1 Answer is: " + PassportProcessing.getValidPassportCountPart1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertThat(PassportProcessing.getValidPassportCountPart2("test2.txt"), is(equalTo(4L)));
  }

  @Test
  public void part2() {
    System.out.println(
        "Day 4 Part 2 Answer is: " + PassportProcessing.getValidPassportCountPart2("input.txt"));
  }

  @Test
  public void testBirthYear() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("byr:1919");
    assertThat(passport.hasValidKey("byr"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("byr:1920");
    assertThat(passport.hasValidKey("byr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("byr:2002");
    assertThat(passport.hasValidKey("byr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("byr:2003");
    assertThat(passport.hasValidKey("byr"), is(false));
  }

  @Test
  public void testIssueYear() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("iyr:2009");
    assertThat(passport.hasValidKey("iyr"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("iyr:2010");
    assertThat(passport.hasValidKey("iyr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("iyr:2020");
    assertThat(passport.hasValidKey("iyr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("iyr:2021");
    assertThat(passport.hasValidKey("iyr"), is(false));
  }

  @Test
  public void testExpirationYear() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("eyr:2019");
    assertThat(passport.hasValidKey("eyr"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("eyr:2020");
    assertThat(passport.hasValidKey("eyr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("eyr:2030");
    assertThat(passport.hasValidKey("eyr"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("eyr:2031");
    assertThat(passport.hasValidKey("eyr"), is(false));
  }

  @Test
  public void testHeight() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:149cm");
    assertThat(passport.hasValidKey("hgt"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:58in");
    assertThat(passport.hasValidKey("hgt"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:150cm");
    assertThat(passport.hasValidKey("hgt"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:59in");
    assertThat(passport.hasValidKey("hgt"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:193cm");
    assertThat(passport.hasValidKey("hgt"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:76in");
    assertThat(passport.hasValidKey("hgt"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:194cm");
    assertThat(passport.hasValidKey("hgt"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hgt:77in");
    assertThat(passport.hasValidKey("hgt"), is(false));
  }

  @Test
  public void testHairColor() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#000000");
    assertThat(passport.hasValidKey("hcl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#ffffff");
    assertThat(passport.hasValidKey("hcl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#012345");
    assertThat(passport.hasValidKey("hcl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#6789ab");
    assertThat(passport.hasValidKey("hcl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#cdef05");
    assertThat(passport.hasValidKey("hcl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("hcl:#ghijkl");
    assertThat(passport.hasValidKey("hcl"), is(false));
  }

  @Test
  public void testEyeColor() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:amb");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:blu");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:brn");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:gry");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:grn");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:hzl");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:oth");
    assertThat(passport.hasValidKey("ecl"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("ecl:blue");
    assertThat(passport.hasValidKey("ecl"), is(false));
  }

  @Test
  public void testPassportId() {
    PassportProcessing.Passport passport = new PassportProcessing.Passport();
    passport.addEntries("pid:000000000");
    assertThat(passport.hasValidKey("pid"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("pid:012345678");
    assertThat(passport.hasValidKey("pid"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("pid:123456789");
    assertThat(passport.hasValidKey("pid"), is(true));
    passport = new PassportProcessing.Passport();
    passport.addEntries("pid:23456789");
    assertThat(passport.hasValidKey("pid"), is(false));
    passport = new PassportProcessing.Passport();
    passport.addEntries("pid:0123456789");
    assertThat(passport.hasValidKey("pid"), is(false));
  }
}
