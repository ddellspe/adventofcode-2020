package net.ddellspe.day17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day17.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Takes the input and does a 3D Conway's game of life on the input for 6 iterations and returns
   * the number of active cells after 6 iterations.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of active cells in the 3D board after 6 iterations
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);

    // Generate initial puzzle board;
    int prevDept = 1;
    int prevRows = data.size();
    int prevCols = data.get(0).length();
    char[] prevPuzzle = new char[prevDept * prevRows * prevCols];
    for (int z = 0; z < prevDept; z++) {
      for (int r = 0; r < prevRows; r++) {
        for (int c = 0; c < prevCols; c++) {
          prevPuzzle[z * prevDept * prevRows + r * prevRows + c] = data.get(r).charAt(c);
        }
      }
    }

    // generate "next" iteration board with more values in each dimension
    int dept = prevDept + 2;
    int rows = prevRows + 2;
    int cols = prevCols + 2;
    char[] puzzle = new char[dept * rows * cols];

    // fill the board with "inactive" ('.') values
    Arrays.fill(puzzle, '.');

    // copy over the previous state of the board in the center of the expanded new puzzle board
    for (int z = 0; z < dept; z++) {
      if (z - 1 < 0 || z > prevDept) continue;
      for (int r = 0; r < rows; r++) {
        if (r - 1 < 0 || r > prevRows) continue;
        for (int c = 0; c < cols; c++) {
          if (c - 1 < 0 || c > prevCols) continue;
          puzzle[z * cols * rows + r * rows + c] =
              prevPuzzle[((z - 1) * prevRows * prevCols) + ((r - 1) * prevRows) + c - 1];
        }
      }
    }
    int count = 0;
    do {
      // Perform the game of life calculations
      processPuzzle(puzzle, 1, dept, rows, cols);

      // set up previous values for moving forward
      prevDept = dept;
      prevRows = rows;
      prevCols = cols;
      prevPuzzle = new char[puzzle.length];
      for (int i = 0; i < puzzle.length; i++) {
        prevPuzzle[i] = puzzle[i];
      }

      // prepare for the next iteration (expand the puzzle by 2 in every dimension)
      dept = prevDept + 2;
      rows = prevRows + 2;
      cols = prevCols + 2;
      puzzle = new char[dept * rows * cols];
      Arrays.fill(puzzle, '.');

      // copy over the previous state of the board in the center of the expanded new puzzle board
      for (int z = 0; z < dept; z++) {
        if (z - 1 < 0 || z > prevDept) continue;
        for (int r = 0; r < rows; r++) {
          if (r - 1 < 0 || r > prevRows) continue;
          for (int c = 0; c < cols; c++) {
            if (c - 1 < 0 || c > prevCols) continue;
            puzzle[z * cols * rows + r * rows + c] =
                prevPuzzle[((z - 1) * prevRows * prevCols) + ((r - 1) * prevRows) + c - 1];
          }
        }
      }

      // increment count
      count++;
    } while (count < 6);
    long[] finalPuzzle = new long[puzzle.length];

    // apply "binary mask" to the puzzle, '#' (active) == 1, '.' (inactive) == 0
    for (int i = 0; i < puzzle.length; i++) {
      finalPuzzle[i] = puzzle[i] == '#' ? 1L : 0L;
    }
    return Arrays.stream(finalPuzzle).sum();
  }
  /**
   * Takes the input and does a 4D Conway's game of life on the input for 6 iterations and returns
   * the number of active cells after 6 iterations.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of active cells in the 4D board after 6 iterations
   */
  public static long part2(String fileName) {
    List<String> data = readFile(fileName);

    // Generate initial puzzle board;
    int prevTime = 1;
    int prevDept = 1;
    int prevRows = data.size();
    int prevCols = data.get(0).length();
    char[] prevPuzzle = new char[prevTime * prevDept * prevRows * prevCols];
    for (int w = 0; w < prevTime; w++) {
      for (int z = 0; z < prevDept; z++) {
        for (int r = 0; r < prevRows; r++) {
          for (int c = 0; c < prevCols; c++) {
            prevPuzzle[
                    w * prevDept * prevRows * prevCols
                        + z * prevRows * prevCols
                        + r * prevRows
                        + c] =
                data.get(r).charAt(c);
          }
        }
      }
    }

    // generate "next" iteration board with more values in each dimension
    int time = prevTime + 2;
    int dept = prevDept + 2;
    int rows = prevRows + 2;
    int cols = prevCols + 2;
    char[] puzzle = new char[time * dept * rows * cols];

    // fill the board with "inactive" ('.') values
    Arrays.fill(puzzle, '.');

    // copy over the previous state of the board in the center of the expanded new puzzle board
    for (int w = 0; w < time; w++) {
      if (w - 1 < 0 || w > prevTime) continue;
      for (int z = 0; z < dept; z++) {
        if (z - 1 < 0 || z > prevDept) continue;
        for (int r = 0; r < rows; r++) {
          if (r - 1 < 0 || r > prevRows) continue;
          for (int c = 0; c < cols; c++) {
            if (c - 1 < 0 || c > prevCols) continue;
            puzzle[w * dept * rows * cols + z * cols * rows + r * rows + c] =
                prevPuzzle[
                    ((w - 1) * prevDept * prevRows * prevCols)
                        + ((z - 1) * prevRows * prevCols)
                        + ((r - 1) * prevRows)
                        + c
                        - 1];
          }
        }
      }
    }
    int count = 0;
    do {
      // Perform the game of life calculations
      processPuzzle(puzzle, time, dept, rows, cols);

      // set up previous values for moving forward
      prevTime = time;
      prevDept = dept;
      prevRows = rows;
      prevCols = cols;
      prevPuzzle = new char[puzzle.length];
      for (int i = 0; i < puzzle.length; i++) {
        prevPuzzle[i] = puzzle[i];
      }

      // generate "next" iteration board with more values in each dimension
      time = prevTime + 2;
      dept = prevDept + 2;
      rows = prevRows + 2;
      cols = prevCols + 2;
      puzzle = new char[time * dept * rows * cols];

      // fill the board with "inactive" ('.') values
      Arrays.fill(puzzle, '.');

      // copy over the previous state of the board in the center of the expanded new puzzle board
      for (int w = 0; w < time; w++) {
        if (w - 1 < 0 || w > prevTime) continue;
        for (int z = 0; z < dept; z++) {
          if (z - 1 < 0 || z > prevDept) continue;
          for (int r = 0; r < rows; r++) {
            if (r - 1 < 0 || r > prevRows) continue;
            for (int c = 0; c < cols; c++) {
              if (c - 1 < 0 || c > prevCols) continue;
              puzzle[w * dept * rows * cols + z * cols * rows + r * rows + c] =
                  prevPuzzle[
                      ((w - 1) * prevDept * prevRows * prevCols)
                          + ((z - 1) * prevRows * prevCols)
                          + ((r - 1) * prevRows)
                          + c
                          - 1];
            }
          }
        }
      }

      // increment count
      count++;
    } while (count < 6);
    final long[] finalPuzzle = new long[puzzle.length];

    // apply "binary mask" to the puzzle, '#' (active) == 1, '.' (inactive) == 0
    for (int i = 0; i < puzzle.length; i++) {
      finalPuzzle[i] = puzzle[i] == '#' ? 1L : 0L;
    }
    return Arrays.stream(finalPuzzle).sum();
  }

  /**
   * Performs the Conway's Game of Life logic for a multidimensional board. The board is first
   * copied to a new board to allow all changes to take place at once based on the current state of
   * the board.
   *
   * @param puzzle puzzle board as a character array, however the inner dimensions are provided in
   *     the time, depth, rows, and cols passed into the method
   * @param time number of times in the 4D puzzle passed in as a 1D array
   * @param depth number of depths in the 4D puzzle passed in as a 1D array
   * @param rows number of rows in the 4D puzzle passed in as a 1D array
   * @param cols number of columns in the 4D puzzle passed in as a 1D array
   */
  public static void processPuzzle(char[] puzzle, int time, int depth, int rows, int cols) {
    char[] origPuzzle = new char[puzzle.length];
    for (int i = 0; i < puzzle.length; i++) {
      origPuzzle[i] = puzzle[i];
    }
    for (int w = 0; w < time; w++) {
      for (int z = 0; z < depth; z++) {
        for (int r = 0; r < rows; r++) {
          for (int c = 0; c < cols; c++) {
            int neighborCount = 0;
            for (int w2 = w - 1; w2 <= w + 1; w2++) {
              for (int z2 = z - 1; z2 <= z + 1; z2++) {
                for (int r2 = r - 1; r2 <= r + 1; r2++) {
                  for (int c2 = c - 1; c2 <= c + 1; c2++) {
                    if (w2 < 0
                        || w2 >= time
                        || z2 < 0
                        || r2 < 0
                        || c2 < 0
                        || z2 >= depth
                        || r2 >= rows
                        || c2 >= cols
                        || (w2 == w && z2 == z && r2 == r && c2 == c)) continue;
                    if (origPuzzle[w2 * depth * rows * cols + z2 * rows * cols + r2 * rows + c2]
                        == '#') neighborCount++;
                  }
                }
              }
            }
            if (puzzle[w * depth * rows * cols + z * rows * cols + r * rows + c] == '#'
                && (neighborCount < 2 || neighborCount > 3)) {
              puzzle[w * depth * rows * cols + z * rows * cols + r * rows + c] = '.';
            } else if (puzzle[w * depth * rows * cols + z * rows * cols + r * rows + c] == '.'
                && neighborCount == 3) {
              puzzle[w * depth * rows * cols + z * rows * cols + r * rows + c] = '#';
            }
          }
        }
      }
    }
  }
}
