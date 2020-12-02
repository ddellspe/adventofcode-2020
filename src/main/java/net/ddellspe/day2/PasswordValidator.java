package net.ddellspe.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidator {

  /**
   * Reads in the integers from the file in the resource path with the given file name and returns
   * the password entries in a list.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of {@link PasswordEntry} found in the file
   */
  public static List<PasswordEntry> readInPasswords(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(PasswordValidator.class.getResourceAsStream(fileName)))) {
      return reader.lines().map(m -> new PasswordEntry(m)).collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Gets the Valid password Count for part 1, this reads in the file which converts to {@link
   * PassworEntry} entries and calls the validator for part 1 and returns the count.
   *
   * @param fileName the name of the file (in the resource path) to read in
   * @return the number of valid passwords in the provided file
   */
  public static int getValidPasswordCountPart1(String fileName) {
    List<PasswordEntry> passwords = readInPasswords(fileName);
    return (int) passwords.stream().filter(PasswordEntry::isValidPasswordPart1).count();
  }

  /**
   * Gets the Valid password Count for part 2, this reads in the file which converts to {@link
   * PassworEntry} entries and calls the validator for part 2 and returns the count.
   *
   * @param fileName the name of the file (in the resource path) to read in
   * @return the number of valid passwords in the provided file
   */
  public static int getValidPasswordCountPart2(String fileName) {
    List<PasswordEntry> passwords = readInPasswords(fileName);
    return (int) passwords.stream().filter(PasswordEntry::isValidPasswordPart2).count();
  }
}
