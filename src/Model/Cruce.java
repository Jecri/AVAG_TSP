package Model;

import java.util.*;

public class Cruce {

    private final double cruce[][] = new double[1000][1000];
    private final int tabla[][] = new int[1000][1000];
    private int m = 10;// cantidad de poblacion
    private int n = 6;// cantidad de ciudades
    private double prob_cruce = 0.55;
    private double reducc = 0.8;
    private int hijos[][] = new int[m][n];
    private int noCruzados = 0;
    private int posNoCruzadas[] = new int[noCruzados];

    public Cruce(int m, double prob_cruce, int n) {
        this.m = m;
        this.n = n;
        this.prob_cruce = prob_cruce;
        Cruce c = new Cruce();

    }

    public Cruce() {

    }

    public void probabilidadCruce() {
        for (int i = 0; i < m / 2; i++) {
            cruce[i][0] = ((float) Math.random() * 10 + 1) / 10;
            if (cruce[i][0] >= prob_cruce) {
//                System.out.print("prob1: "+cruce[nroHijo][0]+ "  prob cruce"+ prob_cruce);
                cruce[i][1] = (int) (Math.random() * reducc * n - 3) + 3;
            } else {
                cruce[i][1] = 0;
                noCruzados++;
            }

        }

    }

    public void imprimir() {
        for (int i = 0; i < m / 2; i++) {

            System.out.println("{" + i + "}" + cruce[i][0] + "-" + cruce[i][1]);

        }
    }

    public void cruzamiento(int padreA[][], int padreB[][]) {

        probabilidadCruce();
        imprimir();
        int nroPareja = 0;
        int bandera = 0;
        for (int nroHijo = 0; nroHijo < m; nroHijo = nroHijo + 2) {
            if (cruce[nroPareja][0] < prob_cruce) {
//                System.out.print("prob1: "+cruce[nroPareja][0]+ "  prob cruce"+ prob_cruce);
                for (int j = 0; j < n; j++) {
                    hijos[nroHijo][j] = padreA[nroPareja][j];
                    hijos[nroHijo + 1][j] = padreB[nroPareja][j];
                }
//                System.out.print("siempre aqui \n");

            } else {
//                System.out.print("Pareja: " + nroPareja + "\n");

                posiciones(nroPareja);
                for (int j = 0; j < cruce[nroPareja][1]; j++) {
                    tabla[j][1] = padreA[nroPareja][tabla[j][0]];
                    tabla[j][2] = padreB[nroPareja][tabla[j][0]];
                    tabla[j][3] = 0;
                    tabla[j][4] = 0;
                }
                for (int j = 0; j < cruce[nroPareja][1]; j++) {
                    for (int k = 0; k < 5; k++) {
//                        System.out.print(tabla[j][k] + "\t");
                    }
//                    System.out.print("\n");
                }
                crucetotal(nroHijo, nroPareja, padreA, 1, cruce);
//                System.out.print("´padre b\n");
                crucetotal((nroHijo + 1), nroPareja, padreB, 2, cruce);
                
            }
            nroPareja++;
        }
        imprimirhijos();
    }

    public void posiciones(int c) {
        int k;
        int[] numeros = new int[n];
//        System.out.print("as"+cruce[c][1]+"\n");
        Random rnd = new Random();
        int res;
        int[] ordenado = new int[(int) cruce[c][1]];

        k = n;
        for (int i = 0; i < n; i++) {
            numeros[i] = i;
        }
        for (int i = 0; i < (int) cruce[c][1]; i++) {
            res = rnd.nextInt(k);
            ordenado[i] = numeros[res];
            tabla[i][0] = numeros[res];
//                System.out.print("res ; "+tabla[nroHijo][0]+"\n");
            numeros[res] = numeros[k - 1];
            k--;

        }
        Arrays.sort(ordenado);
        for (int i = 0; i < (int) cruce[c][1]; i++) {
            tabla[i][0] = ordenado[i];
        }

    }

    public int buscar(int padre, double cr[][], int p, int a) {
        int bandera = -1;
        int pos = 0;
        int z = 0;
        while (z < (int) cr[p][1]) {
//                            System.out.print("tabla :"+tabla[nroPareja][a]);
            if (padre == tabla[z][a]) {
                bandera = z;
            }
            z++;
        }
        return bandera;
    }

    public void crucetotal(int nroHijo, int nroPareja, int padre[][], int b, double cr[][]) {
        int a = 0, c = 0, d = 0, e = 0;
        if (b == 1) {
            a = 2;
            c = 1;
            d = 4;
            e = 3;
        }
        if (b == 2) {
            a = 1;
            c = 2;
            d = 3;
            e = 4;
        }
//        System.out.print("estoy dentro \n");
        int bandera, z = 0;
        int pos = 0;
        for (int j = 0; j < n; j++) {//RECORRER TODA LA GIRA
//            if (j < tabla[0][0] || j > tabla[(int) cruce[nroPareja][1]][0]) { // SI POSICCION NO FUE SELECCIONADA EN LA MATRIZ
                if (buscar(j, cr, nroPareja, 0) == -1) {
                    
                
//                System.out.print("Posicion "+j+" no fue seleccionada \n");
                if (buscar(padre[nroPareja][j], cr, nroPareja, a) == -1) {
                    hijos[nroHijo][j] = padre[nroPareja][j];
//                    System.out.print("Valor no encontrado: "+padre[nroPareja][j]+"\n");
                } else {
                    
                    pos = buscar(padre[nroPareja][j], cr, nroPareja, a);
//                    System.out.print("Valor encontrado: "+padre[nroPareja][j]+"\n");
                    if (tabla[pos][3] + tabla[pos][4] == 0) {
//                        System.out.print("1. Correspondencia = 0 \n");
                        hijos[nroHijo][j] = tabla[pos][c];
                        tabla[pos][3] = 1;
                        tabla[pos][4] = 1;

                        if (buscar(tabla[pos][c], cr, nroPareja, a) != -1) {
//                            System.out.print("Marcado, de encontro valor \n");
                            tabla[buscar(tabla[pos][c], cr, nroPareja, a)][4] = 1;
                        }
         
                    }else{
                        if (tabla[pos][3] + tabla[pos][4] == 2) {
//                     System.out.print("1.  Correspondencia = 2 \n");
                    hijos[nroHijo][j] = tabla[pos][c];
                }else{
                if (tabla[pos][3] + tabla[pos][4] == 1) {
//                    System.out.print("1.  Correspondencia = 1 \n");
                    if (buscar(tabla[pos][a], cr, nroPareja, a) != -1) {
                        hijos[nroHijo][j] = tabla[pos][a];
                        tabla[pos][3] = 1;
                        tabla[pos][4] = 1;
                        if (buscar(tabla[buscar(tabla[pos][a], cr, nroPareja, c)][a], cr, nroPareja, a) != -1){
                            tabla[buscar(tabla[buscar(tabla[pos][a], cr, nroPareja, c)][a], cr, nroPareja, a)][d]=1;
                    }else{
                            hijos[nroHijo][j] = padre[nroPareja][j];
                        }
                }
                    
//                    if (tabla[pos][3] + tabla[pos][4] == 1) {
//                        System.out.print("1. Correspondencia = 1, hijo igual al padre \n");
//                        hijos[nroHijo][j] = padre[nroPareja][j];
//                    }else{
//                        if (tabla[pos][3] + tabla[pos][4] == 2) {
//                        System.out.print("1. Correspondencia = 2, inter cambiar\n");
//                        hijos[nroHijo][j] = tabla[pos][c];
                    }
                    }
                    }
                }
            } else {  //SI FUE SELECCIONADO EN MATRIZ
//                System.out.print("Posicion "+j+" seleccionada \n");
                pos = buscar(padre[nroPareja][j], cr, nroPareja, c);
                
                if (tabla[pos][3] + tabla[pos][4] == 0) {
//                    System.out.print("2.  Correspondencia = 0 \n");
                    hijos[nroHijo][j] = tabla[pos][a];
                    tabla[pos][3] = 1;
                    tabla[pos][4] = 1;

                    if (buscar(tabla[pos][c], cr, nroPareja, a) != -1) {
                        tabla[buscar(tabla[pos][c], cr, nroPareja, a)][d] = 1;
                    }
                    if (buscar(tabla[pos][a], cr, nroPareja, c) != -1) {
                        tabla[buscar(tabla[pos][a], cr, nroPareja, c)][e] = 1;
                    }

//                                
                }else{
                if (tabla[pos][3] + tabla[pos][4] == 2) {
//                     System.out.print("2.  Correspondencia = 2 \n");
                    hijos[nroHijo][j] = tabla[pos][a];
                }else{
                if (tabla[pos][3] + tabla[pos][4] == 1) {
//                    System.out.print("2.  Correspondencia = 1 \n");
                    if (buscar(tabla[pos][c], cr, nroPareja, a) != -1) {
                        hijos[nroHijo][j] = tabla[pos][c];
                        tabla[pos][3] = 1;
                        tabla[pos][4] = 1;
                        if (buscar(tabla[buscar(tabla[pos][c], cr, nroPareja, a)][c], cr, nroPareja, a) != -1){
                            tabla[buscar(tabla[buscar(tabla[pos][c], cr, nroPareja, a)][c], cr, nroPareja, a)][d]=1;
                    }else{
                            hijos[nroHijo][j] = padre[nroPareja][j];
                        }
                }
            }}}
        }
    }
}


public void imprimirhijos() {
        System.out.println("Hijos cruzados:");
        for (int j = 0; j < m; j++) {
            System.out.print("{" + (j + 1) + "}: \t");
            for (int i = 0; i < n; i++) {
                System.out.print(hijos[j][i] + "\t");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Cruce c = new Cruce();
        c.probabilidadCruce();
        c.imprimir();
    }
}