package net.ddellspe.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PassportProcessing {

  /**
   * Reads in the map from the file and provides it in a streamable list of Passports.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of Passports from the filename.
   */
  public static List<Passport> readInPassports(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(PassportProcessing.class.getResourceAsStream(fileName)))) {
      List<Passport> passports = new ArrayList<Passport>();
      passports =
          Arrays.stream(
                  reader
                      .lines()
                      .collect(
                          Collectors.reducing(
                              "",
                              line -> line.equals("") ? "BREAK" : line,
                              (string, line) -> string + (string.equals("") ? "" : " ") + line))
                      .split(" BREAK "))
              .map(entries -> new Passport(entries))
              .collect(Collectors.toList());
      return passports;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Counts the number of valid passports (from the input fileName in the resource path) based on
   * the part 1 criteria. Valid criteria can be found in {@link Passport.isValidPart1()}.
   *
   * @param fileName the name of the file (in the resource path) to read passports data from
   * @return The number of passports considered valid for part 1
   */
  public static long getValidPassportCountPart1(String fileName) {
    List<Passport> passports = readInPassports(fileName);
    return passports.stream().filter(passport -> passport.isValidPart1()).count();
  }

  /**
   * Counts the number of valid passports (from the input fileName in the resource path) based on
   * the part 2 criteria. Valid criteria can be found in {@link Passport.isValidPart1()}.
   *
   * @param fileName the name of the file (in the resource path) to read passports data from
   * @return The number of passports considered valid for part 2
   */
  public static long getValidPassportCountPart2(String fileName) {
    List<Passport> passports = readInPassports(fileName);
    return passports.stream().filter(passport -> passport.isValidPart2()).count();
  }

  static class Passport {
    private List<PassportEntry> passportEntries;

    public static final Pattern VALID_HAIR_COLOR = Pattern.compile("^#[0-9a-f]{6}$");
    public static final List<String> VALID_EYE_COLOR =
        Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    public static final Pattern VALID_PASSPORT_ID = Pattern.compile("^[0-9]{9}$");

    public Passport() {
      passportEntries = new ArrayList<PassportEntry>();
    }

    public Passport(String entries) {
      passportEntries = new ArrayList<PassportEntry>();
      addEntries(entries);
    }

    public void addEntries(String entriesLine) {
      for (String entry : entriesLine.split("\\s+")) {
        passportEntries.add(new PassportEntry(entry));
      }
    }

    /**
     * Returns whether or not the passport has an entry with a matching key.
     *
     * @param key the key to check for presence.
     * @return true if the key is found in the passport, otherwise false.
     */
    private boolean hasKey(String key) {
      return passportEntries.stream().filter(entry -> entry.getKey().equals(key)).count() > 0L;
    }

    /**
     * Returns true if the value for the provided key is valid, false otherwise.
     *
     * <p>Valid values for given keys are:
     *
     * <ul>
     *   <li>byr (Birth Year) - four digits; at least 1920 and at most 2002.
     *   <li>iyr (Issue Year) - four digits; at least 2010 and at most 2020.
     *   <li>eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
     *   <li>hgt (Height) - a number followed by either cm or in:
     *       <ul>
     *         <li>If cm, the number must be at least 150 and at most 193.
     *         <li>If in, the number must be at least 59 and at most 76.
     *       </ul>
     *   <li>hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
     *   <li>ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
     *   <li>pid (Passport ID) - a nine-digit number, including leading zeroes.
     *   <li>cid (Country ID) - ignored, missing or not.
     *
     * @param key key to validate the value in the passport for
     * @return true if the value for the given key is valid, otherwise false.
     */
    boolean hasValidKey(String key) {
      Stream<String> entries =
          passportEntries.stream()
              .filter(entry -> entry.getKey().equals(key))
              .map(PassportEntry::getValue);
      long count = 0;
      switch (key) {
        case "byr":
          count =
              entries
                  .map(entry -> Integer.parseInt(entry))
                  .filter(value -> value >= 1920 && value <= 2002)
                  .count();
          break;
        case "iyr":
          count =
              entries
                  .map(entry -> Integer.parseInt(entry))
                  .filter(value -> value >= 2010 && value <= 2020)
                  .count();
          break;
        case "eyr":
          count =
              entries
                  .map(entry -> Integer.parseInt(entry))
                  .filter(value -> value >= 2020 && value <= 2030)
                  .count();
          break;
        case "hgt":
          count =
              entries
                  .filter(
                      value -> {
                        if (value.contains("in")) {
                          int hgt = Integer.parseInt(value.replace("in", ""));
                          return hgt >= 59 && hgt <= 76;
                        } else if (value.contains("cm")) {
                          int hgt = Integer.parseInt(value.replace("cm", ""));
                          return hgt >= 150 && hgt <= 193;
                        }
                        return false;
                      })
                  .count();
          break;
        case "hcl":
          count = entries.filter(entry -> VALID_HAIR_COLOR.matcher(entry).find()).count();
          break;
        case "ecl":
          count = entries.filter(entry -> VALID_EYE_COLOR.contains(entry)).count();
          break;
        case "pid":
          count = entries.filter(entry -> VALID_PASSPORT_ID.matcher(entry).find()).count();
          break;
      }
      return count > 0L;
    }

    /**
     * Checks that the passport has values for all of 'byr' (Birth Year), 'iyr' (Issue Year), 'eyr'
     * (Expiration Year), 'hgt' (Height), 'hcl' (Hair Color), 'ecl' (Eye Color), and 'pid' (Passport
     * ID).
     *
     * @return true if all 7 passport entries are present, false otherwise
     */
    public boolean isValidPart1() {
      return Stream.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
              .filter(key -> hasKey(key))
              .count()
          == 7L;
    }

    /**
     * Checks that the passport has valid values for all of 'byr' (Birth Year), 'iyr' (Issue Year),
     * 'eyr' (Expiration Year), 'hgt' (Height), 'hcl' (Hair Color), 'ecl' (Eye Color), and 'pid'
     * (Passport ID).
     *
     * <p>Valid passport criteria is as follows:
     *
     * <ul>
     *   <li>byr (Birth Year) - four digits; at least 1920 and at most 2002.
     *   <li>iyr (Issue Year) - four digits; at least 2010 and at most 2020.
     *   <li>eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
     *   <li>hgt (Height) - a number followed by either cm or in:
     *       <ul>
     *         <li>If cm, the number must be at least 150 and at most 193.
     *         <li>If in, the number must be at least 59 and at most 76.
     *       </ul>
     *   <li>hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
     *   <li>ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
     *   <li>pid (Passport ID) - a nine-digit number, including leading zeroes.
     *   <li>cid (Country ID) - ignored, missing or not.
     *
     * @return true if all 7 passport entries are present and valid, false otherwise
     */
    public boolean isValidPart2() {
      return Stream.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
              .filter(key -> hasKey(key))
              .filter(key -> hasValidKey(key))
              .count()
          == 7L;
    }

    @Override
    public String toString() {
      return "Passport[passportEntries=" + passportEntries + "]";
    }

    class PassportEntry {
      private String key;
      private String value;

      public PassportEntry(String entry) {
        key = entry.split(":")[0];
        value = entry.split(":")[1];
      }

      public String getKey() {
        return key;
      }

      public String getValue() {
        return value;
      }

      @Override
      public String toString() {
        return "PassportEntry[key=" + key + ", value=" + value + "]";
      }
    }
  }
}
