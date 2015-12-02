package Model;

/**
 *
 * @author dosan
 */
public class Mutacion {

    private int m = 1000;// cantidad de poblacion
    private int n = 50;// cantidad de ciudades
    private int hijos[][] = new int[m][n];
    private double prob[][]= new double[m][3];
    public double probM=0.5;
    public Mutacion() {
    }

    public int[][] getHijos() {
        return hijos;
    }

    public Mutacion(int m, int n, double probM,int hijos[][]) {
        this.m = m;
        this.n = n;
        this.probM=probM;
        this.hijos = hijos;
    }
    public void mutacion(){
        probabilidadMutacion();
        mutar();
//        imprimir();
    }
//    public static void main(String[] args) {
//        Mutacion m= new Mutacion();
//        m.probabilidadMutacion();
//    }
    public void probabilidadMutacion() {
        int x=-1;
//        System.out.print("M: "+m+" y N: "+n);
        for (int i = 0; i < m ; i++) {
            prob[i][0] = ((double) Math.random() * 10 + 1) / 10;
//            System.out.print("hijo: "+i+", \t prob.:"+prob[i][0]+"\t");
            if (prob[i][0] >= probM) {
                prob[i][1] = (int) (Math.random() * (n - 1));
//                System.out.print(prob[i][1]+"\t");
                do{
                    prob[i][2] = (int) (Math.random() * (n - 1));
                }while(prob[i][2]==prob[i][1]);
//                System.out.print(prob[i][2]+"\n");
            } else {
                prob[i][1]=0;
                prob[i][2]=0;
//                System.out.print(prob[i][1]+"\t");
//                System.out.print(prob[i][2]+"\n");
            }

        }

    }
    public void mutar(){
        int a;
        int b;
        for (int i = 0; i < m; i++) {
            a=0;
            b=0;
            a=hijos[i][(int)prob[i][1]];
            b=hijos[i][(int)prob[i][2]];
            hijos[i][(int)prob[i][1]]=b;
            hijos[i][(int)prob[i][2]]=a;
        }
    }
    public void imprimir(){
        System.out.print("Hijos mutados: \n");
        for (int j = 0; j < m; j++) {
            System.out.print("{" + (j + 1) + "}: \t");
            for (int i = 0; i < n; i++) {
                System.out.print(hijos[j][i] + "\t");
            }
            System.out.print("\n");
        }
    }

}
