package dk.mtdm;

import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Generator {
  
  static final Pattern activeSimpleOperations = Pattern.compile("[/|*|-|+]");
  static final Pattern activeComplexOperations = Pattern.compile("Sin|sin|Cos|cos|tan|Tan|Sqrt|sqrt|^");

  public static Function<? super Float, ? extends Float> translate(String inputMath){
    Function<? super Float, ? extends Float> output = x -> 1f;

    int number = 0;

    Scanner input = new Scanner(inputMath);
    output = nextFullOperation(input, number, 0);
    number++;

    return output;
  }

  private static Function<? super Float, ? extends Float> nextFullOperation(Scanner input, int number, int subnumber){
    
    Function<? super Float, ? extends Float> output;
    Function<? super Float, ? extends QuadStore> translator;
    Function<? super QuadStore, ? extends Float> reverter;
    
    translator = xt -> new QuadStore().setX(xt);
    
    reverter = xt -> xt.y;
    while(input.hasNext()){
      Function<? super QuadStore, ? extends QuadStore> temp = nextSimpleOperation(input);
      translator = translator.andThen(temp);
    }
    Function<? super Float, ? extends QuadStore> home = translator;
    output = xt -> reverter.apply(home.apply(xt));
    return output;
  }
  
  private static Function<? super QuadStore, ? extends QuadStore> nextSimpleOperation(Scanner input) {
    Function<? super QuadStore, ? extends QuadStore> temp;
    switch (Reader(input)) {
    case "/":
      temp = xt -> xt;
    break;

    case "*":
      temp = xt -> xt;
    break;

    case "-":
      temp = xt -> xt;
    break;

    case "+":
      temp = xt -> xt;
    break;

    case "Sin":
      temp = xt -> xt;
    break;

    case "sin":
      temp = xt -> xt;
    break;

    case "Cos":
      temp = xt -> xt;
    break;

    case "cos":
      temp = xt -> xt;
    break;

    case "tan":
      temp = xt -> xt;
    break;

    case "Tan":
      temp = xt -> xt;
    break;

    case "Sqrt":
      temp = xt -> xt;
    break;

    case "sqrt":
      temp = xt -> xt;
    break;

    case "^":
      temp = xt -> xt;
    break;
  }

    temp = xt -> xt;
    return temp;
  }

  static private String Reader(Scanner input){
    if(input.hasNext()){

    }
    return "";
  }
}

