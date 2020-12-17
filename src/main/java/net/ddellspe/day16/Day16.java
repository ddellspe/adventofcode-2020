package net.ddellspe.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day16.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    List<String> ruleStrs = new ArrayList<String>();
    List<String> myTicketStrs = new ArrayList<String>();
    List<String> nearbyTicketStrs = new ArrayList<String>();
    int section = 0;
    for (String row : data) {
      if (row.isEmpty()) continue;
      if (row.startsWith("your ticket") || row.startsWith("nearby tickets")) {
        section++;
        continue;
      }
      if (section == 0) {
        ruleStrs.add(row);
      } else if (section == 1) {
        myTicketStrs.add(row);
      } else {
        nearbyTicketStrs.add(row);
      }
    }
    List<Rule> rules = ruleStrs.stream().map(rule -> new Rule(rule)).collect(Collectors.toList());
    List<Ticket> tickets =
        nearbyTicketStrs.stream().map(ticket -> new Ticket(ticket)).collect(Collectors.toList());
    return tickets
        .stream()
        .flatMapToInt(ticket -> ticket.fields.stream().mapToInt(Integer::valueOf))
        .filter(val -> rules.stream().filter(rule -> rule.satisfiesARange(val)).count() == 0)
        .mapToLong(val -> (long) val)
        .sum();
  }

  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    List<String> ruleStrs = new ArrayList<String>();
    List<String> myTicketStrs = new ArrayList<String>();
    List<String> nearbyTicketStrs = new ArrayList<String>();
    int section = 0;
    for (String row : data) {
      if (row.isEmpty()) continue;
      if (row.startsWith("your ticket") || row.startsWith("nearby tickets")) {
        section++;
        continue;
      }
      if (section == 0) {
        ruleStrs.add(row);
      } else if (section == 1) {
        myTicketStrs.add(row);
      } else {
        nearbyTicketStrs.add(row);
      }
    }
    List<Rule> rules = ruleStrs.stream().map(rule -> new Rule(rule)).collect(Collectors.toList());
    // get the tickets that are valid for further processing
    List<Ticket> validTickets =
        nearbyTicketStrs
            .stream()
            .map(ticket -> new Ticket(ticket))
            .filter(
                ticket ->
                    ticket
                            .fields
                            .stream()
                            .filter(
                                field ->
                                    rules.stream().anyMatch(rule -> rule.satisfiesARange(field)))
                            .count()
                        == ticket.fields.size())
            .collect(Collectors.toList());

    // get all rule potential indices
    Map<Rule, List<Integer>> ruleCandidates = new HashMap<Rule, List<Integer>>();
    for (Rule rule : rules) {
      ruleCandidates.put(
          rule,
          IntStream.range(0, rules.size())
              .filter(
                  index ->
                      validTickets
                          .stream()
                          .allMatch(ticket -> rule.satisfiesARange(ticket.fields.get(index))))
              .boxed()
              .collect(Collectors.toList()));
    }

    // get the single appropriate index for each rule
    Set<Integer> processedValues = new HashSet<Integer>();
    while (!ruleCandidates.entrySet().stream().allMatch(entry -> entry.getValue().size() == 1)) {
      int index =
          ruleCandidates
              .entrySet()
              .stream()
              .filter(entry -> entry.getValue().size() == 1)
              .mapToInt(entry -> entry.getValue().get(0))
              .filter(ind -> !processedValues.contains(ind))
              .boxed()
              .findFirst()
              .get();
      ruleCandidates
          .entrySet()
          .stream()
          .filter(entry -> entry.getValue().size() > 1)
          .filter(entry -> entry.getValue().contains(index))
          .forEach(entry -> entry.getValue().remove(Integer.valueOf(index)));
      processedValues.add(index);
    }

    // Process my ticket for the final value
    Ticket myTicket = new Ticket(myTicketStrs.get(0));
    return ruleCandidates
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().name.startsWith("departure"))
        .mapToInt(entry -> entry.getValue().get(0))
        .mapToLong(index -> (long) myTicket.fields.get(index))
        .reduce(1L, (a, b) -> a * b);
  }

  public static class Rule {
    String name;
    List<Range> ranges;

    public Rule(String row) {
      name = row.split(": ")[0];
      ranges =
          Arrays.stream(row.split(": ")[1].split(" or "))
              .map(range -> new Range(range))
              .collect(Collectors.toList());
    }

    public boolean satisfiesARange(int value) {
      return ranges.stream().anyMatch(range -> range.satisfiesRange(value));
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, ranges);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Rule other = (Rule) obj;
      return Objects.equals(name, other.name) && Objects.equals(ranges, other.ranges);
    }

    @Override
    public String toString() {
      return "Rule[name=" + name + ", ranges=" + ranges + "]";
    }
  }

  public static class Range {
    int min;
    int max;

    public Range(String range) {
      min = Integer.parseInt(range.split("-")[0]);
      max = Integer.parseInt(range.split("-")[1]);
    }

    public boolean satisfiesRange(int value) {
      return value >= min && value <= max;
    }

    @Override
    public String toString() {
      return "Range[min=" + min + ", max=" + max + "]";
    }
  }

  public static class Ticket {
    List<Integer> fields;

    public Ticket(String fields) {
      this.fields =
          Arrays.stream(fields.split(","))
              .mapToInt(val -> Integer.parseInt(val))
              .boxed()
              .collect(Collectors.toList());
    }

    @Override
    public String toString() {
      return "Ticket[fields=" + fields + "]";
    }
  }
}
