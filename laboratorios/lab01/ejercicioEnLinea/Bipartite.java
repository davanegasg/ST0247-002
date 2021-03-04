import java.util.*;
import java.lang.*;
import java.io.*;
 
class Bipartite
{
   
    public static String descomponerGarras(Graph g){
        int [] colored = new int[g.size()+1];
        colored[1] = 1;
        
        return isBipartite(g, 1, colored, 2) ? "YES" : "NO";
    }
    
    boolean isBipartite(Graph g, int source, int[] colored, int currentColor)
    {
        ArrayList<Integer> q = g.getSuccessors(source);
        
         if(q.size() == 0){
            return true;
        }
        
        Queue<Integer> queue = new LinkedList<Integer>();
        boolean confirmar = true;
        
        for(int neighbor: q){
            if(colored[neighbor] == 0){
                queue.add(neighbor);
                colored[neighbor] = currentColor;
            }
            else if(colored[neighbor] != currentColor){
                return false;
            }
        }
        currentColor = currentColor == 1 ? 2:1;
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue
            int u = q.poll();
 
            confirmar = confirmar && isBipartite(g, u, colored, currentColor);
            // Find all non-colored adjacent vertices
            if(!confirmar){
                break;
            }
        }
        // If we reach here, then all adjacent vertices can
        // be colored with alternate color
        return confirmar;
    }
 
    // Driver program to test above function
    public static void main(){        
        ArrayList<Graph> lg = Lector.leerDataset();
        for(Graph g : lg){
            System.out.println(Bipartite(g));
        }
    }
}
