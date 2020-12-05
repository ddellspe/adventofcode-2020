package net.ddellspe.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class BoardingPass {

  /**
   * Reads in the list of boarding passes from the file and provides that list of strings as output.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of Strings representing the boarding passes
   */
  public static List<String> readInBoardingPasses(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(BoardingPass.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Gets the boarding pass seat id given the boarding pass code (string). In the first portion
   * (first 7 characters) the row is a binary to decimal conversion where F is a 0 and B is a 1. In
   * the second portion (last 3 characters) the columns is a binary to decimal conversion where L is
   * 0 and R is 1. The seat id is equal to the row * 8 + col.
   *
   * @param boardingPass The boarding pass string to be processed
   * @return the seat id for the given boarding pass
   */
  public static long getBoardingPassSeatId(String boardingPass) {
    return Integer.parseInt(boardingPass.substring(0, 7).replace('F', '0').replace('B', '1'), 2) * 8
        + Integer.parseInt(boardingPass.substring(7, 10).replace('R', '1').replace('L', '0'), 2);
  }

  /**
   * Returns the maximum seat id for boarding passes from a file.
   *
   * @param fileName the name of the file (in the resource path) containing the boarding passes
   * @return the maximum seat id for the boarding passes in the file
   */
  public static long maxBoardingPassSeatId(String fileName) {
    List<String> passes = readInBoardingPasses(fileName);
    return passes
        .stream()
        .map(boardingPass -> getBoardingPassSeatId(boardingPass))
        .max(Long::compare)
        .get();
  }

  /**
   * Returns the boarding pass seat id that's missing in the ordered list of boarding pass seat ids
   * in the filename, this missing value is considered my boarding pass.
   *
   * @param fileName the name of the file (in the resource path) containing the boarding passes
   * @return the seat id which is not present in the list, but has both neighbors (seatid + 1 and
   *     seatid -1 are in the list)
   */
  public static long findMyBoardingPass(String fileName) {
    List<String> passes = readInBoardingPasses(fileName);
    List<Long> seatIds =
        passes
            .stream()
            .map(boardingPass -> getBoardingPassSeatId(boardingPass))
            .sorted()
            .collect(Collectors.toList());
    long mySeatId = -1;
    for (Long seatId : seatIds) {
      if (mySeatId == -1) {
        mySeatId = seatId;
        continue;
      }
      if (seatId > mySeatId + 1) {
        mySeatId++;
        break;
      }
      mySeatId = seatId;
    }
    return mySeatId;
  }
}
