  

/**
 * Clase en la cual se implementan los metodos del Taller 6
 * 
 * @author Mauricio Toro, Andres Paez
 */
public class Taller6 {

	/**
	* Metodo que dado un entero n y un conjunto de denominciones de dinero
	* busque la manera optima de dar el cambio
	* @param n cantidad a devolver
	* @param denominaciones conjunto de denominaciones de dinero (monedas, billetes)
	* @return un conjunto de unidades por denominacion
	*/
	public static int[] cambioGreedy(int n, int[] denominaciones) {
		
	}

	/**
	* Metodo que recorre todo el grafo con la intencion de buscar un
	* camino que represente el menor costo pasando por todos los vertices exactamente
	* una vez y vuelva al nodo inicial
	* @param g grafo dado 
	* @return cual es el costo que tiene
	*/
	public static int recorrido(Digraph g) {
	
	}
	
	public static int PesoMinimo(ArrayList<Integer> sucesor, Digraph g, int v, boolean[] visitados){
        int pesomin = Integer.MAX_VALUE;
        int nodo = 0;
        for(int i = 0; i < sucesor.size(); i++){
            if(visitados[sucesor.get(i)] == false){
                if(pesomin > g.getWeight(v, sucesor.get(i))){
                    pesomin = g.getWeight(v, sucesor.get(i));
                    nodo = sucesores.get(i);
                }
            }
        }
        return nodo;

}
