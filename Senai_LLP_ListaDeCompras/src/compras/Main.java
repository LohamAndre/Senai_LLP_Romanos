package br.senai.compras;

import br.senai.compras.model.Item;
import br.senai.compras.model.ListaDeCompras;
import br.senai.compras.model.Unidade;
import br.senai.compras.service.GestaoCompras;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static GestaoCompras gestao = new GestaoCompras();

    public static void main(String[] args) {

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerInt();

            switch (opcao) {
                case 1: novaLista();    break;
                case 2: fazerCompras(); break;
                case 3: relatorio();    break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opção inválida."); break;
            }
        }
    }

    static void exibirMenu() {
        System.out.println();
        System.out.println(".-------------------.");
        System.out.println("| Gestão de compras |");
        System.out.println("'-------------------'");
        System.out.println("Selecione a opção:");
        System.out.println("  1. Nova lista");
        System.out.println("  2. Fazer compras");
        System.out.println("  3. Relatório");
        System.out.println("  0. Sair");
        System.out.print(">> Opção: ");
    }

    static void novaLista() {

        String dataHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nomePadrao = "lista_" + dataHoje;

        System.out.print(">> Nova lista, informe o nome [" + nomePadrao + "]: ");
        String nome = sc.nextLine().trim();

        if (nome.isEmpty()) {
            nome = nomePadrao;
        }

        ListaDeCompras lista = new ListaDeCompras(nome);

        while (true) {

            System.out.println(">> ---Informe o item---------");
            System.out.print(">> Descrição: ");
            String descricao = sc.nextLine().trim();

            if (descricao.isEmpty()) {
                break;
            }

            System.out.print(">> Unidade (UN, CX, KG, LT): ");
            String unidadeStr = sc.nextLine().trim().toUpperCase();
            Unidade unidade;

            try {
                unidade = Unidade.valueOf(unidadeStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Unidade inválida, usando UN.");
                unidade = Unidade.UN;
            }

            System.out.print(">> Quantidade: ");
            double quantidade = lerDouble();

            lista.adicionarItem(new Item(descricao, unidade, quantidade));
        }

        gestao.adicionarLista(lista);
        System.out.println(">> ---Lista salva!-----------");
    }

    static void fazerCompras() {

        ListaDeCompras lista = selecionarLista();
        if (lista == null) return;

        ArrayList<Item> itens = lista.getItens();

        if (itens.isEmpty()) {
            System.out.println("Esta lista não tem itens.");
            return;
        }

        System.out.println(">> ---Fazer compras [" + lista.getNome() + "]---");

        int total = itens.size();
        double totalGeral = 0;

        for (int i = 0; i < total; i++) {

            Item item = itens.get(i);

            System.out.println(">> (" + (i + 1) + "/" + total + ") Produto: " + item.getDescricao()
                    + " " + formatarQtd(item.getQuantidade()) + " " + item.getUnidade());

            System.out.print(">> Quantidade [" + formatarQtd(item.getQuantidade()) + " " + item.getUnidade() + "]: ");
            String qtdStr = sc.nextLine().trim();
            double qtd = qtdStr.isEmpty() ? item.getQuantidade() : lerDoubleStr(qtdStr);

            System.out.print(">> Preço: ");
            double preco = lerDouble();

            if (preco == 0) {
                item.setEmFalta(true);
                System.out.println(">> Item em falta, pulando...");
                continue;
            }

            item.setQuantidadeComprada(qtd);
            item.setPrecoUnitario(preco);
            item.setComprado(true);

            double totalItem = qtd * preco;
            totalGeral += totalItem;

            System.out.printf(">> Total do item: R$ %.2f%n", totalItem);
        }

        lista.setCompraRealizada(true);

        System.out.println(">> ---Total------------------");
        System.out.printf(">> R$: %.2f%n", totalGeral);
    }

    static void relatorio() {

        ListaDeCompras lista = selecionarLista();
        if (lista == null) return;

        if (!lista.isCompraRealizada()) {
            System.out.println("Esta lista ainda não teve a compra realizada.");
            return;
        }

        System.out.println(">> ---Relatório [" + lista.getNome() + "]---");
        System.out.println(">> Item, Descrição, Qtd, UN, Preço");

        ArrayList<Item> itens = lista.getItens();
        double totalGeral = 0;
        double totalQtd = 0;

        for (int i = 0; i < itens.size(); i++) {

            Item item = itens.get(i);

            if (item.isComprado()) {
                System.out.printf(">> %d, %s, %s, %s, %.2f%n",
                        i + 1,
                        item.getDescricao(),
                        formatarQtd(item.getQuantidadeComprada()),
                        item.getUnidade(),
                        item.getPrecoUnitario());

                totalGeral += item.getTotal();
                totalQtd += item.getQuantidadeComprada();

            } else {
                System.out.printf(">> %d, %s, EM FALTA%n", i + 1, item.getDescricao());
            }
        }

        System.out.printf(">> 0, TOTAL, %s, UN, %.2f%n", formatarQtd(totalQtd), totalGeral);
    }

    static ListaDeCompras selecionarLista() {

        ArrayList<ListaDeCompras> listas = gestao.getListas();

        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista encontrada.");
            return null;
        }

        System.out.println("Selecione uma lista:");

        for (int i = 0; i < listas.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + listas.get(i).getNome());
        }

        System.out.print(">> Opção: ");
        int opcao = lerInt();

        if (opcao < 1 || opcao > listas.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return listas.get(opcao - 1);
    }

    static String formatarQtd(double qtd) {
        if (qtd == (int) qtd) {
            return String.valueOf((int) qtd);
        }
        return String.valueOf(qtd);
    }

    static int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    static double lerDoubleStr(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
