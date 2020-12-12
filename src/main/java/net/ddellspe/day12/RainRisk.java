package net.ddellspe.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class RainRisk {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings. A tree is
   * represented as a '#' while an open space is represented by a '.'.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings that represent the map.
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(RainRisk.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Calculates the Manhattan distance of ship movements, where it will move north, south, east, or
   * west and rotate (in 90-degree increments) left (counter-clockwise) and right (clockwise) to
   * determine forward moves in the respective direction. The ship starts facing east.
   *
   * @param fileName the name of the file (in the resource path) that has the movements
   * @return the Manhattan distance that the ship moved
   */
  public static long part1(String fileName) {
    List<String> movementDirections = readFile(fileName);
    long x = 0;
    long y = 0;
    int rot = 1;
    for (Movement movement :
        movementDirections.stream().map(Movement::new).collect(Collectors.toList())) {
      switch (movement.move) {
        case "N":
          y += movement.distance;
          break;
        case "S":
          y -= movement.distance;
          break;
        case "E":
          x += movement.distance;
          break;
        case "W":
          x -= movement.distance;
          break;
        case "L":
          movement.distance = 360 - movement.distance;
        case "R":
          rot = (int) ((rot + (movement.distance / 90) + 4) % 4);
          break;
        case "F":
          switch (rot) {
            case 0:
              y += movement.distance;
              break;
            case 1:
              x += movement.distance;
              break;
            case 2:
              y -= movement.distance;
              break;
            case 3:
              x -= movement.distance;
              break;
          }
      }
    }
    return Math.abs(x) + Math.abs(y);
  }

  /**
   * Calculates the Manhattan distance of the Ship moving towards the waypoint which moves north,
   * south, east, west, and rotates around the center-point that is the ship. The ship will move
   * "distance" times towards the waypoint (which will move at the same pace).
   *
   * @param fileName the name of the file (in the resource path) that has the movements
   * @return the Manhattan distance that the ship moved
   */
  public static long part2(String fileName) {
    List<String> movementDirections = readFile(fileName);
    long wpX = 10;
    long wpY = 1;
    long x = 0;
    long y = 0;
    for (Movement movement :
        movementDirections.stream().map(Movement::new).collect(Collectors.toList())) {
      switch (movement.move) {
        case "N":
          wpY += movement.distance;
          break;
        case "S":
          wpY -= movement.distance;
          break;
        case "E":
          wpX += movement.distance;
          break;
        case "W":
          wpX -= movement.distance;
          break;
        case "L":
          movement.distance = 360 - movement.distance;
        case "R":
          long tempX = wpX;
          long tempY = wpY;
          switch ((int) movement.distance) {
            case 90:
              wpX = tempY;
              wpY = -tempX;
              break;
            case 180:
              wpX = -tempX;
              wpY = -tempY;
              break;
            case 270:
              wpX = -tempY;
              wpY = tempX;
              break;
          }
          break;
        case "F":
          x += wpX * movement.distance;
          y += wpY * movement.distance;
          break;
      }
    }
    return Math.abs(x) + Math.abs(y);
  }

  public static class Movement {
    String move;
    long distance;

    public Movement(String movementString) {
      move = movementString.substring(0, 1);
      distance = Long.parseLong(movementString.substring(1));
    }
  }
}
