package net.ddellspe.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdapterArray {

  /**
   * Read in the file and return the list of Long values within it.
   *
   * @param fileName the name of the file (within the resource path) to read in
   * @return the list of Longs contained within the file
   */
  public static List<Long> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(AdapterArray.class.getResourceAsStream(fileName)))) {
      return reader
          .lines()
          .mapToLong(row -> Long.parseLong(row))
          .boxed()
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Calculates the number of 1-value jumps times the number of 3 value jumps from 0 to the input
   * file maximum value + 3
   *
   * @param fileName the name of the file (within the resource path) to read the values from
   * @return the number of 1-value jumps times the number of 3 value jumps from 0 to the input file
   *     maximum value + 3
   */
  public static long part1(String fileName) {
    List<Long> data = readFile(fileName).stream().sorted().collect(Collectors.toList());
    data.add(data.stream().mapToLong(val -> val.longValue()).max().getAsLong() + 3);
    long prevJoltage = 0L;
    int[] counts = new int[] {0, 0, 0};
    for (Long joltage : data) {
      counts[(int) (joltage - prevJoltage) - 1]++;
      prevJoltage = joltage;
    }
    return (long) (counts[2] * counts[0]);
  }

  /**
   * Returns the number of combinations of the given input to go from 0 to the highest value in the
   * input + 3
   *
   * @param fileName the name of the file (within the resource path) to read the values from
   * @return the number of combinations of paths to go from 0 to the max value + 3 in jumps of 1, 2,
   *     or 3
   */
  public static long part2(String fileName) {
    List<Long> data = readFile(fileName).stream().sorted().collect(Collectors.toList());
    data.add(0, 0L);
    data.add(data.stream().mapToLong(val -> val.longValue()).max().getAsLong() + 3);
    Map<Long, List<Long>> validJumps = new HashMap<Long, List<Long>>();
    for (int i = 0; i < data.size(); i++) {
      final long value = data.get(i);
      validJumps.put(
          value,
          data.subList(i + 1, Math.min(i + 4, data.size())).stream()
              .filter(val -> (val - value) <= 3L)
              .collect(Collectors.toList()));
    }
    validJumps.remove(data.stream().mapToLong(val -> val.longValue()).max().getAsLong());
    long value = getPaths(validJumps, 0, new HashMap<Long, Long>());
    return value;
  }

  /**
   * Recursively finds the number of paths for a given value within the paths Map using the registry
   * as a shortcut to return values for previously found paths.
   *
   * @param paths the map of paths (key is starting number, value is a list of potential numbers
   *     within 3 of the key)
   * @param key the current key of the map to find the number of paths from the value to the last
   *     possible value
   * @param registry a map that stores the number of paths for a given key to cut the traversal time
   * @return the number of paths from the given key (in the paths map) to the final value (which has
   *     no entry in the paths map)
   */
  public static long getPaths(Map<Long, List<Long>> paths, long key, Map<Long, Long> registry) {
    if (registry.containsKey(key)) {
      return registry.get(key);
    }
    if (!paths.containsKey(key)) {
      registry.put(key, 1L);
      return 1L;
    }
    long pathCount = 0L;
    for (Long path : paths.get(key)) {
      pathCount += getPaths(paths, path, registry);
    }
    registry.put(key, pathCount);
    return pathCount;
  }
}
