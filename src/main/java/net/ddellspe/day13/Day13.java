package net.ddellspe.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day13 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day13.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Reads the data from the file and calculates the multiplication of the bus id times the
   * difference in time from the earliest time to catch a bus in order to get to the airport.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return time the bus (arrives - earliest time) * busId
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    long earliestTime = Long.parseLong(data.get(0));
    String[] busData = data.get(1).split(",");
    List<Bus> buses =
        IntStream.range(0, busData.length)
            .filter(i -> !"x".equals(busData[i]))
            .mapToObj(i -> new Bus(Long.parseLong(busData[i]), (long) i))
            .collect(Collectors.toList());
    long time =
        LongStream.range(earliestTime, earliestTime + buses.get(0).id)
            .filter(timestamp -> buses.stream().anyMatch(bus -> bus.atStand(timestamp)))
            .min()
            .getAsLong();
    Bus matchingBus =
        buses.stream().filter(bus -> bus.atStand(time)).collect(Collectors.toList()).get(0);
    return (time - earliestTime) * matchingBus.id;
  }

  /**
   * Reports the time at which the list of buses will come in an order at an offset based on their
   * position in the list
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the time where the first bus in the list departs in order for the remaining buses in
   *     the list offset in time by the indexed position in the list
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    String[] busData = data.get(1).split(",");
    List<Bus> buses =
        IntStream.range(0, busData.length)
            .filter(i -> !"x".equals(busData[i]))
            .mapToObj(i -> new Bus(Long.parseLong(busData[i]), (long) i))
            .collect(Collectors.toList());

    Bus bus = buses.get(0);
    long time = bus.id - bus.offset;
    long interval = 1;
    while (true) {
      if (bus.fitsSchedule(time)) {
        interval *= bus.id;
        buses.remove(bus);
        if (buses.size() == 0) {
          break;
        }
        bus = buses.get(0);
      }
      time += interval;
    }
    return time;
  }

  public static class Bus {
    long id;
    long offset;

    public Bus(long id, long offset) {
      this.id = id;
      this.offset = offset;
    }

    public boolean atStand(long time) {
      return time % id == 0;
    }

    public boolean fitsSchedule(long time) {
      return ((time + offset) % id) == 0;
    }
  }
}
