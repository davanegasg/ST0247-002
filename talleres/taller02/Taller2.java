
import java.util.*;
/**
 * Write a description of class Permutation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Taller2
{
    public static LinkedList<String> combinations(String s) {
        LinkedList<String> respuesta = new LinkedList<String>();
        combinations("", s, respuesta);
        System.out.print(respuesta);
        return respuesta;
    }

    private static void combinations(String loQueLlevo, String loQueMeFalta, LinkedList<String> list) {
        if(loQueMeFalta.equals("")){
            list.add(loQueLlevo);

        } else{

            combinations(loQueLlevo + loQueMeFalta.charAt(0), loQueMeFalta.substring(0+1,loQueMeFalta.length()), list);
            combinations(loQueLlevo , loQueMeFalta.substring(0+1,loQueMeFalta.length()), list);

        }          

    }
    
}
