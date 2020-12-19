package net.ddellspe.day19;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * An End rule is a terminus for a chain of referential rules with and rules and or rules and other
 * referential rules. Matches return a set containing the term as part of the rule if the input
 * starts with the same characters as the rule term.
 *
 * @author david
 */
public class EndRule implements Rule {

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
