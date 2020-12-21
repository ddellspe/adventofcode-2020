package net.ddellspe.day21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21 {

  /**
   * Reads in the map from the file and provides it in a streamable list of strings.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of strings where each string is a line in the file
   */
  public static List<String> readFile(String fileName) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(Day21.class.getResourceAsStream(fileName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Returns the total number of ingredients that do not have a known allergen associated with them.
   * This is not the distinct list of ingredients but in all of the shopping list the count of total
   * number of ingredients.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the number of ingredients with no known allergen
   */
  public static long part1(String fileName) {
    List<String> data = readFile(fileName);
    List<Food> foods = data.stream().map(row -> new Food(row)).collect(Collectors.toList());
    Map<String, String> ingredientAllergenMap = getIngredientAllergenMap(foods);
    List<String> allIngredients =
        foods.stream().flatMap(food -> food.ingredients.stream()).collect(Collectors.toList());
    return allIngredients.stream()
        .filter(
            ingredient ->
                !ingredientAllergenMap.values().stream()
                    .collect(Collectors.toList())
                    .contains(ingredient))
        .count();
  }

  /**
   * Reads in the foods, and produces the comma-separated list of the ingredients for the allergens
   * in the order of the allergens alphabetically. If the ingredient to allergen map is foo -> soy,
   * bar -> dairy, baz -> nut the return would be bar,baz,foo.
   *
   * @param fileName The name of the file (in the resource path) to read in
   * @return the list of the ingredients for the allergens in the order of the allergens
   *     alphabetically
   */
  public static String part2(String fileName) {
    List<String> data = readFile(fileName);
    List<Food> foods = data.stream().map(row -> new Food(row)).collect(Collectors.toList());
    Map<String, String> ingredientAllergenMap = getIngredientAllergenMap(foods);
    return ingredientAllergenMap.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(entry -> entry.getValue())
        .collect(Collectors.joining(","));
  }

  /**
   * Takes the list of Foods and generates the map of the single ingredient to the respective
   * allergen
   *
   * @param foods List of {@link Food} objects
   * @return Map of single ingredient to single allergen
   */
  public static Map<String, String> getIngredientAllergenMap(List<Food> foods) {
    Set<String> allergens =
        foods.stream()
            .flatMap(food -> food.allergens.stream())
            .distinct()
            .collect(Collectors.toSet());
    Map<String, Set<String>> allergenIngredients = new HashMap<String, Set<String>>();
    for (String allergen : allergens) {
      Set<String> ingredients =
          foods.stream()
              .flatMap(food -> food.ingredients.stream())
              .distinct()
              .collect(Collectors.toSet());
      for (Food food : foods) {
        if (!food.allergens.contains(allergen)) continue;
        ingredients =
            ingredients.stream()
                .distinct()
                .filter(food.ingredients::contains)
                .distinct()
                .collect(Collectors.toSet());
      }
      allergenIngredients.put(allergen, ingredients);
    }
    while (allergenIngredients.values().stream()
            .mapToInt(set -> set.size())
            .filter(size -> size > 1)
            .count()
        > 0) {
      List<String> singleIngredients =
          allergenIngredients.entrySet().stream()
              .filter(entry -> entry.getValue().size() == 1)
              .map(entry -> entry.getValue().iterator().next())
              .collect(Collectors.toList());
      allergenIngredients.entrySet().stream()
          .filter(entry -> entry.getValue().size() > 1)
          .forEach(
              entry ->
                  singleIngredients.stream()
                      .forEach(ingredient -> entry.getValue().remove(ingredient)));
    }
    return allergenIngredients.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().iterator().next()));
  }

  public static class Food {
    List<String> ingredients;
    List<String> allergens;

    public Food(String row) {
      ingredients =
          Arrays.stream(row.split(" \\(contains")[0].split(" ")).collect(Collectors.toList());
      allergens =
          Arrays.stream(row.split("\\(contains ")[1].split("\\)")[0].split(", "))
              .collect(Collectors.toList());
    }
  }
}
