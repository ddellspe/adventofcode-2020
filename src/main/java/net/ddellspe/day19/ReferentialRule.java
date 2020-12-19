package net.ddellspe.day19;

import java.util.Map;
import java.util.Set;

/**
 * A Rule that has reference to another rule as its match validation (an example would be 0: 1)
 * would be that rule 0 is a reference to rule 1, so rule 0 requires rule 1 to match for rule 0 to
 * match.
 *
 * @author david
 */
public class ReferentialRule implements Rule {

  private final String referencedID;

  public ReferentialRule(String referencedID) {
    this.referencedID = referencedID;
  }

  @Override
  public Set<String> matches(String input, Map<String, Rule> rules) {
    return rules.getOrDefault(referencedID, NilRule.instance()).matches(input, rules);
  }
}
