/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import java.util.Random;

/**
 *
 * @author dosan
 */
public class Pruebas {
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            int numAleatorio = (int) (Math.random() * 8);
//            System.out.println(""+numAleatorio);
//        }
        int n=8;  //numeros aleatorios
        int k;  //auxiliar;
        int[] numeros=new int[n];
        int[][] resultado=new int[10][n];
        Random rnd=new Random();
        int res;
        
        
        //se rellena una matriz ordenada del 1 al 31(1..n)
        
        for (int j = 0; j < 10; j++) {
            k=n;
            for(int i=0;i<n;i++){
            numeros[i]=i;
            }
            for(int i=0;i<n;i++){
                res=rnd.nextInt(k);            
                resultado[j][i]=numeros[res];
                numeros[res]=numeros[k-1];
                k--;
            
            }
        } //se imprime el resultado;
        System.out.println("El resultado de la matriz es:");
        for (int j = 0; j < 10; j++) {
            for(int i=0;i<n;i++){
                System.out.print(resultado[j][i]);
            }
            System.out.print("\n");
        }
    }
}
