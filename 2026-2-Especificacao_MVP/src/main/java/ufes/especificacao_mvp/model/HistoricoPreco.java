package ufes.especificacao_mvp.model;

import java.time.LocalDate;

public class HistoricoPreco {
    private int id;
    private LocalDate data;
    private double valorVenda;
    private double percentualLucro;
    private Produto produto;

    public HistoricoPreco( LocalDate data, double valorVenda, Produto produto) {
        this(data, produto.getCategoria().getPercentualLucro(), valorVenda, produto);
    }

    public HistoricoPreco(LocalDate data, double percentualLucro, double valorVenda, Produto produto) {
        this.data = data;
        this.percentualLucro = percentualLucro;
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

    public double getPercentualLucro() {
        return percentualLucro;
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
