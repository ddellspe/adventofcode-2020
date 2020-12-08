package net.ddellspe.day8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HandheldHalting {

  /**
   * Read in the file and return the list of {@link Instruction}s within it.
   *
   * @param fileName the name of the file (within the resource path) to read in
   * @return the list of {@link Instruction}s contained within the file.
   */
  public static List<Instruction> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(HandheldHalting.class.getResourceAsStream(fileName)))) {
      List<Instruction> bags = new ArrayList<Instruction>();
      bags = reader.lines().map(row -> new Instruction(row)).collect(Collectors.toList());
      return bags;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Runs through the instructions (jmp increases index by the value, acc increases the accumulator
   * by the value then increments the index by 1, nop increments the index by 1) and waits until an
   * instruction has been reached for the second time. Once an instruction has been reached for a
   * second time, the accumulator prior to the second instance of an index is returned.
   *
   * @param fileName the name of the file (within the resource path) to read in
   * @return the accumulator value when the next instruction has already been processed
   */
  public static int part1(String fileName) {
    List<Instruction> instructions = readFile(fileName);
    int accumulator = 0;
    int index = 0;
    Set<Integer> visitedInstructions = new HashSet<Integer>();
    while (!visitedInstructions.contains(index)) {
      Instruction curInst = instructions.get(index);
      visitedInstructions.add(index);
      switch (curInst.getCommand()) {
        case "acc":
          accumulator += curInst.getValue();
          index++;
          break;
        case "jmp":
          index += curInst.getValue();
          break;
        default:
          index++;
      }
    }
    return accumulator;
  }

  /**
   * Runs through the instructions(jmp increases index by the value, acc increases the accumulator
   * by the value then increments the index by 1, nop increments the index by 1) and waits until an
   * instruction has been reached for the second time. While running through, it collects the
   * indices of the jmp and nop instructions for later migration from jmp to nop and nop to jmp to
   * see if those complete successfully. If one of those completes successfully, the program is
   * halted and returns the final accumulator value for that run as the final solution.
   *
   * @param fileName the name of the file (within the resource path) to read in
   * @return the accumulator value when a single instruction is changed either from jmp to nop or
   *     from nop to jmp and completes (index > size of instructions)
   */
  public static int part2(String fileName) {
    List<Instruction> instructions = readFile(fileName);
    int accumulator = 0;
    int index = 0;
    Set<Integer> visitedInstructions = new HashSet<Integer>();
    Set<Integer> jmpInstructions = new HashSet<Integer>();
    Set<Integer> nopInstructions = new HashSet<Integer>();
    while (!visitedInstructions.contains(index) || index > visitedInstructions.size()) {
      Instruction curInst = instructions.get(index);
      visitedInstructions.add(index);
      switch (curInst.getCommand()) {
        case "acc":
          accumulator += curInst.getValue();
          index++;
          break;
        case "jmp":
          jmpInstructions.add(index);
          index += curInst.getValue();
          break;
        case "nop":
          nopInstructions.add(index);
          index++;
      }
    }
    for (int jmpToNopChange : jmpInstructions) {
      accumulator = runBootSequence(instructions, jmpToNopChange, -1);
      if (accumulator > -1) {
        break;
      }
    }
    if (accumulator > -1) {
      return accumulator;
    }
    for (int nopToJmpChange : nopInstructions) {
      accumulator = runBootSequence(instructions, -1, nopToJmpChange);
      if (accumulator > -1) {
        break;
      }
    }
    return accumulator;
  }

  /**
   * Runs the boot sequence (goes through the instructions) changing a single jmp to nop instruction
   * or single nop to jmp instruction. If the boot sequence completes successfully (index ends up
   * more than the size of the instruction set) it returns the accumulator value, otherwise -1.
   *
   * @param instructions list of {@link Instruction}s that codify the boot sequence
   * @param jmpToNop the index to change from jmp to nop for the boot attempt
   * @param nopToJmp the index to change from nop to jmp for the boot attempt
   * @return the accumulator value if the boot sequence completes successfully, otherwise -1
   */
  public static int runBootSequence(List<Instruction> instructions, int jmpToNop, int nopToJmp) {
    int accumulator = 0;
    int index = 0;
    Set<Integer> visitedInstructions = new HashSet<Integer>();
    while (!visitedInstructions.contains(index) && index < instructions.size()) {
      Instruction curInst = instructions.get(index);
      visitedInstructions.add(index);
      String command = curInst.getCommand();
      if (index == jmpToNop) {
        command = "nop";
      }
      if (index == nopToJmp) {
        command = "jmp";
      }
      switch (command) {
        case "acc":
          accumulator += curInst.getValue();
          index++;
          break;
        case "jmp":
          index += curInst.getValue();
          break;
        case "nop":
          index++;
      }
    }
    if (index >= instructions.size()) {
      return accumulator;
    }
    return -1;
  }

  public static class Instruction {
    private String command;
    private int value;

    public Instruction(String row) {
      command = row.split(" ")[0];
      value = Integer.parseInt(row.split(" ")[1]);
    }

    public String getCommand() {
      return command;
    }

    public int getValue() {
      return value;
    }
  }
}
