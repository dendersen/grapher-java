package dk.mtdm;

import java.util.ArrayList;

import dk.mtdm.dataTypes.Point;

public class PointFind {
  public static ArrayList <Point> points = new ArrayList<Point>(); 
  
  public static ArrayList <Point> collectPoints(){
    ArrayList <Point> tempPoints = points;
    System.out.println("loaded " + tempPoints.size() + "points");
    return tempPoints;
  }
  
  public static void insertPoint(Point point){
    points.add(point);
  }
}