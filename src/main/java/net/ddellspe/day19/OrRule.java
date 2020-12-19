package net.ddellspe.day19;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An Or rule requires either sub rule to match in order for this rule to match. Each sub-rule must
 * match based on the criteria of the sub-rule itself.
 *
 * @author david
 */
public class OrRule implements Rule {

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
