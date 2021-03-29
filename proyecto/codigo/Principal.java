import java.util.*;
import java.io.*;
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
    static double nCarros = 0;
    static List<LinkedList<Integer>> rutasCarros = new ArrayList<LinkedList<Integer>>();
    static List<LinkedList<Double>> tCarros = new ArrayList<LinkedList<Double>>();
    static double tiempo = 0;
    static String name;
    public static void main(String[] args) throws IOException, InterruptedException{
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Scanner scan  = new Scanner(System.in); 
        System.out.println("Por favor, ingrese un archivo con las coordenadas");
        String archivo = scan.nextLine();
        Lector lectorcito = new Lector();
        ArrayList<Pair<Double, Double>> pNodo[];
        leerDatos(lectorcito, archivo+".txt");
        ArrayList<Coordinates> datos = lectorcito.getC();
        cVehicle carrito = lectorcito.v;
        grafito = new GrafoAM(datos.size());
        visitados = new boolean[grafito.size];
        visitados1 = new boolean[grafito.size];
        visitados[0] = true;
        visitados1[0] = true;
        ruta.add(0);
        generarGrafoDT(datos, carrito);
        calcularRuta(datos, carrito);
        tiempo = calcularTiempo(datos, carrito, ruta);
        generarCarros(carrito.m);
        asignarRutas(ruta, ItoD(nCarros));
        boolean salir = false;
        while(!salir){
            System.out.println("-----------¿Qué quieres hacer?-----------");
            System.out.println("1) Mostrar la ruta General");
            System.out.println("2) Imprimir el tiempo para esta ruta");
            System.out.println("3) Imprimir el número de carros que usaremos para hacer esta ruta");
            System.out.println("4) Mostrar las rutas de cada carro individualmente");
            System.out.println("5) Mostrar la Matriz Distancia-Tiempo");
            System.out.println("6) Guardar coordenadas");
            System.out.println("0) Salir");
            try{
                System.out.println("----------Selecciona una opción----------");
                int opcion = scan.nextInt();
                switch(opcion){
                    case 1:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    imprimirRutas(ruta);
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 2:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    imprimirTiempo(tiempo);
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 3:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    imprimirCarros((ItoD(nCarros)));
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 4:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    imprimirRG(rutasCarros);
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 5:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    grafito.imprimir();
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 6:
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    guardarArchivoCSV(datos);
                    System.out.println("");
                    System.out.println("");
                    break;
                    case 0:
                    salir = true;
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    System.out.println("Muchas gracias------------------------");
                    System.out.println("---------------por usar---------------");
                    System.out.println("------------------------el programa :D");
                    System.out.println("-----------------Adios-----------------");
                    break;
                    default: 
                    System.out.println("Las opciones son entre 0 y 5");
                }
            }catch(InputMismatchException e){
                System.out.println("Debes introducir un número");
                scan.next();
            }
        }
        //
        //imprimirTiempo();
        //imprimirCarros();
        //imprimirRG();

        //grafito.imprimir();
    }

    public static void guardarArchivoCSV(ArrayList<Coordinates> datos)throws IOException{
        Scanner scan = new Scanner(System.in);
        File archivo=null;
        System.out.println("Inserte el nombre con el que guardara el archivo: ");
        String name=scan.next();
        int n = 0;
        try{
            archivo = new File(name+".csv");
            PrintStream guardado = new PrintStream(archivo);
            for(Coordinates v: datos){
                guardado.print(v.getX() +  ";" + v.getY()+ ";"+ v.getTnode());
                guardado.println();
                n++;
            }
            System.out.println(n + " Coordenadas guardadas exitosamente.");
            guardado.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    } 

    public static int ItoD (double n){
        return Integer.parseInt(String.valueOf(n).replace(".0", ""));
    }

    public static Lector leerDatos(Lector lectorcito, String rutaA){
        lectorcito.leer("D:\\Escritorio\\Escritorio\\Universidad EAFIT\\Estructura de Datos\\Proyecto\\" + rutaA);
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
        if(tiempo>24){
            double dias = tiempo/24;
            if(dias>30.28767){
                double meses = dias/30.28767;
                System.out.println("El tiempo estimado para esta ruta es: "+meses + " Meses");
            } else {
                System.out.println("El tiempo estimado para esta ruta es: "+dias + " Días");
            }
        } else {
            System.out.println("El tiempo estimado para esta ruta es: "+tiempo + " Horas");
        }
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

    public static int generarCarros(int numeroClientes){
        if(numeroClientes%2 == 0){
            if(numeroClientes<4){nCarros = 1;} 
            else { nCarros = Math.ceil(numeroClientes/4);}
        }else {nCarros = Math.ceil(numeroClientes/4);}
        return ItoD(nCarros);
    }

    public static void imprimirCarros(int n){
        System.out.println("El número de carros que usaremos para las entregas es: "+n);
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

    public static double calcularTC(int source, int destination){
        double t = grafito.getTime(source, destination)+0.5;
        return t;
    }

    public static double Devuelvase(int source){
        double t = grafito.getTime(source, 0);
        return t;
    }

    public static double Comience(int destination){
        double t = grafito.getTime(0, destination)+0.5;
        return t;
    }

    public static List<LinkedList<Integer>> asignarRutas(ArrayList<Integer> rutas, int nCarros){
        int max = rutas.size()-1;
        int maxx = max+1;
        int k = 0;
        int j = 1; //Ruta
        double demora = 0;
        for(int i = 0; i<nCarros; i++){
            rutasCarros.add(new LinkedList<Integer>());
            tCarros.add(new LinkedList<Double>());
            demora = Comience(j);
            k = 0;
            while(k<4){
                if(max<4){
                    if(j == maxx){
                        break;
                    }
                    if(k<1){
                        demora += calcularTC(j, j+1);
                    } else {
                        demora += Devuelvase(j);
                    }
                    if(demora<10){
                        rutasCarros.get(i).add(rutas.get(j));
                    }
                    j++;
                    k++;
                } else if(max%2 == 0){
                    if(j == maxx){
                        break;
                    }
                    if(k<3){
                        demora+=calcularTC(j, j+1);
                    } else {
                        demora+= Devuelvase(j);
                    }
                    if(demora<10){
                        rutasCarros.get(i).add(rutas.get(j));}
                    k++;
                    j++;
                }

            }
            tCarros.get(i).add(demora);
            demora = 0;
        }
        return rutasCarros;
    }

    public static void imprimirRG(List<LinkedList<Integer>> rutasCarros){
        String buffer = "{Deposito -> "; 
        int nR = ruta.size()-1;
        for(int i = 0; i<nCarros;i++){
            if(nR <4){
                for(int j = 0; j<nR; j++){
                    buffer = buffer +rutasCarros.get(i).get(j) + " -> ";
                }
                int x = i+1;
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarros.get(i) + " Horas" ;
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> ";  
            } else {
                for(int j = 0; j<4; j++){
                    buffer = buffer + rutasCarros.get(i).get(j) + " -> ";
                }
                int x = i+1;
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarros.get(i) + " Horas";
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> "; }
        }
    }
}
