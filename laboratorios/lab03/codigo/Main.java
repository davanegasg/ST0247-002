import java.util.ArrayList;
import javafx.util.Pair;
/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main
{

    public static void main(String args[]){
        ArrayList ola = new ArrayList();
        PuntoUno holita = new PuntoUno(10000);

        holita.addArc(10000,1,10);
        holita.addArc(10000,3,14);
        holita.addArc(10000,4,10);
        holita.addArc(1,10000,10);
        holita.addArc(1,2,7);
        holita.addArc(1,3,12);
        holita.addArc(1,4,15);
        holita.addArc(2,1,7);
        holita.addArc(2,3,20);
        holita.addArc(3,10000,14);
        holita.addArc(3,1,12);
        holita.addArc(3,2,20);
        holita.addArc(3,4,8);
        holita.addArc(4,10000,10);
        holita.addArc(4,1,15);
        holita.addArc(4,3,8);

        ola.add(2);
        Pair<Integer,ArrayList> pareja = holita.MejorCamino(2,4,ola);

        for(int i = 0 ; i<pareja.getValue().size() ;i++){
            System.out.println(pareja.getValue().get(i));
        }
        System.out.println("Distancia: "+pareja.getKey());
    }
}
