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
        return "0x" + String.format("%32s", bin).replace(" ", "0");
    }
    
    static String binToHex(String s){
        return null;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String posicaoInicial = "0x1CC151A0";
        String pdbr = "0x001B3000";
        posicaoInicial = hexToBin(posicaoInicial);
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
        int resultado = a + b;
        String hex = Integer.toHexString(resultado);
        hex = "0x00" + hex;
        
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
        
    }    
}
