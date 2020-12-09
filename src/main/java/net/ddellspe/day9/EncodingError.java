package net.ddellspe.day9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class EncodingError {

  /**
   * Read in the file and return the list of Long values within it.
   *
   * @param fileName the name of the file (within the resource path) to read in
   * @return the list of Longs contained within the file
   */
  public static List<Long> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(EncodingError.class.getResourceAsStream(fileName)))) {
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
   * Caller for part 1, which returns the number within the list of values in the provided fileName
   * that is not a sum of the last size number of elements.
   *
   * @param fileName the name of the file (within the resource path) to read the values from
   * @param size the number of values to look back to validate the sum
   * @return the first number in the list that does not have a sum of two numbers in the previous
   *     size number of elements in the list.
   */
  public static long part1(String fileName, int size) {
    List<Long> data = readFile(fileName);
    Queue<Long> queue = new ArrayDeque<Long>(size);
    for (Long value : data) {
      if (queue.size() < size) {
        queue.add(value);
      } else {
        if (queue.stream().filter(queueValue -> queue.contains(value - queueValue)).count() == 0) {
          return value;
        }
        queue.remove();
        queue.add(value);
      }
    }
    return 0L;
  }

  /**
   * Uses the result from {@link #part1(String, int)} to determine the consecutive values in the
   * entire list of numbers that add up to the value that is considered invalid in {@link
   * #part1(String, int)}. This value then returns the sum of the smallest value and largest value.
   *
   * @param fileName the name of the file (within the resource path) to read the values from
   * @param size the number of values to look back to validate the sum
   * @return the sum of the smallest and largest value in the list that adds up to the invalid entry
   *     from {@link #part1(String, int)} given the conditions
   */
  public static long part2(String fileName, int size) {
    List<Long> data = readFile(fileName);
    long value = part1(fileName, size);
    boolean found = false;
    List<Long> values = new ArrayList<Long>();
    for (int i = 0; i < data.size(); i++) {
      if (found) {
        break;
      }
      if (data.get(i) >= value) {
        continue;
      }
      values = new ArrayList<Long>();
      values.add(data.get(i));
      values.add(data.get(i + 1));
      for (int j = i + 2; j < data.size(); j++) {
        long sum = values.stream().mapToLong(Long::longValue).sum();
        if (sum > value) {
          break;
        } else if (sum == value) {
          found = true;
          break;
        }
        values.add(data.get(j));
      }
    }
    return values.stream().mapToLong(Long::longValue).max().getAsLong()
        + values.stream().mapToLong(Long::longValue).min().getAsLong();
  }
}
