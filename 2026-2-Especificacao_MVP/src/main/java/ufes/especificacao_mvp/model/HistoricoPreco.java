package ufes.especificacao_mvp.model;

import java.time.LocalDate;

public class HistoricoPreco {
    private int id;
    private LocalDate data;
    private double valorVenda;
    private Produto produto;

    public HistoricoPreco( LocalDate data, double valorVenda, Produto produto) {
        this.data = data;
        this.valorVenda = valorVenda;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }
}
