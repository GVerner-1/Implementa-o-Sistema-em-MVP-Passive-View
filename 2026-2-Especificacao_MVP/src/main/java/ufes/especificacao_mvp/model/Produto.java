package ufes.especificacao_mvp.model;

public class Produto {
    private int id;
    private String nome;
    private double precoCusto;
    private double precoVenda;
    private Categoria categoria;
    
    public Produto(int id, String nome, double precoCusto, double precoVenda, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
