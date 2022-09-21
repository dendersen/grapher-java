package dk.mtdm.translator;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class GeneratorV2 {
  
  public static Expression gen(String inputLine){
    return new ExpressionBuilder(inputLine).variable("x").build();
  }
  
}