package com.sarath.sample.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Functional Smooothies Inc. is releasing a new smoothie machine that will make the best icy fruit beverages of all time. According to Wikipedia:
 *
 * A smoothie (occasionally spelled smoothee or smoothy) is a thick, cold beverage made from pureed raw fruit blended with ice cream or frozen yogurt along with other ingredients such as crushed ice, fruit juice, sweeteners, dairy products, nuts, seeds, etc.
 *
 * In order to sell the machine to smoothie vendors all over the world, Functional Smooothies needs to ensure that the machine takes all dietary preferences and allergies into account. They have hired you to write the software for the machine.
 *
 * The software has a menu of standard smoothie options, including the ingredients for each drink. When a customer places their order, they supply a list of zero or more dietary restrictions that must be honoured. Your software will print out a list of the ingredients that the smoothie operator needs to put into the machine.
 *
 * Menu
 * The menu options, along with the ingredients needed for each are as follows.
 *
 * Classic: strawberry, banana, pineapple, mango, peach, honey
 * Freezie: blackberry, blueberry, black currant, grape juice, frozen yogurt
 * Greenie: green apple, lime, avocado, spinach, ice, apple juice
 * Just Desserts: banana, ice cream, chocolate, peanut, cherry
 * Input
 * You should write a function called ingredients, which takes as input a string containing item from the menu and optionally one or more ingredients to omit from the smoothie, separated by commas.
 *
 * Restricted ingredients have to be preceded by - sign, as opposed to the ones that should be added. Note that:
 *
 * It is valid to have the allergens not present in the ordered smoothie. Those ingredients will just be ignored and no exception should be thrown.
 * As adding new ingredients is not supported yet, any input with additional ingredients is considered invalid and an IllegalArgumentException should be thrown.
 * In very rare cases of the user input processor mechanical failures, their order might be lost or arrive empty to the software.
 *
 * Output
 * The function should return a string listing the ingredients that the operator needs to put in. To make it more convenient for the operator, the ingredients should be listed in alphabetical order and separated by commas in the string returned from the function.
 *
 * A smoothie with no ingredients is represented as "". In case of input being invalid, an IllegalArgumentException should be thrown.
 *
 * Examples
 * if a customer orders a Classic but is allergic to strawberry and peanuts, your function will be called with the argument "Classic,-strawberry,-peanut" and should return "banana,honey,mango,peach,pineapple". Note that peanut was ignored as it is not a part of the Classic menu.
 * Requesting Classic with additional chocolate "Classic,chocolate" should result in IllegalArgumentException being thrown.
 * Same result should apply for requesting a smoothie that is not present in the menu "Vitamin smoothie".
 *
 */

public class Smoothie {

    private static Map<String, String> menu = new HashMap();

    static {
        menu.put("Classic", "banana,honey,mango,peach,pineapple,strawberry");
        menu.put("Freezie", "blackberry,blueberry,black currant,grape juice,frozen yogurt");
        menu.put("Greenie", "green apple,lime,avocado,spinach,ice,apple juice");
        menu.put("Just Desserts", "banana,ice cream,chocolate,peanut,cherry");
    }

    public static String ingredients(String order) throws IllegalArgumentException {
        String[] userInput = order.split(",");
        if (isSmoothieAvailable(userInput[0])) {
            return extractIngredients(userInput);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static boolean isSmoothieAvailable(String smoothieName) {
        return menu.containsKey(smoothieName);
    }

    private static String extractIngredients(String[] userInput) throws IllegalArgumentException {
        if (userInput.length == 1) {
            return menu.get(userInput[0]);
        } else {
            String[] menuValue = menu.get(userInput[0]).split(",");
            return trimIngredientsTest(menuValue, userInput);
        }
    }

    private static String trimIngredientsTest(String[] menuDefaultContent, String[] userInput) throws IllegalArgumentException {
        String finalValue = "";
        for (int i = 1; i < userInput.length; i++) {
            if (checkHyphenInUserInput(userInput[i])) {
                String userIngredient = removeFirstCharacter(userInput[i].trim());
                if (checkIngredientAvailable(menuDefaultContent, userIngredient)) {
                    finalValue = removeFromDefaultList(menuDefaultContent, userIngredient);
                }
            } else {
                if (checkIngredientAvailable(menuDefaultContent, userInput[i].trim())) {
                    finalValue = removeFromDefaultList(menuDefaultContent, userInput[i].trim());
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        return finalValue;
    }

    private static boolean checkIngredientAvailable(
            String[] menuDefaultContent, String userIngredient) {
        for (int i = 0; i < menuDefaultContent.length; i++) {
            if (menuDefaultContent[i].equalsIgnoreCase(userIngredient)) {
                return true;
            }
        }
        return false;
    }

    private static String removeFromDefaultList(String[] menuDefaultContent, String userIngredient) {
        String[] newMenuDefaultContent = new String[menuDefaultContent.length - 1];
        for (int i = 0, j = 0; j < menuDefaultContent.length; i++, j++) {
            if (!menuDefaultContent[j].trim().equalsIgnoreCase(userIngredient)) {
                newMenuDefaultContent[i] = menuDefaultContent[j];
            } else {
                i--;
            }
        }
        return arrayToString(newMenuDefaultContent);
    }

    private static String arrayToString(String[] newMenuDefaultContent) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < newMenuDefaultContent.length; i++) {
            builder.append(newMenuDefaultContent[i].trim()).append(",");
        }
        return removeLastCharacter(builder.toString());
    }

    private static boolean checkHyphenInUserInput(String userIngredient) {
        return userIngredient.contains("-");
    }

    private static String removeFirstCharacter(String str) {
        return Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(1))
                .orElse(str);
    }

    public static String removeLastCharacter(String str) {
        String result = Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
        return result;
    }

}
