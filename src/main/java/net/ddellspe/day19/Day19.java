package net.ddellspe.day19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day19.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Reads in the rules and candidate strings and checks how many strings match rule 0.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of strings in the file that match rule 0
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    List<String> ruleData = new ArrayList<String>();
    List<String> testData = new ArrayList<String>();
    int section = 0;
    for (String row : data) {
      if (row.isEmpty()) {
        section++;
        continue;
      }
      if (section == 0) {
        ruleData.add(row);
      } else {
        testData.add(row);
      }
    }
    Map<String, Rule> rules = new HashMap<>();
    ruleData.stream()
        .forEach(
            line -> {
              String key = line.split(": ")[0];
              String rulePart = line.split(": ")[1];
              rules.put(key, getRule(rulePart));
            });
    return testData.stream()
        .filter(input -> rules.get("0").matches(input, rules).contains(input))
        .count();
  }

  /**
   * Reads in the rules and candidate strings modifies rule 8 to be "42 | 42 8" and modifies rule 11
   * to be "42 31 | 42 11 31" then checks how many strings match rule 0.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of strings in the file that match rule 0
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    List<String> ruleData = new ArrayList<String>();
    List<String> testData = new ArrayList<String>();
    int section = 0;
    for (String row : data) {
      if (row.isEmpty()) {
        section++;
        continue;
      }
      if (section == 0) {
        ruleData.add(row);
      } else {
        testData.add(row);
      }
    }
    Map<String, Rule> rules = new HashMap<>();
    ruleData.stream()
        .forEach(
            line -> {
              String key = line.split(": ")[0];
              String rulePart = line.split(": ")[1];
              rules.put(key, getRule(rulePart));
            });
    rules.put("8", getRule("42 | 42 8"));
    rules.put("11", getRule("42 31 | 42 11 31"));
    return testData.stream()
        .filter(input -> rules.get("0").matches(input, rules).contains(input))
        .count();
  }

  /**
   * Gets the Rule for the ruleRow provided. A String containing a "|" will return an {@link OrRule}
   * with subRules in the {@link OrRule} based on the recursive call for each of the sides of the
   * "|". A String containing a space (" ") will return an {@link AndRule} with subRules for each of
   * the elements (separated by spaces) in the input ruleRow. A String containing a double quote (")
   * will return a {@link EndRule} with the value being the internal string within the quotes. A
   * String containing only a number return a {@link ReferentialRule} to indicate that matches need
   * to use another rule to provide the match information.
   *
   * @param ruleRow the string containing the rule partial rule string.
   * @return the Rule represented by the rule partial
   */
  public static Rule getRule(String ruleRow) {
    if (ruleRow.contains(" | ")) {
      return new OrRule(
          getSubRules(Arrays.stream(ruleRow.split(" \\| ")).collect(Collectors.toList())));
    } else if (ruleRow.contains(" ")) {
      return new AndRule(
          getSubRules(Arrays.stream(ruleRow.split(" ")).collect(Collectors.toList())));
    } else if (ruleRow.contains("\"")) {
      return new EndRule(ruleRow.replaceAll("[\" ]", ""));
    } else {
      return new ReferentialRule(ruleRow.trim());
    }
  }

  /**
   * Takes the rule tokens (either `# #`, `#`, or "c" where # is any number any number of digits and
   * "c" is any character surrounded by quotes), and returns a list of the corresponding rules for
   * each of the tokens. It will only return AndRule, End Rule, or ReferentialRules.
   *
   * @param ruleTokens the list of potential rule tokens, which must be either numbers separated by
   *     spaces, a single number (as a String) or a character wrapped in double-quotes
   * @return The list of {@link Rule}s contained in the ruleTokens, which will be one of {@link
   *     AndRule}s, {@link EndRule}s, or {@link ReferentialRule}s corresponding to the possible
   *     input types above.
   */
  public static List<Rule> getSubRules(List<String> ruleTokens) {
    return ruleTokens.stream().map(rule -> getRule(rule)).collect(Collectors.toList());
  }
}
