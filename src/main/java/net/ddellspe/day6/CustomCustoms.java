package net.ddellspe.day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCustoms {
  /**
   * Reads in the file and returns a list of String where each string is the lines that make up the
   * group of input data (each list element is a group's customs forms, each group has each person
   * separated by a hyphen)
   *
   * @param fileName the name of the file (in the resource path) to read in
   * @return the list of strings where each string represents a group's customs form answers
   */
  public static List<String> readInCustomsForms(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(CustomCustoms.class.getResourceAsStream(fileName)))) {
      List<String> customsForms = new ArrayList<String>();
      customsForms =
          Arrays.stream(
                  reader
                      .lines()
                      .collect(
                          Collectors.reducing(
                              "",
                              line -> line.equals("") ? "BREAK" : line,
                              (string, line) -> string + (string.equals("") ? "" : "-") + line))
                      .split("-BREAK-"))
              .collect(Collectors.toList());
      return customsForms;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Counts the number of unique characters (a-z) in the customs forms. Since each input string may
   * contain multiple peoples' answers, separated by "-".
   *
   * @param customsString The string of individual responses for a group separated by "-"
   * @return the number of characters (yes answers) that are found across all respondents
   */
  public static long countUniqueCharacters(String customsString) {
    long count = 0;
    String[] customForms = customsString.split("-");
    for (String letter : "abcedfghijklmnopqrstuvwxyz".split("")) {
      int formCount = 0;
      for (String form : customForms) {
        if (form.contains(letter)) {
          formCount++;
        }
      }
      if (formCount >= customForms.length) {
        count++;
      }
    }
    return count;
  }

  /**
   * Processes the data in part one to find out the unique yes answers for each group. For each
   * group, the output is the number of unique yes answers (unique characters found) for the group.
   * THe final output of this method is the sum of unique characters across all groups.
   *
   * @param fileName the name of the file (in the resource path) containing the customs form answers
   * @return the sum of unique characters per group.
   */
  public static long part1(String fileName) {
    List<String> customsForms = readInCustomsForms(fileName);
    return customsForms
        .stream()
        .mapToLong(groupData -> groupData.replace("-", "").chars().distinct().count())
        .sum();
  }

  /**
   * Processes the data in part two to find out the number of yes answers that all of the group
   * answered yes to (letters that show up in all of the strings (separated by "-")). This method
   * returns the sum of group-answered yes questions for all groups.
   *
   * @param fileName the name of the file (in the resource path) containing the customs form answers
   * @return the sum of common characters per group.
   */
  public static long part2(String fileName) {
    List<String> customsForms = readInCustomsForms(fileName);
    return customsForms.stream().mapToLong(groupForms -> countUniqueCharacters(groupForms)).sum();
  }
}
