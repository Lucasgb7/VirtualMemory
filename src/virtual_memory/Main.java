/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtual_memory;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Lucas Jose da Cunha
 * @author Luiz Alberto Zimmermann Zabel Martins Pinto
 */
public class Main {

    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }
    static String hexaPraBinario(String s){
        String binario = "";
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            switch (c){
                case '0':
                    binario += "0000";
                    break;
                case '1':
                    binario += "0001";
                    break;
                case '2':
                    binario += "0010";
                    break;
                case '3':
                    binario += "0011";
                    break;
                case '4':
                    binario += "0100";
                    break;
                case '5':
                    binario += "0101";
                    break;
                case '6':
                    binario += "0110";
                    break;
                case '7':
                    binario += "0111";
                    break;
                case '8':
                    binario += "1000";
                    break;
                case '9':
                    binario += "1001";
                    break;
                case 'A':
                    binario += "1010";
                    break;
                case 'B':
                    binario += "1011";
                    break;
                case 'C':
                    binario += "1100";
                    break;
                case 'D':
                    binario += "1101";
                    break;
                case 'E':
                    binario += "1110";
                    break;
                case 'F':
                    binario += "1111";
                    break;
            }
        }
        return binario;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String posicaoInicial = "1CC151A0";
        String pdbr = "001B3000";
        posicaoInicial = hexaPraBinario(posicaoInicial);
        System.out.println("VM Adress: " + posicaoInicial);

        String vmAdress = posicaoInicial.substring(0, 10);
        String pageNumber = posicaoInicial.substring(10, 20);
        String offset = posicaoInicial.substring(20, 32);

        System.out.print("Page Table Number: " + vmAdress);
        System.out.print("\nPage Number: " + pageNumber);
        System.out.print("\nOffset: " + offset);

        vmAdress = vmAdress + "00";
        int decimal = Integer.parseInt(vmAdress,2);
        String vmMultiplicado = Integer.toString(decimal,16);
        System.out.printf("\n Page Table Number * 4 = 0x" + vmMultiplicado);

        int a = Integer.parseInt(vmMultiplicado, 16);
        int b = Integer.parseInt(pdbr, 16);
        int fodaseInt = a + b;
        String hex = Integer.toHexString(fodaseInt);
        hex = "0x00" + hex;
        System.out.println("\n" + hex);
    }
    
}
