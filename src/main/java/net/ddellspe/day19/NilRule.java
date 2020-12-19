package net.ddellspe.day19;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * This is simply an empty rule in the event that a referential rule doesn't match a rule in the
 * map.
 *
 * @author david
 */
public class NilRule implements Rule {

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
