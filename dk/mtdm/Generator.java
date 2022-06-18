package dk.mtdm;

import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Generator {
  
  static final Pattern activeOperations = Pattern.compile("[/|*|-|+|Sin|sin|Cos|cos|tan|Tan|Sqrt|sqrt|^]");

  public static Function<? super Float, ? extends Float> translate(String inputMath){
    Function<? super Float, ? extends Float> output = x -> 1f;

    int number = 0;

    Scanner input = new Scanner(inputMath);
    output = nextOpertion(input, number, 0);
    number++;

    return output;
  }

  private static Function<? super Float, ? extends Float> nextOpertion(Scanner input, int number, int subnumber){
    
    Function<? super Float, ? extends Float> output;
    
    if(input.hasNextFloat()){
      Float temp = input.nextFloat();
      output = x -> temp;
        while(input.hasNext()){
          String Temp = input.next();
          switch(Temp){
            case "/":
              if(input.hasNextFloat()){
                Float temper = input.nextFloat();
                Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
                output = output.andThen(after);
              }
            break;
            case "*":
              if(input.hasNextFloat()){
                Float temper = input.nextFloat();
                Function <? super Float, ? extends Float> after = ttx -> ttx * temper; 
                output = output.andThen(after);
              }
            break;
            case "-":
              if(input.hasNextFloat()){
                Float temper = input.nextFloat();
                Function <? super Float, ? extends Float> after = ttx -> ttx - temper; 
                after = after.andThen(nextOpertion(input, number, subnumber+1));
                output = output.andThen(after);
              }
            break;
            case "+":
              if(input.hasNextFloat()){
                Float temper = input.nextFloat();
                Function <? super Float, ? extends Float> after = ttx -> ttx + temper; 
                output = output.andThen(after);
              }
            break;
            case "Sin":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx sin temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "sin":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "Cos":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "cos":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "tan":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "Tan":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "Sqrt":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "sqrt":
              // if(input.hasNextFloat()){
              //   Float temper = input.nextFloat();
              //   Function <? super Float, ? extends Float> after = ttx -> ttx / temper; 
              //   output.andThen(after);
              // }
              System.out.println(Temp + "has yet to ble implimented and will be skiped");
            break;
            case "^":
              if(input.hasNextFloat()){
                Float temper = input.nextFloat();
                Function <? super Float, ? extends Float> after = ttx -> (float) Math.pow(ttx, temper); 
                output = output.andThen(after);
              }
            break;
          }
        }
    }else{
      System.out.println("an operation error has occured, funktion " + number + "." + subnumber + " did not start with a float");
      output = x -> -124f;
    }

    return output;
  }
}

