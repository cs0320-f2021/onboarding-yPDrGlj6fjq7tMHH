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

  public void processCSV(String filename) {
    file = filename;
    try (BufferedReader br2 = new BufferedReader(new FileReader(filename))) {
      String line;
      line = br2.readLine();
      line = line.trim();
      starHeaders = line.split(",");
      while ((line = br2.readLine()) != null) {
        line = line.trim();
        String[] starTraits = line.split(",");
        starData.add(starTraits);
      }
    } catch (Exception e) {
      System.out.println("ERROR: Invalid file name");
    }
  }

  public void neighborsCoords(int k, float x, float y, float z) {
    originX = x;
    originY = y;
    originZ = z;

    findNeighbors(k, "Coords");
  }

  public void neighborsName(int k, String name) {
    try {
      findStar(name);
    } catch (Exception e) {
      System.out.println("Star name not found.");
    }

    searchedName = name;
    findNeighbors(k, "Name");
  }

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

      int starID = Integer.parseInt(starToCheck[0]);
      starX = Float.parseFloat(starToCheck[2]);
      starY = Float.parseFloat(starToCheck[3]);
      starZ = Float.parseFloat(starToCheck[4]);
      distance = calcDistanceSqrd(starX, starY, starZ);

      for (int i = listLength - 1; i >= 0; i--) {
        if (starsNearby[i] == null || distance < starsNearby[i].getDistance()) {
          starsNearby[i] = new Star(starID, distance);
          Arrays.sort(starsNearby, new SortByDistance());
          break;
        }
      }
    }
  }

  private float calcDistanceSqrd(float x, float y, float z) {
    float xComponent = (float) Math.pow((originX - x), 2);
    float yComponent = (float) Math.pow((originY - y), 2);
    float zComponent = (float) Math.pow((originZ - z), 2);

    return xComponent + yComponent + zComponent;
  }

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

  public void printNeighbors() {
    System.out.println("Read " + starData.size() + " stars from " + file);
    for (Star star : starsNearby) {
      System.out.println(star.getStarID());
    }
  }

  public String[] getStarHeaders() {
    return starHeaders;
  }

  public List<String[]> getStarData() {
    return starData;
  }

  public String getFile() {
    return file;
  }

  public Star[] getStarsNearby() {
    return starsNearby;
  }
}
