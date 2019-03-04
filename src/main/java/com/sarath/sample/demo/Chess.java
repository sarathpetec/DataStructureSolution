package com.sarath.sample.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Chess {

  public static String[] consoleInput = new String[5];
  public static int noOfTestCase = 0;
  public int MAX_NUMBER_X_AXIS_MOVEMENT = 9;
  public int MAX_NUMBER_Y_AXIS_MOVEMENT = 9;

  public static void main(String[] args) {
    getUserInput();

  }

  /*
  To get the user input from console
   */
  private static void getUserInput() {
    Chess problemB = new Chess();
    Scanner sc = new Scanner(System.in);
    readNumberOfTestCase(sc);
    problemB.printMovement(sc);
  }

  /*
  Method to read input from the console
   */
  private static void readNumberOfTestCase(Scanner sc) {
    noOfTestCase = sc.nextInt();
  }

  boolean boardChecking(int x, int y) {
    return !(x > 8 || x < 1 || y > 8 || y < 1);
  }

  char c(int num) {
    return (char) (num + 'A' - 1);
  }

  public void printMovement(Scanner sc) {
    for (int i = 0; i < noOfTestCase; i++) {
      int consoleCount = 0;
      while (consoleCount <= 3) {
        String value = "";
        if(sc.hasNext()){
          value = sc.next();
        }
        consoleInput[consoleCount] = value;
        consoleCount++;
      }
      //System.out.println(consoleInput[0]+","+consoleInput[1]+","+consoleInput[2]+","+consoleInput[3]);

      int y1 = Integer.parseInt(consoleInput[1]), y2 = Integer.parseInt(consoleInput[3]);
      char a = consoleInput[0].charAt(0);
      char b = consoleInput[2].charAt(0);
      int x1 = a - 'A' + 1;
      int x2 = b - 'A' + 1;

      Position position1 = new Position(x1, y1);
      Position position2 = new Position(x2, y2);

      List<Position> pos1 = new ArrayList<>();
      List<Position> pos2 = new ArrayList<>();
      boolean printed = false;
      // If both positions are same then,
      if (position1.xCoordinate == position2.xCoordinate && position1.yCoordinate == position2.yCoordinate) {
        System.out.println("0 " + a + " " + y1);
        printed = true;
      }

      // Get the possible movements of position1
      for (int j = 0; j < MAX_NUMBER_X_AXIS_MOVEMENT; j++) {
        if (boardChecking(position1.xCoordinate + j, position1.yCoordinate + j)) {
          Position pTemp1 = new Position(position1.xCoordinate + j, position1.yCoordinate + j);
          pos1.add(pTemp1);
        }
        if (boardChecking(position1.xCoordinate + j, position1.yCoordinate - j)) {
          Position pTemp2 = new Position(position1.xCoordinate + j, position1.yCoordinate - j);
          pos1.add(pTemp2);
        }
        if (boardChecking(position1.xCoordinate - j, position1.yCoordinate + j)) {
          Position pTemp3 = new Position(position1.xCoordinate - j, position1.yCoordinate + j);
          pos1.add(pTemp3);
        }
        if (boardChecking(position1.xCoordinate - j, position1.yCoordinate - j)) {
          Position pTemp4 = new Position(position1.xCoordinate - j, position1.yCoordinate - j);
          pos1.add(pTemp4);
        }
      }

      // If position2 in position1's moves
      for (int j = 0; j < pos1.size(); j++) {
        if (pos1.get(j).xCoordinate == position2.xCoordinate && pos1.get(j).yCoordinate == position2.yCoordinate) {
          if (!printed) {
            System.out.println("1 " + c(position1.xCoordinate) + " " + position1.yCoordinate + " " + c(position2.xCoordinate) + " " + position2.yCoordinate);
            printed = true;
          }
        }
      }

      // Get position2's possible moves
      for (int j = 0; j < MAX_NUMBER_Y_AXIS_MOVEMENT; j++) {
        if (boardChecking(position2.xCoordinate + j, position2.yCoordinate + j)) {
          Position pTemp4 = new Position(position2.xCoordinate + j, position2.yCoordinate + j);
          pos2.add(pTemp4);
        }
        if (boardChecking(position2.xCoordinate + j, position2.yCoordinate - j)) {
          Position pTemp5 = new Position(position2.xCoordinate + j, position2.yCoordinate - j);
          pos2.add(pTemp5);
        }
        if (boardChecking(position2.xCoordinate - j, position2.yCoordinate + j)) {
          Position pTemp6 = new Position(position2.xCoordinate - j, position2.yCoordinate + j);
          pos2.add(pTemp6);
        }
        if (boardChecking(position2.xCoordinate - j, position2.yCoordinate - j)) {
          Position pTemp7 = new Position(position2.xCoordinate - j, position2.yCoordinate - j);
          pos2.add(pTemp7);
        }
      }

      // If position1 and position2 share moves
      int found = -1;
      for (int j = 0; j < pos1.size(); j++) {
        for (int k = 0; k < pos2.size(); k++) {
          if (pos1.get(j).xCoordinate == pos2.get(k).xCoordinate && pos1.get(j).yCoordinate == pos2.get(k).yCoordinate) {
            found = j;
          }
        }
      }

      // If it’s not possible to move a bishop from X to  Y
      if (found == -1) {
        System.out.println("Impossible");
        continue;
      }

      // If multiple moves
      Position mid = pos1.get(found);
      if (!printed) {
        System.out.println("2 " + c(position1.xCoordinate) + " " + position1.yCoordinate + " " + c(mid.xCoordinate) + " " + mid.yCoordinate + " " + c(position2.xCoordinate) + " " + position2.yCoordinate);
      }
    }
  }

  class Position {
    int xCoordinate;
    int yCoordinate;

    Position(int x, int y) {
      this.xCoordinate = x;
      this.yCoordinate = y;

    }

  }
}
