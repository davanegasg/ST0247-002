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
    static int contador = 0;
    static double nCarros = 0;
    static List<List<List<Integer>>> resultss = new ArrayList<List<List<Integer>>>();
    static List<LinkedList<Integer>> rutasCarros = new ArrayList<LinkedList<Integer>>();
    static List<LinkedList<Integer>> rutasCarrosP = new ArrayList<LinkedList<Integer>>();
    static List<LinkedList<Integer>> rutasBateria = new ArrayList<LinkedList<Integer>>();
    static List<LinkedList<Double>> tCarros = new ArrayList<LinkedList<Double>>();
    static List<LinkedList<Double>> dCarros = new ArrayList<LinkedList<Double>>();
    static List<LinkedList<Double>> tCarrosP = new ArrayList<LinkedList<Double>>();
    static List<LinkedList<Double>> dCarrosP = new ArrayList<LinkedList<Double>>();
    static List<LinkedList<Double>> tCarrosB = new ArrayList<LinkedList<Double>>();
    static List<LinkedList<Double>> dCarrosB = new ArrayList<LinkedList<Double>>();
    static double tiempo = 0;
    static double distancia = 0;
    static String name;
    static double bateria;
    static final int r = 125;
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
        bateria = carrito.getQ();
        grafito = new GrafoAM(datos.size());
        visitados = new boolean[grafito.size];
        visitados1 = new boolean[grafito.size];
        visitados[0] = true;
        visitados1[0] = true;
        ruta.add(0);
        generarGrafoDT(datos, carrito);
        calcularRuta(datos, carrito);
        tiempo = calcularTiempo(datos, carrito, ruta);
        distancia = calcularDistancia(datos, carrito, ruta);
        generarCarros(carrito.m);
        asignarRutas(ruta, ItoD(nCarros), datos);
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
                    imprimirDistancia(distancia);
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
                    // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    // imprimirRG(rutasCarros);
                    // System.out.println("");
                    // System.out.println("");
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    permutaciones(rutasCarros, datos);
                    asignarCargaBateria(rutasCarrosP, datos);
                    imprimirRGB(rutasBateria);
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
                    // case 7: //Caso de prueba
                    // new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    // permutaciones(rutasCarros, datos);
                    // asignarCargaBateria(rutasCarrosP, datos);
                    // imprimirRGB(rutasBateria);
                    // System.out.println("");
                    // System.out.println("");
                    // break;
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

    public static void imprimirDistancia(double distancia){
        System.out.println("La distancia estimada para esta ruta es: "+distancia + " Km");
    }

    public static double calcularDistancia(ArrayList<Coordinates> datos, cVehicle carrito, ArrayList<Integer> ruta){
        int pivote1= datos.get(0).getIdNode();
        double dist = 0;
        double distan = 0;
        double distancia = 0;
        for(int i = 0; i<carrito.m;i++){
            dist = generarDistaciaTotal(pivote1, datos);
            pivote1 = ruta.get(i+1);
            distancia = grafito.getDistace(pivote1, 0);
            distan+=dist;
        }         
        double distanciaTotal= distan+distancia;
        return distanciaTotal;
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

    public static double generarDistaciaTotal(int idNodoInicial, ArrayList<Coordinates> datos){
        double distanciaTotal = 0;
        int auxId = 0;
        for(Coordinates o: datos){
            if(o.getIdNode() == idNodoInicial){
                continue;
            } else if((!visitados1[o.getIdNode()]) && (o.getTnode().equals("c"))){
                distanciaTotal += grafito.getDistace(idNodoInicial, o.getIdNode());
                auxId = o.getIdNode();
                break;
            }
        }
        visitados1[auxId]=true;
        return distanciaTotal;
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

    public static int bestStation(int idNodoInicial, ArrayList<Coordinates> datos, int tipoSolicitud){
        double auxTime1 = Integer.MAX_VALUE;
        int auxT = 0;
        int auxID = 0;
        int respuesta = 0;
        for(Coordinates o: datos){
            if(idNodoInicial == o.getIdNode()){
                continue;
            }else if((grafito.getTime(idNodoInicial, o.getIdNode())<auxTime1) && o.getTnode().equals("s") && (o.getTstation() == 0 
                || o.getTstation() == 1 || o.getTstation() == 2 )){
                auxTime1 = grafito.getTime(idNodoInicial, o.getIdNode());
                auxT = o.getTstation();
                auxID = o.getIdNode();
            }
        }    
        if(tipoSolicitud==0){
            respuesta = auxID;
        } else if (tipoSolicitud == 1){
            respuesta = auxT; 
        }
        return respuesta;
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

    public static double calcularDC(int source, int destination){
        double t = grafito.getDistace(source, destination);
        return t;
    }

    public static double DevuelvaseD(int source){
        double t = grafito.getDistace(source, 0);
        return t;
    }

    public static double ComienceD(int destination){
        double t = grafito.getDistace(0, destination);
        return t;
    }

    public static List<LinkedList<Integer>> asignarRutas(ArrayList<Integer> rutas, int nCarros, ArrayList<Coordinates> datos){
        int max = rutas.size()-1;
        int maxx = max+1;
        int k = 0;
        int j = 1; //Ruta
        int sCarga = 0;
        double demora = 0;
        double distancia = 0;
        for(int i = 0; i<nCarros; i++){
            rutasCarros.add(new LinkedList<Integer>());
            tCarros.add(new LinkedList<Double>());
            dCarros.add(new LinkedList<Double>());
            demora = Comience(rutas.get(j));
            distancia = ComienceD(rutas.get(j));
            k = 0;
            while(k<4){
                if(max<4){
                    if(j == maxx){
                        break;
                    }
                    if(k<1){
                        demora += calcularTC(rutas.get(j), rutas.get(j+1));
                        distancia+=calcularDC(rutas.get(j),rutas.get(j+1));
                    } else {
                        demora += Devuelvase(rutas.get(j));
                        distancia+=DevuelvaseD(rutas.get(j));
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
                        demora+=calcularTC(rutas.get(j), rutas.get(j+1));
                        distancia+=calcularDC(rutas.get(j),rutas.get(j+1));
                    } else {
                        demora+= Devuelvase(rutas.get(j));
                        distancia+=DevuelvaseD(rutas.get(j));
                    }
                    if(demora<10){
                        rutasCarros.get(i).add(rutas.get(j));}
                    k++;
                    j++;
                }
            }
            tCarros.get(i).add(demora);
            dCarros.get(i).add(distancia);
            demora = 0;
            distancia = 0;
        }
        return rutasCarros;
    }

    public static List<Integer> bestPermu(List<List<Integer>> resultados){
        double mejorDistancia = Integer.MAX_VALUE;
        double mejorTiempo = 0;
        List<Integer> mejorRuta  = null;
        for(List<Integer> ruta: resultados){
            double distancia = 0;
            double tiempo = 0;
            distancia+= ComienceD(ruta.get(0));
            distancia+= calcularDC(ruta.get(0), ruta.get(1));
            distancia+= calcularDC(ruta.get(1), ruta.get(2));
            distancia+= calcularDC(ruta.get(2), ruta.get(3));
            distancia+= DevuelvaseD(ruta.get(3));
            tiempo+= Comience(ruta.get(0));
            tiempo+= calcularTC(ruta.get(0), ruta.get(1));
            tiempo+= calcularTC(ruta.get(1), ruta.get(2));
            tiempo+= calcularTC(ruta.get(2), ruta.get(3));
            tiempo+= Devuelvase(ruta.get(3));
            if(distancia<mejorDistancia){
                mejorDistancia = distancia;
                mejorTiempo = tiempo;
                mejorRuta = ruta;
            }
        }
        tCarrosP.add(new LinkedList<Double>());
        dCarrosP.add(new LinkedList<Double>());
        dCarrosP.get(contador).add(mejorDistancia);
        tCarrosP.get(contador).add(mejorTiempo);
        contador++;
        return mejorRuta;
    }

    public static List<Integer> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) {

        }
        List<Integer> result = new ArrayList<>();
        dfs(nums, results, result);
        List<Integer> resulto = bestPermu(results);
        return resulto;
    }

    public static void dfs(int[] nums, List<List<Integer>> results, List<Integer> result) {
        int distancia = 0;
        if (nums.length == result.size()) {
            List<Integer> temp = new ArrayList<>(result);
            results.add(temp);
        }        
        for (int i=0; i<nums.length; i++) {
            if (!result.contains(nums[i])) {
                result.add(nums[i]);
                dfs(nums, results, result);
                result.remove(result.size() - 1);
            }
        }
    }

    public static List<LinkedList<Integer>> permutaciones(List<LinkedList<Integer>> rutasCarros, ArrayList<Coordinates> Datos){
        int[] rutas = new int[4];
        for(int i = 0; i<rutasCarros.size();i++){
            rutasCarrosP.add(new LinkedList<Integer>());
            for(int j = 0; j<4; j++){
                rutas[j] = rutasCarros.get(i).get(j);
            }
            List<Integer> p = permute(rutas);
            for(int x = 0; x<4;x++){
                rutasCarrosP.get(i).add(p.get(x));}
        }
        return rutasCarrosP;
    }

    public static List<LinkedList<Integer>> asignarCargaBateria(List<LinkedList<Integer>> rutasCarros, ArrayList<Coordinates> Datos){
        double distancia = 0;
        double distanciaTemporal = 0;
        int idStation = 0;
        int tStation = 0;
        boolean prueba = false;
        double tiempo = 0;
        double tiempoTemporal = 0;
        double bateriaGastada = 0;
        double bateriaSobrante = 0;
        double tiempoRecargar = 0;
        clonar(rutasCarros);
        for(int i = 0; i<nCarros; i++){
            tCarrosB.add(new LinkedList<Double>());
            dCarrosB.add(new LinkedList<Double>());
            if(dCarrosP.get(i).get(0)<128){
                tCarrosB.get(i).add(tCarrosP.get(i).get(0));
                dCarrosB.get(i).add(dCarrosP.get(i).get(0));
                continue;
            } else {
                for(int j = 0; j<3; j++){
                    if(j == 0){ //Primera vez
                        distancia+= ComienceD(rutasCarros.get(i).get(j));
                        tiempo+= Comience(rutasCarros.get(i).get(j));
                        if(distancia<128){
                            prueba = true;
                            distanciaTemporal = distancia;
                            tiempoTemporal = tiempo;
                        } else {prueba = false;}
                    } else {
                        distanciaTemporal += calcularDC(rutasCarros.get(i).get(j), rutasCarros.get(i).get(j+1));
                        tiempoTemporal+= calcularTC(rutasCarros.get(i).get(j), rutasCarros.get(i).get(j+1));
                        if(distanciaTemporal<128){
                            prueba = true;
                            distancia = distanciaTemporal;
                            tiempo = tiempoTemporal;
                        } else {prueba = false;}
                        if(prueba == false){
                            idStation = bestStation(rutasCarros.get(i).get(j), Datos, 0);
                            tStation = bestStation(rutasCarros.get(i).get(j), Datos, 1);
                            distancia +=calcularDC(rutasCarros.get(i).get(j), idStation);
                            tiempo+=calcularTC(rutasCarros.get(i).get(j), idStation);
                            if(distancia<128){
                                rutasBateria.get(i).add(j+1, idStation);
                                switch(tStation){
                                    case 0:
                                    bateriaGastada = distancia*125;
                                    bateriaSobrante = 16000-bateriaGastada;
                                    tiempoRecargar = bateriaGastada/31373;
                                    tiempo+=tiempoRecargar;
                                    break;
                                    case 1:
                                    bateriaGastada = distancia*125;
                                    bateriaSobrante = 16000-bateriaGastada;
                                    tiempoRecargar = bateriaGastada/15842;
                                    tiempo+=tiempoRecargar;
                                    break;
                                    case 2:
                                    bateriaGastada = distancia*125;
                                    bateriaSobrante = 16000-bateriaGastada;
                                    tiempoRecargar = bateriaGastada/7960.2;
                                    tiempo+=tiempoRecargar;
                                    break;
                                }
                                distanciaTemporal = 0;
                            }
                        }
                    }
                }
                if(prueba == true){
                    idStation = bestStation(rutasCarros.get(i).get(3), Datos, 0);
                    tStation = bestStation(rutasCarros.get(i).get(3), Datos, 1);
                    distancia+=calcularDC(rutasCarros.get(i).get(3), idStation);
                    if(distancia<128){
                        rutasBateria.get(i).add(3, idStation);
                        switch(tStation){
                            case 0:
                            bateriaGastada = distancia*125;
                            bateriaSobrante = 16000-bateriaGastada;
                            tiempoRecargar = bateriaGastada/31373;
                            tiempo+=tiempoRecargar;
                            break;
                            case 1:
                            bateriaGastada = distancia*125;
                            bateriaSobrante = 16000-bateriaGastada;
                            tiempoRecargar = bateriaGastada/15842;
                            tiempo+=tiempoRecargar;
                            break;
                            case 2:
                            bateriaGastada = distancia*125;
                            bateriaSobrante = 16000-bateriaGastada;
                            tiempoRecargar = bateriaGastada/7960.2;
                            tiempo+=tiempoRecargar;
                            break;
                        }
                        distancia += DevuelvaseD(idStation);
                        tiempo += Devuelvase(idStation);}
                } else {
                    distancia += DevuelvaseD(idStation);
                    tiempo += Devuelvase(idStation);}
            }
            dCarrosB.get(i).add(distancia);
            tCarrosB.get(i).add(tiempo);
            tiempoTemporal = 0;
            distanciaTemporal = 0;
            tiempo = 0;
            distancia = 0;
        }

        return rutasBateria;
    }

    public static void clonar(List<LinkedList<Integer>> rutas){
        for(int i = 0; i<rutas.size();i++){
            rutasBateria.add(new LinkedList<Integer>());
            for(int j = 0; j<4;j++){
                rutasBateria.get(i).add(rutas.get(i).get(j));
            }
        }
    }

    public static void imprimirRGB(List<LinkedList<Integer>> rutasBateria){
        String buffer = "{Deposito -> ";
        int nR = ruta.size()-1;
        for(int i = 0; i<nCarros;i++){
            if(nR <4){
                for(int j = 0; j<nR; j++){
                    buffer = buffer +rutasBateria.get(i).get(j) + " -> ";
                }
                int x = i+1;
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarrosB.get(i) + " Horas y se recorre: " + dCarrosB.get(i) + " Km";
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> ";  
            } else {
                for(int j = 0; j<rutasBateria.get(i).size(); j++){
                    buffer = buffer + rutasBateria.get(i).get(j) + " -> ";
                }
                int x = i+1;
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarrosB.get(i) + " Horas y se recorre: " + dCarrosB.get(i) + " Km";
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> "; }
        }
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
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarros.get(i) + " Horas y se recorre: " + dCarros.get(i) + " Km";
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> ";  
            } else {
                for(int j = 0; j<4; j++){
                    buffer = buffer + rutasCarros.get(i).get(j) + " -> ";
                }
                int x = i+1;
                buffer = buffer + "Deposito" + "} " + "y su tiempo es: " + tCarros.get(i) + " Horas y se recorre: " + dCarros.get(i) + " Km";
                System.out.println("La mejor ruta para el carro " + x + " es: " +buffer);
                buffer = "{Deposito -> "; }
        }
    }
}
