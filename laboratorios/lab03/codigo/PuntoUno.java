import java.util.ArrayList;
import java.util.*;
import javafx.util.Pair;

public class PuntoUno{
    LinkedList<Pair<Integer,Integer>> lista[];
    int size;
    
    public PuntoUno(int size) {
        
        this.size = size;
        this.lista = new LinkedList[size];
    }
    
    public Pair<Integer,ArrayList> MejorCamino(int init, int destination, ArrayList camino){

        if(init==destination){
            ArrayList<Integer> copiaLista = (ArrayList<Integer>) camino.clone();
            return new Pair(distanciaCamino(camino), copiaLista);
        }

        ArrayList<Integer> sucesor = getSuccessors(init);
        int mejorDistancia = Integer.MAX_VALUE;
        ArrayList<Integer> mejor_Camino = new ArrayList<>();

        for(Integer a : sucesor){
            if(camino.contains(a)){
                continue;
            }

            camino.add(a);  
            Pair<Integer,ArrayList> aux = MejorCamino((int)a, destination, camino);
            camino.remove(a);

            if(aux.getKey() < mejorDistancia){
               mejorDistancia = aux.getKey();
               mejor_Camino = aux.getValue();
            }
        }
        return new Pair(mejorDistancia, mejor_Camino);

    }

    public int distanciaCamino(ArrayList camino){
        int distancia = 0;
        if( camino.size() == 0){
            return 0;}
        for(int i = 0 ; i<camino.size()-1 ;i++){
            distancia += getWeight((int)camino.get(i), (int)camino.get(i+1));
        }
        return distancia;
    }

    public int getWeight(int init, int destino) {
        LinkedList<Pair<Integer,Integer>> listica = this.lista[init];
        int weigth = 0;

        for(Pair p : listica)
            if((int)p.getKey() == destino){
                weigth = (int)p.getValue();

            }
        return weigth;
    }

    public ArrayList<Integer> getSuccessors(int vertex) {
        ArrayList<Integer> sucesores = null;
        LinkedList<Pair<Integer, Integer>> filaSucesores = this.lista[vertex];

        if (filaSucesores != null){
            for(Pair p: filaSucesores){
                if(sucesores == null){
                    sucesores = new ArrayList<Integer>();
                }
                sucesores.add((Integer)p.getKey());  
            }            
        }        
        return sucesores;
    }
    
     public void addArc(int init, int destino, int peso) {

        if(lista[init]==null){
            lista[init] = new LinkedList();
        } 
        lista[init].add(new Pair(destino,peso));

    }
    }
