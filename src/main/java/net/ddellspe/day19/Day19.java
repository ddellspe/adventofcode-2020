package net.ddellspe.day19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    ruleData
        .stream()
        .forEach(
            line -> {
              String key = line.split(": ")[0];
              String rulePart = line.split(": ")[1];
              rules.put(key, getRule(rulePart));
            });
    return testData
        .stream()
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
    ruleData
        .stream()
        .forEach(
            line -> {
              String key = line.split(": ")[0];
              String rulePart = line.split(": ")[1];
              rules.put(key, getRule(rulePart));
            });
    rules.put("8", getRule("42 | 42 8"));
    rules.put("11", getRule("42 31 | 42 11 31"));
    return testData
        .stream()
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
      return new AddRule(
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

  public static interface Rule {
    Set<String> matches(String input, Map<String, Rule> rules);
  }

  /**
   * An and rule requires all sub rules to match in order for this rule to match. Each sub-rule must
   * match based on the criteria of the sub-rule itself.
   *
   * @author david
   */
  public static class AddRule implements Rule {

    final List<Rule> subRules;

    public AddRule(List<Rule> subRules) {
      this.subRules = Collections.unmodifiableList(subRules);
    }

    @Override
    public Set<String> matches(String input, Map<String, Rule> rules) {
      Set<String> matches = subRules.get(0).matches(input, rules);
      for (int i = 1; i < subRules.size(); i++) {
        Set<String> nextMatches = new HashSet<>();
        for (String match : matches) {
          nextMatches.addAll(
              subRules
                  .get(i)
                  .matches(input.substring(match.length()), rules)
                  .stream()
                  .map(nextMatch -> match + nextMatch)
                  .collect(Collectors.toSet()));
        }
        matches = nextMatches;
      }
      return matches;
    }
  }

  /**
   * An End rule is a terminus for a chain of referential rules with and rules and or rules and
   * other referential rules. Matches return a set containing the term as part of the rule if the
   * input starts with the same characters as the rule term.
   *
   * @author david
   */
  public static class EndRule implements Rule {

    private final String term;

    public EndRule(String term) {
      this.term = term;
    }

    @Override
    public Set<String> matches(String input, Map<String, Rule> rules) {
      return input.startsWith(term)
          ? Collections.singleton(input.substring(0, term.length()))
          : Collections.emptySet();
    }
  }

  /**
   * This is simply an empty rule in the event that a referential rule doesn't match a rule in the
   * map.
   *
   * @author david
   */
  public static class NilRule implements Rule {

    private static final NilRule rule = new NilRule();

    private NilRule() {}

    public static NilRule instance() {
      return rule;
    }

    @Override
    public Set<String> matches(String input, Map<String, Rule> rules) {
      return Collections.emptySet();
    }
  }

  /**
   * An Or rule requires either sub rule to match in order for this rule to match. Each sub-rule
   * must match based on the criteria of the sub-rule itself.
   *
   * @author david
   */
  public static class OrRule implements Rule {

    private final List<Rule> subRules;

    public OrRule(List<Rule> subRules) {
      this.subRules = Collections.unmodifiableList(subRules);
    }

    @Override
    public Set<String> matches(String input, Map<String, Rule> rules) {
      Set<String> matches = new HashSet<>();
      for (Rule r : subRules) {
        matches.addAll(r.matches(input, rules));
      }

      return matches;
    }
  }
  /**
   * A Rule that has reference to another rule as its match validation (an example would be 0: 1)
   * would be that rule 0 is a reference to rule 1, so rule 0 requires rule 1 to match for rule 0 to
   * match.
   *
   * @author david
   */
  public static class ReferentialRule implements Rule {

    private final String referencedID;

    public ReferentialRule(String referencedID) {
      this.referencedID = referencedID;
    }

    @Override
    public Set<String> matches(String input, Map<String, Rule> rules) {
      return rules.getOrDefault(referencedID, NilRule.instance()).matches(input, rules);
    }
  }
}
