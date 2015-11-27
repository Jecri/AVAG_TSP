package Model;

import java.util.Random;

public class AlgoritmoGenético {

    private static int n = 5; //número de ciudades
    private static int m = 10; //número de individuos
    private static int c = 2; //número de competidores de torneo
    private static double mapa[][] = new double[n][n];
    private static int poblacion_inicial[][] = new int[m][n];
    private static int padreA[][] = new int[m/2][n];
    private static int padreB[][] = new int[m/2][n];
    private double suma[] = new double[m];
    private int valido[] = new int[m];
    private int torneo[] = new int[c];
    private static Cruce cr;

    public AlgoritmoGenético() {
    }

    public void prueba_LlenarMapa() {
        double mapa_prueba[][] = {{0, 2, 1, 3, 2}, {2, 0, 2, 3, 4}, {1, 2, 0, -1, 5}, {3, 3, -1, 0, -1}, {2, 4, 5, -1, 0}};
        for (int i = 0; i < n; i++) {
            System.arraycopy(mapa_prueba[i], 0, mapa[i], 0, n);
        }
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
    public void imprimir_poblacion(){
        System.out.println("Población de prueba generada:");
        for (int j = 0; j < m; j++) {
            System.out.print("{" + (j + 1) + "}: \t");
            for (int i = 0; i < n; i++) {
                System.out.print(poblacion_inicial[j][i] + "\t");
            }
            System.out.print("criterio: " + suma[j] + "\t ciudad valida: " + valido[j] + "\n");
        }
    }

    public void prueba_valor_poblacion() {
        
        int j = 0;
        for (int i = 0; i < m; i++) {
            for (j = 0; j < n - 1; j++) {
                if (mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][j + 1] - 1] == -1) {
                    valido[i] = 0;
                } else {
                    valido[i] = 1;
                }
                suma[i] += mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][j + 1] - 1];
            }
            if (mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][0] - 1] == -1) {
                valido[i] = 0;
            } else {
                valido[i] = 1;
            }
            suma[i] += mapa[poblacion_inicial[i][j] - 1][poblacion_inicial[i][0] - 1];
//            System.out.println("{"+i+"}: "+suma+"\t ciudad valida: "+valido);
        }
    }

    public int prueba_torneo() {

        int i = 0, j;
        double mayor = 0;
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
        System.out.print("Parejas seleccionadas: \n");
        String cadPaA="";
        String cadPaB="";
        int bandera = 0;
        int bandera1 = 0;
        int p = 0;
        for (int i = 0; i < m / 2; i++) {
            p = 0;
            while (p == 0) {
                bandera = prueba_torneo();
                bandera1 = prueba_torneo();
                if (bandera != bandera1) {
                    cadPaA="";
                    cadPaB="";
                    for (int j = 0; j < n ; j++) {
                        
                        padreA[i][j] = poblacion_inicial[bandera][j];
                        padreB[i][j] = poblacion_inicial[bandera1][j];
                        cadPaA+=padreA[i][j]+"\t";
                        cadPaB+=padreB[i][j]+"\t";
                    }
                    System.out.print("Pareja {" + i + "}: \t"+"\n"+"Individuo: "+(bandera+1)+", \t"+cadPaA+"\n"+"Individuo: "+(bandera1+1)+", \t"+cadPaB+"\n");
                    p = 1;
                } else {
                    p = 0;
                }
            }
        }
    }
    
    
    public static void main(String[] args) {
        AlgoritmoGenético ag = new AlgoritmoGenético();
        System.out.print(ag.prueba_imprime());
        ag.prueba_poblacionIncial();
        ag.imprimir_poblacion();
        ag.prueba_llenarParejas();
        cr = new Cruce(m, 0.55, n);
        cr.cruzamiento(padreA, padreB);
    }

}
