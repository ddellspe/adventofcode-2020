package net.ddellspe.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {

  /**
   * Takes the list and processes the numbers based on the requirements, where you first fill out
   * the list based on input list, then fill out the next number based on the difference in indices
   * between the last insertion of the last number and the previous insertion of the last number.
   *
   * @param numbers String containing the list of numbers separated by commas (no spaces)
   * @param size the number of values to process in the list
   * @return the value of the number at the last position of the list (size)
   */
  public static long processList(String numbers, int size) {
    List<Long> startingNumbers =
        Arrays.stream(numbers.split(","))
            .mapToLong(num -> Long.parseLong(num))
            .boxed()
            .collect(Collectors.toList());
    List<Long> spokenOrder = new ArrayList<Long>();
    Map<Long, Long> lastInstance = new HashMap<Long, Long>();
    long lastSpoken = 0L;
    for (int i = 0; i < size; i++) {
      long nextValue = 0L;
      if (i < startingNumbers.size()) {
        nextValue = startingNumbers.get(i);
      } else {
        nextValue = 0L;
        if (lastInstance.get(lastSpoken) != null) {
          nextValue = spokenOrder.size() - lastInstance.get(lastSpoken) - 1;
        }
      }
      if (i > 0) lastInstance.put(lastSpoken, (long) i - 1);
      spokenOrder.add(nextValue);
      lastSpoken = nextValue;
    }
    return spokenOrder.get(spokenOrder.size() - 1);
  }
}
