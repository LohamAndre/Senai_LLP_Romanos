package br.senai.compras.model;

public class Item {

    private String descricao;
    private Unidade unidade;
    private double quantidade;
    private double quantidadeComprada;
    private double precoUnitario;
    private boolean comprado;
    private boolean emFalta;

    public Item(String descricao, Unidade unidade, double quantidade) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.quantidadeComprada = quantidade;
        this.precoUnitario = 0;
        this.comprado = false;
        this.emFalta = false;
    }

    public double getTotal() {
        return quantidadeComprada * precoUnitario;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Unidade getUnidade() { return unidade; }
    public void setUnidade(Unidade unidade) { this.unidade = unidade; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }

    public double getQuantidadeComprada() { return quantidadeComprada; }
    public void setQuantidadeComprada(double quantidadeComprada) { this.quantidadeComprada = quantidadeComprada; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

    public boolean isComprado() { return comprado; }
    public void setComprado(boolean comprado) { this.comprado = comprado; }

    public boolean isEmFalta() { return emFalta; }
    public void setEmFalta(boolean emFalta) { this.emFalta = emFalta; }
}
