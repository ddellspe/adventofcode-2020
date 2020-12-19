package net.ddellspe.day19;

import java.util.Map;
import java.util.Set;

public interface Rule {
  Set<String> matches(String input, Map<String, Rule> rules);
}
