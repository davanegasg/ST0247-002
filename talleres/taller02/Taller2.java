
import java.util.*;
/**
 * Write a description of class Permutation here.
 * 
 * @author (Diego Alejandro Vanegas, Jacobo Rave Londoño) 
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

public static LinkedList<String> permutations(String s) {
        LinkedList<String> respuesta = new LinkedList<String>();
        permutations("", s, respuesta);
        //System.out.print(respuesta);
        return respuesta;
    }

    private static void permutations(String loQueLlevo, String loQueMeFalta, LinkedList<String> list) {
        
        if(loQueMeFalta.equals("")){
            list.add(loQueLlevo);

        } else{
            for(int i = 0 ; i<loQueMeFalta.length(); i++)
                permutations(loQueLlevo + loQueMeFalta.charAt(i), loQueMeFalta.replaceFirst(String.valueOf(loQueMeFalta.charAt(i)), ""), list);

        }          
    }
