package net.ddellspe.day14;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day14 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day14.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Part 1 masks the value being put into the memory with the provided mask (rolling) and sums the
   * values. The mask in part 1 retains the original values in the binary representation where the
   * mask has an 'X' otherwise sets to the value in the mask
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of the values set in memory
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    Masker masker = new Masker(data.get(0));
    data.remove(0);
    for (String row : data) {
      if (row.startsWith("mask")) {
        masker.setMask(row);
      } else {
        FileEntry fe = new FileEntry(row);
        masker.addPart1(fe.index, fe.value);
      }
    }
    return masker.getSum();
  }

  /**
   * Part 2 takes the index for where to store the value into memory and generates the appropriate
   * memory addresses based on the mask. This mask sees all '0' values pull from the binary
   * representation of the value, and overwrites indices where the mask is either '1' or 'X' with
   * the respective value from the mask. In the final representation, the 'X' is considered a
   * floating number, so it may either be 0 or 1 in the final form. All possible combinations are
   * generated in order to determine the memory addresses to store the desired value in.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the sum of the values set in memory
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);
    Masker masker = new Masker(data.get(0));
    data.remove(0);
    for (String row : data) {
      if (row.startsWith("mask")) {
        masker.setMask(row);
      } else {
        FileEntry fe = new FileEntry(row);
        masker.addPart2(fe.index, fe.value);
      }
    }
    return masker.getSum();
  }

  /**
   * Takes a masked binary string from part two and generates all of the possible long values for
   * that binary representation (recursively replacing the open 'X' values with 0 or 1 until all
   * combinations are found.
   *
   * @param binaryString the character array of the binary string, should contain only '1', '0' amd
   *     'X' values.
   * @param values the current set of values represented in the floating expansion of this binary
   *     string
   * @return the set of long values for this level of the recursive function, when there are no 'X'
   *     in the string, this is simply the value represented by that string in the binary to decimal
   *     conversion
   */
  public static Set<Long> getCandidateIndices(char[] binaryString, Set<Long> values) {
    if (!IntStream.range(0, binaryString.length).anyMatch(v -> binaryString[v] == 'X')) {
      values.add(Long.parseLong(new String(binaryString), 2));
      return values;
    }
    int index =
        IntStream.range(0, binaryString.length)
            .filter(v -> binaryString[v] == 'X')
            .min()
            .getAsInt();
    char[] innerString = new String(binaryString).toCharArray();
    innerString[index] = '0';
    values.addAll(getCandidateIndices(innerString, values));
    innerString[index] = '1';
    values.addAll(getCandidateIndices(innerString, values));
    return values;
  }

  public static class FileEntry {
    int index;
    long value;
    Pattern MATCHER = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

    public FileEntry(String row) {
      Matcher match = MATCHER.matcher(row);
      if (match.find()) {
        index = Integer.parseInt(match.group(1));
        value = Long.parseLong(match.group(2));
      } else {
        // This should never hit with good data
        System.out.println(row + " does not follow guidelines");
      }
    }
  }

  public static class Masker {
    Map<Long, Long> map;
    String mask;

    Pattern MATCHER = Pattern.compile("mask = (.+)");

    public Masker(String mask) {
      map = new HashMap<Long, Long>();
      setMask(mask);
    }

    public void setMask(String mask) {
      Matcher match = MATCHER.matcher(mask);
      if (match.find()) {
        this.mask = match.group(1);
      } else {
        // This should never hit with good data
        System.out.println(mask + " does not follow guidelines");
      }
    }

    public void addPart1(long index, long value) {
      String valueBinary = Long.toBinaryString(value);
      final int length = 36 - valueBinary.length();
      char[] preStr = new char[length];
      String fullValueBinary = new String(preStr).replace('\0', '0') + valueBinary;
      String maskingApplied =
          IntStream.range(0, 36)
              .mapToObj(
                  ind ->
                      String.valueOf(
                          'X' != mask.charAt(ind) ? mask.charAt(ind) : fullValueBinary.charAt(ind)))
              .collect(Collectors.joining());
      map.put(index, Long.parseLong(maskingApplied, 2));
    }

    public void addPart2(long index, long value) {
      String indexBinary = Long.toBinaryString(index);
      final int length = 36 - indexBinary.length();
      char[] preStr = new char[length];
      String fullIndexBinary = new String(preStr).replace('\0', '0') + indexBinary;
      String maskingApplied =
          IntStream.range(0, 36)
              .mapToObj(
                  ind ->
                      String.valueOf(
                          '0' == mask.charAt(ind) ? fullIndexBinary.charAt(ind) : mask.charAt(ind)))
              .collect(Collectors.joining());
      Set<Long> indices = getCandidateIndices(maskingApplied.toCharArray(), new HashSet<Long>());
      indices.stream().forEach(ind -> map.put(ind, value));
    }

    public long getSum() {
      return map.values().stream().mapToLong(val -> (long) val).sum();
    }
  }
}
