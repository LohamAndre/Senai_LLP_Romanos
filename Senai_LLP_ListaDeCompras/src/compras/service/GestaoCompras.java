package br.senai.compras.service;

import br.senai.compras.model.ListaDeCompras;
import java.util.ArrayList;

public class GestaoCompras {

    private ArrayList<ListaDeCompras> listas;

    public GestaoCompras() {
        this.listas = new ArrayList<>();
    }

    public void adicionarLista(ListaDeCompras lista) {
        listas.add(lista);
    }

    public ArrayList<ListaDeCompras> getListas() {
        return listas;
    }
}
