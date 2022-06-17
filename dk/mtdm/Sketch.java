package dk.mtdm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

import processing.core.PApplet;

public class Sketch extends PApplet{
  
  public static ArrayList <Function<? super Float, ? extends Float>> lines = new ArrayList<Function<? super Float, ? extends Float>>();
  public static ArrayList <FunktionRunner> calculators = new ArrayList<FunktionRunner>();
  private static float[][] colorCodes;

  public static int height = 1000;
  public static int width = 1000;
  
  final private float DensityStart = 0.1f;
  final private int NumberOfThreads = 10;
  final private Function<? super Float, ? extends Float> DensityRamp = x ->x * 1.03f;

  float frameCheck = 100;

  public void main() {
    PApplet.main("Sketch");
  }
  
  @Override
  public void settings() {
    size(width, height);
  }
  
  @Override
  public void setup() {
    background(0f);
    stroke(255f);
    line(width/2, 0, width/2, height);
    line(0, height/2, width, height/2);
    strokeWeight(2);
    loadPreMade();
    loadTxtFile();
    initializeThreads();
  }

  private void initializeThreads() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for(int j = 0; j < lines.size() ; j++){
      
      for(int i = 0; i < NumberOfThreads ; i++){
        
        calculators.add(new FunktionRunner());
        calculators.get(i+j*NumberOfThreads).threadSetup(
          -width/2 + width/NumberOfThreads * i, 
          -width/2 + width/NumberOfThreads * (i + 1), 
          lines.get(j), 
          DensityStart, 
          DensityRamp,
          j
        );
        
        calculators.get(i+j*NumberOfThreads).start();
        calculators.get(i+j*NumberOfThreads).run();
      }
    }
    colorCodes = new float[lines.size()][3];
    for(int i = 0; i < lines.size() * 3;i++){
      colorCodes[i%lines.size()][(i-(i%lines.size()))%3] = -1;
    }
  }
  
  @Override
  public void draw(){
    if((byte) frameRate == 1){
      System.out.println("programe finished, awaiting closeing");
      return;
    }
    if(frameCheck < 15){
      frameRate(0.1f);
      System.out.println("stopping" + frameRate);
      return;
    }

    ArrayList <Point> points = PointFind.collectPoints();
    for(int i = 0; i < points.size(); i++){
      float [] rgb = colorCalc(points.get(i).colorID);
      stroke(rgb[0],rgb[1],rgb[2]);
      point(points.get(i).x+width/2, -points.get(i).y + height/2);
    }
    PointFind.points.clear();
    for(int i = 0; i < calculators.size(); i++){
      calculators.get(i).run();
    }
    frameCheck = frameRate;
    System.out.println(frameCheck);
  }

  private static void loadPreMade(){

    Function<? super Float, ? extends Float> calc1  = x->sin(x/50)*400;
    lines.add(calc1);

    Function<? super Float, ? extends Float> calc2  = x->tan(x/50)*400;
    lines.add(calc2);

    Function<? super Float, ? extends Float> calc3  = x->cos(x/50)*400;
    lines.add(calc3);

  //   Function<? super Float, ? extends Float> calc4  = x->asin(x/50)*400;
  //   lines.add(calc4);

  //   Function<? super Float, ? extends Float> calc5  = x->atan(x/50)*400;
  //   lines.add(calc5);

  //   Function<? super Float, ? extends Float> calc6  = x->acos(x/50)*400;
  //   lines.add(calc6);
  }
  
  private static void loadTxtFile(){
    File file = new File("mathematical lines.txt");
    try (Scanner input = new Scanner(file)) {
      while (input.hasNextLine()){
        lines.add(
          Generator.translate(input.nextLine())
        );
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static float[] colorCalc(int colorID){
    if(colorCodes[colorID][0] != -1){
      return new float[] {colorCodes[colorID][0],colorCodes[colorID][1],colorCodes[colorID][2]};
    }
    float value = (255*6) / lines.size() * colorID;
    
    float r = 255f;
    float g = 0f;
    float b = 0f;

    for(float i = 0f; i < value; i ++){
      if(r == 255 && g <255 && b == 0)
      g++;
      else if(r <= 255 && g == 255 && r != 0)
      r--;
      else if(g == 255 && r == 0 && b <255)
      b++;
      else if(g <= 255 && b == 255 && g != 0)
      g--;
      else 
      r++;
    }
    
    colorCodes[colorID][0] = r;
    colorCodes[colorID][1] = g;
    colorCodes[colorID][2] = b;

    System.out.println("new color generated: r:" + r + " g:" + g + " b: " + b);

    return (new float[] {r,g,b});
  }

}
