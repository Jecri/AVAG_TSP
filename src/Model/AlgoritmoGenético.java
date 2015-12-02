package Model;

import java.util.Arrays;
import java.util.Random;

public class AlgoritmoGenético {

    private  int n = 50; //número de ciudades
    private  int m = 100; //número de individuos debe ser par
    private  int c = 20; //número de competidores de torneo
    private double mapa[][] = new double[n][n];
    private  int poblacion_inicial[][] = new int[m][n];
    private  int poblacion_hijos[][] = new int[m][n];
    private  int padreA[][] = new int[m / 2][n];
    private  int padreB[][] = new int[m / 2][n];
    private double suma[] = new double[m];
    private int valido[] = new int[m];
    private int valido2[] = new int[m];
    private int torneo[] = new int[c];
    private static Cruce cr;
    private static Mutacion mu;
    private double mejor;
    private double promedio=0;

    
    public  void setN(int n) {
        this.n = n;
    }

    public  void setM(int m) {
        this.m = m;
    }
    public  void setC(int c) {
        this.c = c;
    }
    public double getMejor() {
        return mejor;
    }

    public double getPromedio() {
        return promedio;
    }
    
    
    public AlgoritmoGenético() {
    }

    public void setMapa(double[][] mapa) {
        this.mapa = mapa;
        //n=mapa.length;
        //m=n*2;
        //c=(int)n*(2/10);
    }

    public  int[][] getPadreA() {
        return padreA;
    }

    public  int[][] getPadreB() {
        return padreB;
    }

    public  int getN() {
        return n;
    }

    public  int getM() {
        return m;
    }
    
    public void prueba_LlenarMapa() {
        double mapa_prueba[][] = {{0, 24, 10, 3, 12}, {12, 0, 21, 7, 4}, {11, 21, 0, 14, 5}, {3, 31, 13, 0, 15}, {21, 4, 15, 12, 0}};
        for (int i = 0; i < n; i++) {
            System.arraycopy(mapa_prueba[i], 0, mapa[i], 0, n);
        }
    }
//    public void copiar_hijos(int hijo[][]){
//        for (int i = 0; i < m; i++) {
//            System.arraycopy(hijo[i], 0, poblacion_hijos[i], 0, n);
//        }
//    }

    public  void setPoblacion_inicial(int[][] poblacion_inicial) {
        this.poblacion_inicial = poblacion_inicial;
        
    }

    public String prueba_imprime() {
//        prueba_LlenarMapa();
        String cadena = "Matriz de prueba generada \n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cadena += mapa[i][j] + "\t";
            }
            cadena += "\n";
        }
        return cadena;
    }

    public void prueba_poblacionIncial() {

        int k;
        int[] numeros = new int[n];// numero de posiciones (cruce[][0]
        Random rnd = new Random();
        int res;

        for (int j = 0; j < m; j++) {
            k = n;
            for (int i = 0; i < n; i++) {
                numeros[i] = i + 1;
            }
            for (int i = 0; i < n; i++) {
                res = rnd.nextInt(k);
                poblacion_inicial[j][i] = numeros[res];
                numeros[res] = numeros[k - 1];
                k--;

            }
        } //se imprime el resultado;
        prueba_valor_poblacion();

    }

    public void imprimir_poblacion() {
//        System.out.println("Población de prueba generada:");
        for (int j = 0; j < m; j++) {
            System.out.print("{" + (j + 1) + "}: \t");
            for (int i = 0; i < n; i++) {
                System.out.print(poblacion_inicial[j][i] + "\t");
            }
            System.out.print("criterio: " + suma[j] + "\t ciudad valida: " + valido2[j] + "\n");
        }
    }

    public void prueba_valor_poblacion() {
        int invalido = 0;
        int j = 0;
        for (int i = 0; i < m; i++) {
            suma[i]=0;
        }
        for (int i = 0; i < m; i++) {
            for (j = 0; j < n - 1; j++) {
                if (mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][j + 1] - 1] == -1) {
                    invalido = 1;
                }
                suma[i] += mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][j + 1] - 1];
            }
            if (mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][0] - 1] == -1) {
                invalido = 1;
            }
            if (invalido == 1) {
                valido[i] = 0;
                invalido = 0;
            } else {
                valido[i] = 1;
            }
            suma[i] += mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][0] - 1];
//            System.out.println("{"+i+"}: "+suma+"\t ciudad valida: "+valido);
        }
        int prom=0;
        int sum=0;
        for (int i = 0; i < m; i++) {
            prom+=suma[i];
            for (int k = 0; k < n; k++) {
                sum+=poblacion_inicial[i][k];
            }
            if (sum==((n*(n-1)/2)+n)) {
                valido2[i]=sum;
            }
            sum=0;
        }
        promedio=prom/m;
        System.out.print("Promedio de poblacion: "+promedio+"\n");
    }
    public void convergencia(){
        mejor=0;
//        double menor[]=new double[m];
//        menor=suma;
//        
//        Arrays.sort(menor);
        double menor = suma[0];//de acuerdo a criterio
        
        for (int k = 1; k < m; k++) {

            if (suma[k] < menor) {//de acuerdo a criterio
                menor = suma[k];
            }
        }
        mejor=menor;
        System.out.print("Mejor adaptado: "+mejor+"\n");
    }
    public int prueba_torneo() {

        int i = 0, j;
        double menor = 0;
        int ganador = 0;

        int aux = 0;
        while (aux == 0) {
            aux = (int) (Math.random() * (m));
        }
        torneo[i] = aux;
        for (i = 1; i < c; i++) {
            aux = 0;
            while (aux == 0) {
                aux = (int) (Math.random() * (m));
            }
            torneo[i] = aux;
            for (j = 0; j < i; j++) {
                if (torneo[i] == torneo[j]) {
                    i--;
                }
            }

        }

        menor = suma[torneo[0]];//de acuerdo a criterio
        ganador = torneo[0];
        for (int k = 1; k < c; k++) {

            if (suma[torneo[k]] < menor) {//de acuerdo a criterio
                menor = suma[torneo[k]];
                ganador = torneo[k];
            }
        }
        return ganador;
    }

    public void prueba_llenarParejas() {
        System.out.print("Parejas seleccionadas: \n"+ c);
        String cadPaA = "";
        String cadPaB = "";
        int bandera = 0;
        int bandera1 = 0;
        int p = 0;
        for (int i = 0; i < m / 2; i++) {
            p = 0;
            while (p == 0) {

//                do {
                    bandera = prueba_torneo();
//                    System.out.println("Salgo de un torneon"+ bandera);
                    bandera1 = prueba_torneo();
//                                        System.out.println("Salgo de otro torneon"+ bandera1);
//                } while (valido[bandera] == 0 && valido[bandera] == 0);

                if (bandera != bandera1) {
//                    cadPaA = "";
//                    cadPaB = "";
                    for (int j = 0; j < n; j++) {

                        padreA[i][j] = poblacion_inicial[bandera][j];
                        padreB[i][j] = poblacion_inicial[bandera1][j];
                        cadPaA += padreA[i][j] + "\t";
                        cadPaB += padreB[i][j] + "\t";
                    }
//                    System.out.print("Pareja {" + i + "}: \t" + "\n" + "Individuo: " + (bandera + 1) + ", \t" + cadPaA + "\n" + "Individuo: " + (bandera1 + 1) + ", \t" + cadPaB + "\n");
                    p = 1;
                } else {
                    p = 0;
                }
            }
        }
    }

    public void poblacion_hijo(int[][] hijos) {
        poblacion_hijos = hijos;
        setPoblacion_inicial(poblacion_hijos);
        prueba_valor_poblacion();
        System.out.println("Hijos: \n");
        imprimir_poblacion();
    }
    

    public static void main(String[] args) {
        int i=0;
        AlgoritmoGenético ag = new AlgoritmoGenético();
//        ag.setM(20);
//        ag.setN(10);
//        ag.setC(5);
        LeerCaminos lc = new LeerCaminos();
        ag.setMapa(lc.getMatrizCaminos());
//        ag.prueba_LlenarMapa();
        System.out.print(ag.prueba_imprime());
        
        //Pobalcion inicial
        ag.prueba_poblacionIncial();
        ag.convergencia();
        System.out.print("Poblacion inicial: \n");
        ag.imprimir_poblacion();
//        for (int j = 0; j < 100; j++) {
       do{
            System.out.print("\n Generacion: "+i+"\n");
            ag.prueba_llenarParejas();
        cr = new Cruce(ag.getM(), 0.85, ag.getN());
        cr.cruzamiento(ag.getPadreA(), ag.getPadreB());
        mu = new Mutacion(ag.getM(),ag.getN(), 0.5, cr.getHijos());
        mu.mutacion();
        ag.poblacion_hijo(mu.getHijos()); 
        ag.convergencia();
        i++;
        }while(ag.getMejor()<ag.getPromedio());
        
//        }
        System.out.print("\n Generacion: "+i+", promedio: "+ag.getPromedio()+", mejor: "+ag.getMejor()+"\n");
    }

}
