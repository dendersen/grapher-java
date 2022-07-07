package dk.mtdm.translator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Finder {
  public Boolean[] Search(String[] path, Pattern pattern){
    Boolean[] output = new Boolean[path.length];
    for (int i = 0; i < path.length; i++) {
      output[i] = pattern.matcher(path[i]).matches();
    }
    return output;
  }

  public Boolean[] Search (String[] path, String regex){
    return Search(path, Pattern.compile(regex));
  }

  public float numerator (String[] path, int location){
    return numerator(path[location]);
  }

  public float numerator(String number){
    try (Scanner scan = new Scanner(number)) {
      return scan.nextFloat();
    }
  }
}
