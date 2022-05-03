
import java.io.*;
import java.util.*;

public class RandomData {
    Map<String,Integer> map;
  public Map<String, Integer> readFile() throws FileNotFoundException {

     map = new HashMap<String,Integer>();
     for(int i = 0; i < 1000; ++i)
     {
        String s = choose(new File("src\\otherfiles\\file.txt"));
        if(!map.containsKey(s))
           map.put(s, 0);
        map.put(s, map.get(s) + 1);
     }

return map;

}

  public String choose(File f) throws FileNotFoundException
  {
     String result = null;
     Random rand = new Random();
     int n = 0;
     for(Scanner sc = new Scanner(f); sc.hasNext(); )
     {
        ++n;
        String line = sc.nextLine();
        if(rand.nextInt(n) == 0)
           result = line;         
     }

     return result;      
  }
}