import java.util.ArrayList;

/**
 * Implementacion de un grafo dirigido usando matrices de adyacencia
 *
 * @author Mauricio Toro, Jacobo Rave Londoño, Diego Alejandro Vanegas
 */
public class DigraphAM extends Digraph {
    int size;
    int matriz[][];
    /**
     * Constructor para el grafo dirigido
     * @param vertices el numero de vertices que tendra el grafo dirigido
     *
     */
    public DigraphAM(int size) {
        super(size);
        this.size = size;
        matriz= new int[size][size];
        for(int i=0; i < size ; i++){
            for(int j=0; j < size; j++){
                matriz[i][j] = 0;
            }            
        }
    }

    /**
     * Metodo para añadir un arco nuevo, donde se representa cada nodo con un entero
     * y se le asigna un peso a la longitud entre un nodo fuente y uno destino  
     * @param source desde donde se hara el arco
     * @param destination hacia donde va el arco
     * @param weight el peso de la longitud entre source y destination
     */
    public void addArc(int source, int destination, int weight) {
        matriz[source][destination] = weight;

    }

    /**
     * Metodo para obtener una lista de hijos desde un nodo, es decir todos los nodos
     * asociados al nodo pasado como argumento
     * @param vertex nodo al cual se le busca los asociados o hijos
     * @return todos los asociados o hijos del nodo vertex, listados en una ArrayList
     * Para más información de las clases:
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html"> Ver documentacion ArrayList </a>
     */
    public ArrayList<Integer> getSuccessors(int vertex) {

        ArrayList<Integer> Succesors = new ArrayList<>();
        ArrayList<Integer> SuccesorsD = new ArrayList<>();

        for(int i = 0; i<this.size-1; i++){
            //int a = matriz[vertex][i];
            Succesors.add(matriz[vertex][i]);
            if(Succesors.get(i)!=0){

                SuccesorsD.add(i);
            }

        }
        return SuccesorsD;

    }

    public void imprimir(){
        for (int x=0; x < matriz.length; x++) {
            System.out.print("|");
            for (int y=0; y < matriz[x].length; y++) {
                System.out.print (matriz[x][y]);
                if (y!=matriz[x].length-1) System.out.print("\t");
            }
            System.out.println("|");
        }
    }

    /**
     * Metodo para obtener el peso o longitud entre dos nodos
     * 
     * @param source desde donde inicia el arco
     * @param destination  donde termina el arco
     * @return un entero con dicho peso
     */ 
    public int getWeight(int source, int destination) {
        return matriz[source][destination]; 
    }

}
