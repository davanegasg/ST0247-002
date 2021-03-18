public static ArrayList lowCostPathDFS(Graph g, int source, int destination){
        boolean [] checked = new boolean[g.size()+1];
        ArrayList<ArrayList<Integer>> ruta = new ArrayList();
        return lowCostPathAux(g, source, destination, checked, ruta);
    }

    private static ArrayList lowCostPathAux(Graph g, int source, int destination, boolean[] checked, ArrayList ruta){

                        ArrayList<Integer> next = g.getSuccessors(source);
        next.remove((Integer)source);
        
        checked[source] = true;
        boolean answer = false;

        
        if(destination == source){
            answer = true;
        }
        

        for(int neighbor: next){
            if(checked[neighbor] == false){
                Pair<ArrayList<Integer>, Integer> rutas = new Pair<source,destination>();
                ruta.set(1, ruta.get(1) + g.getWeight(source, neighbor));            
                rutas[0].append(source);
                answer =  lowCostPathAux(g, neighbor, destination, checked, ruta);               
            }
            if (answer == true){
              break;
            }
        }
        return answer;
  }
