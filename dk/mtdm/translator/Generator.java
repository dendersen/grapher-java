package dk.mtdm.translator;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

import dk.mtdm.FunktionRunner;
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
    
    Boolean[] ParanteseStart = Search(path, "(");
    Boolean[] ParanteseEnd = Search(path, ")");

    for(int i = 1; i < path.length-1; i += 2){
      if(SimpleOperation.matcher(path[i]).matches()){
        switch(path[i]){
        case "*": {
            float numb = numerator(path,i+1);
            Function <QuadStore,QuadStore> after = xt -> xt.setY(xt.y*numb);
            line = line.andThen(after);
          } break;
          
          case "/":{
            float numb = numerator(path, i+1);
            Function <QuadStore,QuadStore> after = xt -> xt.setY(xt.y/numb);
            line = line.andThen(after);
          }break;
          
          case "+":{
            Generator math = new Generator(path); //TODO fix
            Function<QuadStore,QuadStore> temp = math.RawResult();
            Function<QuadStore,QuadStore> moveToEkstra = xt -> xt.setExtra(xt.y);
            line = line.andThen(moveToEkstra);
            float numb = numerator(path,i+1);
            Function<QuadStore,QuadStore> prepY = xt -> xt.setY(numb);
            line = line.andThen(prepY);
            line = line.andThen(temp);
            Function<QuadStore,QuadStore> addition = xt -> xt.setY(xt.extra + xt.y);
            line = line.andThen(addition);
          }break;
          case "-":{
            Generator math = new Generator(path); //TODO fix
            Function<QuadStore,QuadStore> temp = math.RawResult();
            Function<QuadStore,QuadStore> moveToEkstra = xt -> xt.setExtra(xt.y);
            line = line.andThen(moveToEkstra);
            float numb = numerator(path,i+1);
            Function<QuadStore,QuadStore> prepY = xt -> xt.setY(numb);
            line = line.andThen(prepY);
            line = line.andThen(temp);
            Function<QuadStore,QuadStore> substraction = xt -> xt.setY(xt.extra - xt.y);
            line = line.andThen(substraction);
          }break;
        }
        
      }
      
    }
    
    return line;
  }

  public Function<Float,Float> Result(){
    Function<QuadStore,QuadStore> output = RawResult();
    Function<Float,QuadStore> translate1 = xt -> new QuadStore(xt, 0 , 0, numerator(path[0]));
    translate1 = translate1.andThen(output);
    Function<QuadStore, Float> translate2 = xt -> xt.y;
    return translate1.andThen(translate2);
  }

}

