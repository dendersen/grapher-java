package dk.mtdm.translator;

import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;

import dk.mtdm.QuadStore;

/*TODO
 * what does "action do"
 * save actions
 * save numbers
 * return funkrion
*/
public class Generator extends Finder{
  private String[] path;
  private Function<QuadStore,QuadStore> output;
  
  private final Pattern SimpleOperation = Pattern.compile("[+|-|*|/+]");
  private final Pattern complexOperations = Pattern.compile("[x]");



  public Generator(String path, String regex) {
    this.path = path.split(regex);
  }
  public Generator(String path) {
    this.path = path.split(" ");
  }
  public Generator(String[] path) {
    this.path = path;
  }

  private Function<QuadStore,QuadStore> RawResult(){
    Function<QuadStore,QuadStore> line = xt -> xt;
    
    Boolean[] ParanteseStart = Search(path, "[(]");
    Boolean[] ParanteseEnd = Search(path, "[)]");

    for(int i = 1; i < path.length-1; i += 2){
      if(SimpleOperation.matcher(path[i]).matches()){
        switch(path[i]){
          case "*": {
            if(!complexOperations.matcher(path[i+1]).matches()){
            Float numb = numerator(path,i+1);

            Function <QuadStore,QuadStore> after = xt -> xt.setY(xt.y*numb);
            line = line.andThen(after);
            break;
            }
            
            if(Pattern.compile("[x]").matcher(path[i+1]).matches()){
              Function <QuadStore,QuadStore> after = xt -> xt.setY(xt.y*xt.x);
              line = line.andThen(after);
            }
            System.out.println("complex operations have yet too be added");//TODO add cos sin and so on
            
          } break;
          
          case "/":{
            float numb = numerator(path, i+1);
            Function <QuadStore,QuadStore> after = xt -> xt.setY(xt.y/numb);
            line = line.andThen(after);
          }break;
          
          case "+":{
            line = PrepMultiCalculation(line, i);
            Function<QuadStore,QuadStore> addition = xt -> xt.setY(xt.extra.remove(xt.extra.size()-1) + xt.y);
            line = line.andThen(addition);
            return line;
          }
          case "-":{
            line = PrepMultiCalculation(line, i);
            Function<QuadStore,QuadStore> addition = xt -> xt.setY(xt.extra.remove(xt.extra.size()-1) - xt.y);
            line = line.andThen(addition);
            return line;
          }
        }
        
      }
      
    }
    
    return line;
  }
  /**
   * 
   * @param line just the variable Line
   * @param i current location in path
   * @return the new value of line
   */
  private Function<QuadStore, QuadStore> PrepMultiCalculation(Function<QuadStore, QuadStore> line, int i) {
    Function<QuadStore,QuadStore> moveToEkstra = xt -> xt.setExtra(xt.y);
    line = line.andThen(moveToEkstra);
    float numb = numerator(path,i+1);
    
    // Slice array
    path = Arrays.copyOfRange(path, i+1, path.length);
    Generator math = new Generator(path);

    Function<QuadStore,QuadStore> temp = math.RawResult();
    Function<QuadStore,QuadStore> prepY = xt -> xt.setY(numb);
    line = line.andThen(prepY);
    line = line.andThen(temp);
    return line;
  }

  public Function<Float,Float> Result(){
    Function<QuadStore,QuadStore> output = RawResult();
    Float num = numerator(path[0]);
    Function<Float,QuadStore> translate1 = xt -> new QuadStore(xt, 0 , 0, num);
    translate1 = translate1.andThen(output);
    Function<QuadStore, Float> translate2 = xt -> xt.y;
    return translate1.andThen(translate2);
  }

}

