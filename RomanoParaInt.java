package atividadesRamires.Senai_LLP_Romanos;

import java.util.ArrayList;
import java.util.Scanner;

public class RomanoParaInt {
    public static int AlgarismoRomanoParaInteiro(String s) {
        ArrayList<Integer> simboloVet = new ArrayList<>();
        int soma = 0;
        int valor = 0;
        char simbolo;
        for (int i = 0; i < s.length(); i++) {
            simbolo = s.toUpperCase().charAt(i);
            switch (simbolo) {
                case 'I':
                    valor = 1;
                    break;

                case 'V':
                    valor = 5;
                    break;

                case 'X':
                    valor = 10;
                    break;

                case 'L':
                    valor = 50;
                    break;

                case 'C':
                    valor = 100;
                    break;

                case 'D':
                    valor = 500;
                    break;

                case 'M':
                    valor = 1000;
                    break;

                default:
                    valor = 0;
                    break;
            }
            simboloVet.add(valor);
        }
        //1000 100 1000 10 100 1 5
        soma = simboloVet.get(simboloVet.size() - 1);
        for (int i = simboloVet.size()-1; i > 0; i--) {
            if (simboloVet.get(i) > simboloVet.get(i-1)){
                soma -= simboloVet.get(i-1);
            } else {
                soma += simboloVet.get(i-1);
            }
        }
        return soma;
    }

    public static void main(String[] args) {
        // Passando argumento.
        if (args.length > 0) {
            String algarismos = String.join("", args);
            int valor = AlgarismoRomanoParaInteiro(algarismos);
            System.out.println(valor);
            return;
        }
        // Por pipe | ou interação com usuário.
        Scanner sc = new Scanner(System.in);
        System.out.print("Informe o algarismo: ");
        String algarismos = sc.nextLine();

        int valor = AlgarismoRomanoParaInteiro(algarismos);
        System.out.println(valor);
    }
}
