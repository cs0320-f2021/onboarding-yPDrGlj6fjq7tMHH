package edu.brown.cs.student.main;

import java.util.Comparator;

public class Star {
  private String starID;
  private float distance;

  // Constructor
  public Star(int starID, float distance) {
    starID = starID;
    distance = distance;
  }

  public float getDistance() {
    return distance;
  }

  public String getStarID() {
    return starID;
  }
}

// Used to sort stars by distance
class SortByDistance implements Comparator<Star> {
  public int compare(Star star1, Star star2) {
    if (star1.getDistance() < star2.getDistance()) {
      return -1;
    } else if (star1.getDistance() > star2.getDistance()) {
      return 1;
    } else {
      return 0;
    }
  }
}
