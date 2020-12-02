package net.ddellspe.day2;

public class PasswordEntry {
  private int num1;
  private int num2;
  private char character;
  private String password;

  public PasswordEntry(String passwordRow) {
    num1 = Integer.parseInt(passwordRow.split(" ")[0].split("-")[0]);
    num2 = Integer.parseInt(passwordRow.split(" ")[0].split("-")[1]);
    character = passwordRow.split(" ")[1].split(":")[0].charAt(0);
    password = passwordRow.split(" ")[2];
  }

  public int getNum1() {
    return num1;
  }

  public int getNum2() {
    return num2;
  }

  public char getCharacter() {
    return character;
  }

  public String getPassword() {
    return password;
  }

  /**
   * Checks whether based on the PasswordEntry attributes, that the password is valid. In part 1, a
   * valid password has between num1 and num2 (inclusive) instances of the character specified by
   * character.
   *
   * @return true if the password is valid, false if the password is invalid
   */
  public boolean isValidPasswordPart1() {
    long charCount = password.chars().filter(ch -> ch == character).count();
    if (charCount >= num1 && charCount <= num2) {
      return true;
    }
    return false;
  }

  /**
   * Checks whether based on the PasswordEntry attributes, that the password is valid. In Part 2, a
   * valid password has the character at EITHER position num1 or num2, but not both. If a password
   * doesn't have the character and either position num1 or num2, it will also be invalid.
   *
   * @return true if the password is valid, false if the password is invalid
   */
  public boolean isValidPasswordPart2() {
    if (password.charAt(num1 - 1) == character) {
      if (password.charAt(num2 - 1) == character) {
        return false;
      } else {
        return true;
      }
    } else {
      if (password.charAt(num2 - 1) == character) {
        return true;
      } else {
        return false;
      }
    }
  }

  @Override
  public String toString() {
    return "PasswordEntry [num1="
        + num1
        + ",num2="
        + num2
        + ",character='"
        + character
        + "',password=\""
        + password
        + "\"]";
  }
}
