package dk.mtdm;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class FunktionRunner implements Runnable{
  
  private String threadName;
  private Thread t;
  
  private float pixelEnd;
  private float pixelStart;
  private float density;
  private Function<? super Float, ? extends Float> densityRamp;

  private int colorID;
  
  private Function<? super Float, ? extends Float>  calc;
  
  public void threadSetup(float pixelStart, float pixelEnd, Function<? super Float, ? extends Float> functionToCalculate, float startingDensity, Function<? super Float, ? extends Float> densityRamp, int colorID){
    
    this.pixelEnd = pixelEnd;
    this.pixelStart = pixelStart;
    this.threadName = "processing area: " + pixelStart + ";" + pixelEnd + " colorID: " + colorID;
    System.out.println("creating thread: " + this.threadName);
    
    this.colorID = colorID;

    this.calc = functionToCalculate;
    
    this.density = startingDensity;
    this.densityRamp = densityRamp;
  }
  public void start() {
    System.out.println("starting thread: " + threadName);
    
    if(t == null){
      t = new Thread (this, threadName);
    }
  }
  
  public void run(){
    // System.out.println("running: " + threadName);
    
    for(int i = 0; i < density; i++){
      float place = movement();
      float y = result(place);
      PointFind.insertPoint(new Point(place, y,colorID));
    }
    
    density = densityRamp.apply(density);
  }
  
  public Float result(Float x){
    Float fin = calc.apply(x);
    return (fin);
  }

  private float movement(){
    return ( ThreadLocalRandom.current().nextFloat() * (pixelEnd - pixelStart) + pixelStart);
  }
}
