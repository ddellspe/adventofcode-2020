package net.ddellspe.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class Day25 {
  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day25.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Calculates the card loop count and door loop count using 7 as the subject number and modulo
   * value of 20201227. Then calculates the encryption using the respective loop counts for the
   * OTHER device (door encryption uses card loop count, card encryption uses door loop count) The
   * return is the encryption calculated as specified before, but only if they match going both
   * ways, otherwise a runtime error is thrown
   *
   * @param fileName the name of the file (in the resource path) to read in
   * @return the encryption key (subject number * value % 20201227 looped based on the opposite
   *     items count)
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    long cardInput = Long.parseLong(data.get(0));
    long doorInput = Long.parseLong(data.get(1));
    int cardLoopCnt = 0;
    long cardValue = 1L;
    long subjectNumber = 7L;
    while (cardInput != cardValue) {
      cardLoopCnt++;
      cardValue *= subjectNumber;
      cardValue %= 20201227L;
    }
    int doorLoopCnt = 0;
    long doorValue = 1L;
    while (doorInput != doorValue) {
      doorLoopCnt++;
      doorValue *= subjectNumber;
      doorValue %= 20201227L;
    }
    long cardEncrypt = 1L;
    for (int i = 0; i < doorLoopCnt; i++) {
      cardEncrypt *= cardInput;
      cardEncrypt %= 20201227L;
    }
    long doorEncrypt = 1L;
    for (int i = 0; i < cardLoopCnt; i++) {
      doorEncrypt *= doorInput;
      doorEncrypt %= 20201227L;
    }
    if (doorEncrypt == cardEncrypt) {
      return doorEncrypt;
    } else {
      throw new RuntimeException("Door and Card inputs do not match");
    }
  }
}
