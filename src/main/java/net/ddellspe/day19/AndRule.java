package net.ddellspe.day19;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An and rule requires all sub rules to match in order for this rule to match. Each sub-rule must
 * match based on the criteria of the sub-rule itself.
 *
 * @author david
 */
public class AndRule implements Rule {

  private final List<Rule> subRules;

  public AndRule(List<Rule> subRules) {
    this.subRules = Collections.unmodifiableList(subRules);
  }

  @Override
  public Set<String> matches(String input, Map<String, Rule> rules) {
    Set<String> matches = subRules.get(0).matches(input, rules);
    for (int i = 1; i < subRules.size(); i++) {
      Set<String> nextMatches = new HashSet<>();
      for (String match : matches) {
        nextMatches.addAll(
            subRules.get(i).matches(input.substring(match.length()), rules).stream()
                .map(nextMatch -> match + nextMatch)
                .collect(Collectors.toSet()));
      }
      matches = nextMatches;
    }
    return matches;
  }
}
