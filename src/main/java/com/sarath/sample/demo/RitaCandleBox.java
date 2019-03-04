package com.sarath.sample.demo;

import java.util.Scanner;

public class RitaCandleBox {

  private static final int THEO_START_ADDING_CANDLE_AGE = 3;
  private static final int RITA_START_ADDING_CANDLE_AGE = 4;
  public static int ageDifference = 0, noCandleRita = 0, noCandleTheo = 0;
  public static int[] consoleInput = new int[3];


  public static void main(String[] args) {
    getUserInput();
    int candleToRemove = candleToBeThrown();
    System.out.println(candleToRemove);
  }

  /*
  To get the user input from console

  @InputMismatchException will throw if the input is not valid
   */
  private static void getUserInput() {
    readInputFromConsole();
  }

  /*
  Method to read input from the console
   */
  private static void readInputFromConsole() {
    int i = 0;
    Scanner sc = new Scanner(System.in);
    while (i <= 2) {
      consoleInput[i] = Integer.parseInt(sc.next().trim());
      i++;
    }
    ageDifference = consoleInput[0];
    noCandleRita = consoleInput[1];
    noCandleTheo = consoleInput[2];
  }

  /*
  Method to find the number of extra candle
   */
  private static int candleToBeThrown() {
    int value = 0;
    int theoArr[] = new int[70];
    int ritaArr[] = new int[70];
    for (int i = 0; i < theoArr.length; i++) {
      if (i >= THEO_START_ADDING_CANDLE_AGE) {
        theoArr[i] = i + theoArr[i - 1];
      }
      if (i >= RITA_START_ADDING_CANDLE_AGE) {
        ritaArr[i] = i + ritaArr[i - 1];
      }
    }

    for (int i = ageDifference; i < theoArr.length; i++) {
      if (theoArr[i - ageDifference] + ritaArr[i] == noCandleRita + noCandleTheo) {
        value = (noCandleRita - ritaArr[i]);
      }
    }
    return value;
  }
}
