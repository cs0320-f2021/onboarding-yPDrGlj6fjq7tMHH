package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarProcessor {

  private List<String[]> starData = new ArrayList<>();
  private String[] starHeaders;
  private Star[] starsNearby;
  private float originX;
  private float originY;
  private float originZ;
  private String file;
  private String searchedName;

  /**
   * Default constructor.
   */
  public StarProcessor() {

  }

  /**
   * Reads and parses the inputted CSV file.
   *
   * @param filename - file path to stars CSV file
   */
  public void processCSV(String filename) {
    file = filename;
    try (BufferedReader br2 = new BufferedReader(new FileReader(filename))) {
      String line;
      line = br2.readLine();
      line = line.trim();
      starHeaders = line.split(",");
      while ((line = br2.readLine()) != null) {
        try {
          line = line.trim();
          String[] starTraits = line.split(",");
          starData.add(starTraits);
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println("ERROR: Couldn't read input");
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR: Invalid file name");
    }
  }

  /**
   * Handles the input of (x,y,z) coords to find nearest neighbors.
   *
   * @param k - the number of neighbors to return
   * @param x - x coordinate
   * @param y - y coordinate
   * @param z - z coordinate
   */
  public void neighborsCoords(int k, float x, float y, float z) {
    originX = x;
    originY = y;
    originZ = z;

    this.findNeighbors(k, "Coords");
  }

  /**
   * Handles the input of a star name to find its nearest neighbors.
   *
   * @param k    - the number of neighbors to return
   * @param name - name of the star
   */
  public void neighborsName(int k, String name) {
    String trimmedName = null;
    try {
      trimmedName = name.substring(1, name.length() - 1);
      findStar(trimmedName);
    } catch (Exception e) {
      System.out.println("Star name not found.");
    }

    this.searchedName = trimmedName;
    findNeighbors(k, "Name");
  }

  /**
   * Algorithm used by neighborsCoords() and neighborsName() to find nearest neighbors.
   *
   * @param k           - number of neighbors to return
   * @param funcVersion - what function is calling findNeighbors.
   */
  private void findNeighbors(int k, String funcVersion) {
    int listLength = Math.min(k, starData.size());
    starsNearby = new Star[listLength];

    float distance;
    float starX;
    float starY;
    float starZ;

    for (String[] starToCheck : starData) {

      if (funcVersion.equals("Name")) {
        if (starToCheck[1].equals(searchedName)) {
          continue;
        }
      }

      String starID = starToCheck[0];
      starX = Float.parseFloat(starToCheck[2]);
      starY = Float.parseFloat(starToCheck[3]);
      starZ = Float.parseFloat(starToCheck[4]);
      distance = calcDistanceSqrd(starX, starY, starZ);

      // int i = listLength - 1; i >= 0; i--
      for (int i = 0; i < listLength; i++) {
        if (starsNearby[i] == null || distance < starsNearby[i].getDistance()) {
          starsNearby[i] = new Star(starID, distance);

          if (starsNearby.length > 1) {
            Arrays.sort(starsNearby, new SortByDistance());
          }
          break;
        }
      }
    }
  }

  /**
   * Calculates the squared distance between origin point and star location. The distance
   * is kept squared to save time from program having to compute the squareroot.
   *
   * @param x - x coordinate of given star
   * @param y - y coordinate of given star
   * @param z - z cooridnate of given star
   * @return - distance between origin and star as a flaot
   */
  private float calcDistanceSqrd(float x, float y, float z) {
    float xComponent = (float) Math.pow((originX - x), 2);
    float yComponent = (float) Math.pow((originY - y), 2);
    float zComponent = (float) Math.pow((originZ - z), 2);

    return xComponent + yComponent + zComponent;
  }

  /**
   * Helper function to neighborsName(). Find the coordinates of the inputted star.
   *
   * @param name - name of inputted star
   * @throws Exception - Exception if star name is not found in CSV file
   */
  private void findStar(String name) throws Exception {
    for (String[] starToCheck : starData) {
      if (starToCheck[1].equals(name)) {
        originX = Float.parseFloat(starToCheck[2]);
        originY = Float.parseFloat(starToCheck[3]);
        originZ = Float.parseFloat(starToCheck[4]);
        return;
      }
    }
    throw new Exception("Star name not found.");
  }

  /**
   * Prints the nearest neighbors to the terminal. Called by neighborsName() and
   * neighborsCoords()
   */
  public void printNeighbors() {
    System.out.println("Read " + starData.size() + " stars from " + file);
    for (Star star : starsNearby) {
      System.out.println(star.getStarID());
    }
  }

  // Getter for starHeaders Array
  public String[] getStarHeaders() {
    return starHeaders.clone();
  }

  // Getter for starData List
  public List<String[]> getStarData() {
    List<String[]> copyOfStarData = new ArrayList<>();
    copyOfStarData.addAll(starData);
    return copyOfStarData;
  }

  // Getter for filename String
  public String getFile() {
    return file;
  }
}
