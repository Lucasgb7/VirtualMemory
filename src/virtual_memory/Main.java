/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtual_memory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Lucas Jose da Cunha
 * @author Luiz Alberto Zimmermann Zabel Martins Pinto
 */
public class Main {

    static String hexToBin(String s) {
        if("0x".equals(s.substring(0, 2))){
            s = s.replace("0x", "");
    }
        String bin = new BigInteger(s, 16).toString(2);
        return String.format("%32s", bin).replace(" ", "0");
    }
    
    static String remove0x(String s) {
        return s = s.replaceAll("0x", "");
    }
    
    static String binToHex(String s){
        return null;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String vmAdress_hex = "0x1CC151A0";
        String pdbr = "0x001B3000";
        String vmAdress_bin = hexToBin(vmAdress_hex);
        System.out.println("VM Adress: " + vmAdress_bin);

        String pageTableNumber = vmAdress_bin.substring(0, 10);
        String pageNumber = vmAdress_bin.substring(10, 20);
        String offset = vmAdress_bin.substring(20, 32);
        
        System.out.print("------ Endereços divididos -----\n");
        System.out.print("Page Table Number: " + pageTableNumber);
        System.out.print("\nPage Number: " + pageNumber);
        System.out.print("\nOffset: " + offset);
        System.out.print("\n\n");
        
        String ptnDeslocado = leftShift_2bits(pageTableNumber); 
        System.out.printf("\nPage Table Number * 4 = " + ptnDeslocado);
        
        int a = Integer.parseInt(remove0x(ptnDeslocado), 16);
        int b = Integer.parseInt(remove0x(pdbr), 16);
        int resultado = a + b;
        String hex = Integer.toHexString(resultado);
        hex = "0x00" + hex; // Completa o resultado com dois zeros antes
        
        HashMap<String, String> tabela_address_contents = new HashMap<>();
        tabela_address_contents.put("0x0001a038", "0x000b4045");
        tabela_address_contents.put("0x000b4b9c", "0x236b12c1");
        tabela_address_contents.put("0x000b91a0", "0x1b9d8fc5");
        tabela_address_contents.put("0x001b31cc", "0x003a9067");
        tabela_address_contents.put("0x001b3458", "0x0001a067");
        tabela_address_contents.put("0x003a9054", "0x000b9067");
        /* Ver o link https://wwww.w3schools.com/java/java_hashmap.asp
                      https://blog.alura.com.br/iterando-por-um-hashmap-em-java/
        */
        System.out.println("\n\n");
        String conteudoMem = new String();
        for (Map.Entry<String, String> i : tabela_address_contents.entrySet()){
            if (hex.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        HashMap<String, String> tabela_bits = new HashMap<>();
        tabela_bits.put("31-12", "0x" + conteudoMem.substring(0, 4));
        tabela_bits.put("11-7", "0x" + hexToBin(conteudoMem).substring(20, 24));
        tabela_bits.put("6", hexToBin(conteudoMem).substring(25));
        tabela_bits.put("5", hexToBin(conteudoMem).substring(26));
        tabela_bits.put("4-3", hexToBin(conteudoMem).substring(27, 28));
        tabela_bits.put("2", hexToBin(conteudoMem).substring(29));
        tabela_bits.put("1", hexToBin(conteudoMem).substring(30));
        tabela_bits.put("0", hexToBin(conteudoMem).substring(31));
        
        String novoEnd = tabela_bits.get("31-12") + "000";
        pageNumber = pageNumber + "00";
        int pageNumberMult = Integer.parseInt(pageNumber);
        String pageNumberMultiplicado = Integer.toString(pageNumberMult,16);
        int end = Integer.parseInt(novoEnd, 16);
        resultado = pageNumberMult + end;
        String newHex = Integer.toHexString(resultado);
        
        for (Map.Entry<String, String> i : tabela_address_contents.entrySet()){
            if (newHex.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        System.out.println("\n\tSegundo conteúdo da memória: " + conteudoMem);
    }    
    private static String leftShift_2bits(String val_dec) throws NumberFormatException {
        int decimal = Integer.parseInt(val_dec, 2) * 4; // Page Table Number * 4
        String valDeslocado = "0x00000" + Integer.toHexString(decimal); // Adicionando zeros para completar o nº
        return valDeslocado;
    }
}
