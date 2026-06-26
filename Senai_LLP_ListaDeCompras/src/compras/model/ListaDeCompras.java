package br.senai.compras.model;

import java.util.ArrayList;

public class ListaDeCompras {

    private String nome;
    private ArrayList<Item> itens;
    private boolean compraRealizada;

    public ListaDeCompras(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
        this.compraRealizada = false;
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public ArrayList<Item> getItens() { return itens; }

    public boolean isCompraRealizada() { return compraRealizada; }
    public void setCompraRealizada(boolean compraRealizada) { this.compraRealizada = compraRealizada; }
}
