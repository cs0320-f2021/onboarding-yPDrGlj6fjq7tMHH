package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.List;

public class StarProcessorTest {

  @Test
  public void testWorksAsIntended() {
    StarProcessor starBot = new StarProcessor();
    starBot.processCSV("data/stars/one-star.csv");
    List<String[]> stars = starBot.getStarData();
    String[] starTraits = stars.get(0);
    String[] starHeaders = starBot.getStarHeaders();

    assertEquals("1", starTraits[0]);
    assertEquals("Lonely Star", starTraits[1]);
    assertEquals("10.04", starTraits[4]);
    assertEquals("StarID", starHeaders[0]);
    assertEquals("ProperName", starHeaders[1]);
  }

  @Test
  public void testNoName() {
    StarProcessor starBot = new StarProcessor();
    starBot.processCSV("data/stars/ten-star.csv");

    List<String[]> stars = starBot.getStarData();
    String[] starTraits = stars.get(1);

    assertEquals("", starTraits[1]);
  }

//  @Test
//  public void testNeighboringStars() {
//    StarProcessor starBot = new StarProcessor();
//    starBot.processCSV("data/stars/ten-star.csv");
//    starBot.neighborsName(5, "Sol");
//
//    Star[] sortedStars = starBot.getStarsNearby();
//    String starID = sortedStars[0].getStarID();
//
//    assertEquals(starID, "70667");
//  }
}
