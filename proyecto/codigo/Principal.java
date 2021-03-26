import java.util.*;
/**
 * Write a description of class Principal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Principal
{
    static GrafoAM grafito;
    static boolean[] visitados;
    static boolean[] visitados1;
    static ArrayList<Integer> ruta = new ArrayList<>();
    
    public static void main(String[] args){
        Lector lectorcito = new Lector();
        ArrayList<Pair<Double, Double>> pNodo[];
        leerDatos(lectorcito);
        ArrayList<Coordinates> datos = lectorcito.getC();
        cVehicle carrito = lectorcito.v;
        grafito = new GrafoAM(datos.size());
        visitados = new boolean[grafito.size];
        visitados1 = new boolean[grafito.size];
        visitados[0] = true;
        visitados1[0] = true;
        ruta.add(0);
        generarGrafoDT(datos, carrito);
        imprimirRutas(calcularRuta(datos, carrito));
        imprimirTiempo(calcularTiempo(datos, carrito, ruta));
        grafito.imprimir();
    }

    public static Lector leerDatos(Lector lectorcito){
        lectorcito.leer("D:\\Escritorio\\Escritorio\\Universidad EAFIT\\Estructura de Datos\\Proyecto\\dummy.txt");
        return lectorcito;
    }

    public static GrafoAM generarGrafoDT(ArrayList<Coordinates> datos, cVehicle carrito){
        for(Coordinates o: datos){
            for(Coordinates m: datos){
                if(o == m){
                    continue;
                } else {
                    double distancia = Math.hypot((o.getX()-m.getX()),(o.getY()-m.getY()));
                    double time = distancia/carrito.getSpeed();
                    grafito.addArc(o.getIdNode(),m.getIdNode(), distancia, time);
                }
            }
        } 
        return grafito;
    }

    public static ArrayList<Integer> calcularRuta(ArrayList<Coordinates> datos, cVehicle carrito){
        int pivote = datos.get(0).getIdNode();
        for(int i = 0; i<carrito.m;i++){
            int res = bestClient(pivote, datos);
            ruta.add(res);
            pivote = res;
        }   
        return ruta;
    }

    public static void imprimirTiempo(double tiempo){
        System.out.println("El tiempo estimado para esta ruta es: "+tiempo + " Horas");
    }

    public static double calcularTiempo(ArrayList<Coordinates> datos, cVehicle carrito, ArrayList<Integer> ruta){
        int pivote1= datos.get(0).getIdNode();
        double tiem = 0;
        double timp = 0;
        double tiempo0 = 0;
        for(int i = 0; i<carrito.m;i++){
            tiem = generarTiempoTotal(pivote1, datos);
            pivote1 = ruta.get(i+1);
            tiempo0 = grafito.getTime(pivote1, 0);
            timp+=tiem;
        }         
        double tiempoTotal= timp+tiempo0;
        return tiempoTotal;
    }

    public static void imprimirRutas(ArrayList<Integer> rutas){
        String buffer = "{"; 
        for(Integer id: rutas){
            buffer = buffer + id + " -> ";
        }
        buffer = buffer + "0" + "}";
        System.out.println("La mejor ruta es: "+buffer);
    }

    public static double generarTiempoTotal(int idNodoInicial, ArrayList<Coordinates> datos){
        double tiempoTotal = 0;
        int auxId = 0;
        for(Coordinates o: datos){
            if(o.getIdNode() == idNodoInicial){
                continue;
            } else if((!visitados1[o.getIdNode()]) && (o.getTnode().equals("c"))){
                tiempoTotal += grafito.getTime(idNodoInicial, o.getIdNode()) + 0.5;
                auxId = o.getIdNode();
                break;
            }
        }
        visitados1[auxId]=true;
        return tiempoTotal;
    }

    public static void generarCarros(int numeroClietes){

    }

    public static int bestClient(int idNodoInicial, ArrayList<Coordinates> datos){
        double auxTime = Integer.MAX_VALUE;
        int auxId = 0;
        for(Coordinates o: datos){
            if(idNodoInicial == o.getIdNode()){
                continue;
            }else if(!visitados[o.getIdNode()] && (grafito.getTime(idNodoInicial, o.getIdNode())<auxTime) && o.getTnode().equals("c")){
                auxTime = grafito.getTime(idNodoInicial, o.getIdNode());
                auxId = o.getIdNode();

            }
        }        
        visitados[auxId]=true;
        return auxId;
    }
}
