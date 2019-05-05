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
       
   static String completaZeros(String valor){ // Função para completar os zeros a esquerda do numero
        switch(valor.length()){
            case 1: return "0000000" + valor; // 7 zeros
            case 2: return "000000" + valor; // 6 zeros
            case 3: return "00000" + valor; // 5 zeros
            case 4: return "0000" + valor; // 4 zeros
            case 5: return "000" + valor; // 3 zeros
            case 6: return "00" + valor; // 2 zeros
            case 7: return "0" + valor; // 1 zero
        }
        return valor;
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String vmAdress_hex = "0x4580eb9c";
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
        System.out.println("\nPage Table Number * 4 = " + ptnDeslocado);
        
        int a = Integer.parseInt(remove0x(ptnDeslocado), 16);
        int b = Integer.parseInt(remove0x(pdbr), 16);
        int resultado = a + b;
        String adress1 = Integer.toHexString(resultado);
        adress1 = "0x" + completaZeros(adress1); // Completa o resultado com dois zeros antes
        System.out.println("adress1:"+ adress1);
        
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
            if (adress1.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        System.out.println("Conteudo 1:"+conteudoMem);
        
        HashMap<String, String> tabela1_bits = new HashMap<>();
        tabela1_bits.put("31-12", conteudoMem.substring(0, 7));
        tabela1_bits.put("11-7", "0x" + hexToBin(conteudoMem).substring(20, 24));
        tabela1_bits.put("6", hexToBin(conteudoMem).substring(25));
        tabela1_bits.put("5", hexToBin(conteudoMem).substring(26));
        tabela1_bits.put("4-3", hexToBin(conteudoMem).substring(27, 28));
        tabela1_bits.put("2", hexToBin(conteudoMem).substring(29));
        tabela1_bits.put("1", hexToBin(conteudoMem).substring(30));
        tabela1_bits.put("0", hexToBin(conteudoMem).substring(31));
        
        
        String conteudo31_12 = tabela1_bits.get("31-12") + "000"; // Completando (31-12) com zeros
        System.out.println("Conteudo (31-12): " + conteudo31_12); // Conteúdo do primeiro endereço
        pageNumber = leftShift_2bits(pageNumber); // Multiplica o PN por 4
        System.out.println("PageNumber * 4: " + pageNumber); // Imprime o PN Multiplicado
        
        int pageNumberMult = Integer.parseInt(remove0x(pageNumber),16); // Converte PN para decimal 
        int contains1Sum0 = Integer.parseInt(remove0x(conteudo31_12),16); // Converte os primeiros 10 bits do primeiro conteúdo para decimal
        resultado = contains1Sum0 + pageNumberMult; // Soma os dois decimais convertidos
        String adress2 = Integer.toHexString(resultado); // Converte o resultado para String Hexadecimal
        adress2 = "0x"+ completaZeros(adress2); 
        System.out.println("Adress2: " + adress2);
        //String pageNumberMultiplicado = Integer.toString(pageNumberMult,16);
        //int end = Integer.parseInt(Contains1, 16);
        //resultado = pageNumberMult + end;
 
        
        for (Map.Entry<String, String> i : tabela_address_contents.entrySet()){
            if (adress1.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        System.out.println("adress1:"+adress1);
        System.out.println("\n\tPrimeiro conteúdo econtrado: " + conteudoMem);
        //nao tapegando o segundo conteudo 0x000b4045
        for (Map.Entry<String, String> i : tabela_address_contents.entrySet()){
            if (adress2.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        
        System.out.println("adress2:"+adress2);
        System.out.println("\n\tSegundo conteúdo econtrado: " + conteudoMem);
        // ----------
        
        HashMap<String, String> tabela2_bits = new HashMap<>();
        tabela2_bits.put("31-12", conteudoMem.substring(0, 7));
        tabela2_bits.put("11-7", "0x" + hexToBin(conteudoMem).substring(20, 24));
        tabela2_bits.put("6", hexToBin(conteudoMem).substring(25));
        tabela2_bits.put("5", hexToBin(conteudoMem).substring(26));
        tabela2_bits.put("4-3", hexToBin(conteudoMem).substring(27, 28));
        tabela2_bits.put("2", hexToBin(conteudoMem).substring(29));
        tabela2_bits.put("1", hexToBin(conteudoMem).substring(30));
        tabela2_bits.put("0", hexToBin(conteudoMem).substring(31));
       
        String segundo_conteudo31_12 = tabela2_bits.get("31-12") + "000"; // Completando (31-12) com zeros
        System.out.println("2º Conteudo 31-12: " + segundo_conteudo31_12); // Conteúdo do primeiro endereço
        
        int offset_int = Integer.parseInt(offset, 2); // Converte PN para decimal 
        int segundo_conteudo31_12_int = Integer.parseInt(remove0x(segundo_conteudo31_12),16); // Converte os primeiros 10 bits do primeiro conteúdo para decimal
        resultado = offset_int + segundo_conteudo31_12_int; // Soma os dois decimais convertidos
        String address3 = Integer.toHexString(resultado); // Converte o resultado para String Hexadecimal
        address3 = "0x" + completaZeros(address3);
        
        System.out.println("\n3º Endereço: " + address3);
        
        for (Map.Entry<String, String> i : tabela_address_contents.entrySet()){
            if (address3.equals(i.getKey())){
                conteudoMem = i.getValue();
            }
        }
        System.out.println("3º conteudo (end. fisico): " + conteudoMem);
    }    

    private static String leftShift_2bits(String val_dec) throws NumberFormatException {
        int decimal = Integer.parseInt(val_dec, 2) * 4; // Page Table Number * 4
        String valDeslocado = completaZeros(Integer.toHexString(decimal));
            return valDeslocado;
        }
}