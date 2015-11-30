package Model;

import java.util.Random;

public class AlgoritmoGenético {

    private static int n = 5; //número de ciudades
    private static int m = 100; //número de individuos debe ser par
    private static int c = 2; //número de competidores de torneo
    private static double mapa[][] = new double[n][n];
    private static int poblacion_inicial[][] = new int[m][n];
    private static int poblacion_hijos[][] = new int[m][n];
    private static int padreA[][] = new int[m / 2][n];
    private static int padreB[][] = new int[m / 2][n];
    private double suma[] = new double[m];
    private int valido[] = new int[m];
    private int torneo[] = new int[c];
    private static Cruce cr;
    private static Mutacion mu;

    public AlgoritmoGenético() {
    }

    public void prueba_LlenarMapa() {
        double mapa_prueba[][] = {{0, 2, 1, 3, 2}, {2, 0, 2, 3, 4}, {1, 2, 0, 4, 5}, {3, 3, 3, 0, 1}, {2, 4, 5, 2, 0}};
        for (int i = 0; i < n; i++) {
            System.arraycopy(mapa_prueba[i], 0, mapa[i], 0, n);
        }
    }
//    public void copiar_hijos(int hijo[][]){
//        for (int i = 0; i < m; i++) {
//            System.arraycopy(hijo[i], 0, poblacion_hijos[i], 0, n);
//        }
//    }

    public static void setPoblacion_inicial(int[][] poblacion_inicial) {
        AlgoritmoGenético.poblacion_inicial = poblacion_inicial;
    }

    public String prueba_imprime() {
        prueba_LlenarMapa();
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
            System.out.print("criterio: " + suma[j] + "\t ciudad valida: " + valido[j] + "\n");
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
        for (int i = 0; i < m; i++) {
            prom+=suma[i];
        }
        System.out.print("\n Promedio de poblacion: "+(prom/m)+"\n");
    }

    public int prueba_torneo() {

        int i = 0, j;
        double mayor = 0;
        int ganador = 0;

        int aux = 0;
//        while (aux == 0) {
//            aux = (int) (Math.random() * (m));
//        }
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

        mayor = suma[torneo[0]];//de acuerdo a criterio
        ganador = torneo[0];
        for (int k = 1; k < c; k++) {

            if (suma[torneo[k]] > mayor) {//de acuerdo a criterio
                mayor = suma[torneo[k]];
                ganador = torneo[k];
            }
        }
        return ganador;
    }

    public void prueba_llenarParejas() {
//        System.out.print("Parejas seleccionadas: \n");
//        String cadPaA = "";
//        String cadPaB = "";
        int bandera = 0;
        int bandera1 = 0;
        int p = 0;
        for (int i = 0; i < m / 2; i++) {
            p = 0;
            while (p == 0) {

                do {
                    bandera = prueba_torneo();
                    bandera1 = prueba_torneo();
                } while (valido[bandera] == 0 && valido[bandera] == 0);

                if (bandera != bandera1) {
//                    cadPaA = "";
//                    cadPaB = "";
                    for (int j = 0; j < n; j++) {

                        padreA[i][j] = poblacion_inicial[bandera][j];
                        padreB[i][j] = poblacion_inicial[bandera1][j];
//                        cadPaA += padreA[i][j] + "\t";
//                        cadPaB += padreB[i][j] + "\t";
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
        AlgoritmoGenético.poblacion_hijos = hijos;
        setPoblacion_inicial(poblacion_hijos);
        prueba_valor_poblacion();
        System.out.println("Hijos: \n");
        imprimir_poblacion();
    }
    

    public static void main(String[] args) {

        AlgoritmoGenético ag = new AlgoritmoGenético();
        System.out.print(ag.prueba_imprime());
        ag.prueba_poblacionIncial();
        System.out.print("Poblacion inicial: \n");
        ag.imprimir_poblacion();
        for (int i = 0; i < 5; i++) {
            ag.prueba_llenarParejas();
        cr = new Cruce(m, 0.55, n);
        cr.cruzamiento(padreA, padreB);
        mu = new Mutacion(m, n, 0.55, cr.getHijos());
        mu.mutacion();
        ag.poblacion_hijo(mu.getHijos());
        }
        
    }

}
